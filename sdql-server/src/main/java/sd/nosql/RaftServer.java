package sd.nosql;

import org.apache.ratis.conf.RaftProperties;
import org.apache.ratis.grpc.GrpcConfigKeys;
import org.apache.ratis.protocol.RaftGroup;
import org.apache.ratis.protocol.RaftGroupId;
import org.apache.ratis.protocol.RaftPeer;
import org.apache.ratis.protocol.RaftPeerId;
import org.apache.ratis.server.RaftServerConfigKeys;
import org.apache.ratis.thirdparty.com.google.protobuf.ByteString;
import org.apache.ratis.util.LifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.service.impl.StateMachineServiceImpl;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RaftServer {
    private static final Logger logger = LoggerFactory.getLogger(RaftServer.class);
    private static final String HOSTNAME = "127.0.0.1";
    private static final String RAFT_GROUP_ID = "sdql-group-id___";
    private static final String RAFT_DIRECTORY = "database/";

    public static void main(String[] args) throws IOException, InterruptedException {
        Map<String,InetSocketAddress> id2addr = new HashMap<>();
        id2addr.put("p1", new InetSocketAddress(HOSTNAME, 5000));
        id2addr.put("p2", new InetSocketAddress(HOSTNAME, 5500));
        id2addr.put("p3", new InetSocketAddress(HOSTNAME, 6000));

        List<RaftPeer> addresses = id2addr.entrySet()
                .stream()
                .map(e -> new RaftPeer(RaftPeerId.valueOf(e.getKey()), e.getValue()))
                .collect(Collectors.toList());
        RaftPeerId myId = RaftPeerId.valueOf(args[0]);

        if (addresses.stream().noneMatch(p -> p.getId().equals(myId))) {
            logger.error("Identifier {} is invalid", args[0]);
            System.exit(1);
        }

        RaftProperties properties = new RaftProperties();
        properties.setInt(GrpcConfigKeys.OutputStream.RETRY_TIMES_KEY, Integer.MAX_VALUE);
        GrpcConfigKeys.Server.setPort(properties, id2addr.get(args[0]).getPort());
        RaftServerConfigKeys.setStorageDir(properties, Collections.singletonList(new File(RAFT_DIRECTORY + myId)));

        final RaftGroup raftGroup = RaftGroup.valueOf(RaftGroupId.valueOf(ByteString.copyFromUtf8(RAFT_GROUP_ID)), addresses);
        org.apache.ratis.server.RaftServer raftServer = org.apache.ratis.server.RaftServer.newBuilder()
                .setServerId(myId)
                .setStateMachine(new StateMachineServiceImpl()).setProperties(properties)
                .setGroup(raftGroup)
                .build();
        raftServer.start();

        while(raftServer.getLifeCycleState() != LifeCycle.State.CLOSED) {
            TimeUnit.SECONDS.sleep(1);
        }
    }
}