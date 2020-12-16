package sd.nosql.prototype.service.impl;

import org.apache.ratis.client.RaftClient;
import org.apache.ratis.conf.Parameters;
import org.apache.ratis.conf.RaftProperties;
import org.apache.ratis.grpc.GrpcFactory;
import org.apache.ratis.protocol.*;
import org.apache.ratis.thirdparty.com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.Record;
import sd.nosql.prototype.response.RaftResponse;
import sd.nosql.prototype.service.RaftClientService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RaftClientServiceImpl implements RaftClientService {
    private static final Logger logger = LoggerFactory.getLogger(RaftClientServiceImpl.class);
    private static final String RAFT_GROUP_ID = "sdql-group-id___";
    private static final String HOSTNAME = "127.0.0.1";

    @Override
    public Record query(String operation) {
        try {
            RaftClient client = getRaftClient();
            RaftClientReply raftClientReply = client.sendReadOnly(Message.valueOf(operation));
            client.close();
            return getRecord(raftClientReply);
        } catch (IOException e) {
            logger.error("Error when attempt query operation {}", operation, e);
            return null;
        }
    }

    @Override
    public Record applyTransaction(String operation) {
        try {
            RaftClient client = getRaftClient();
            RaftClientReply raftClientReply = client.send(Message.valueOf(operation));
            client.close();
            return getRecord(raftClientReply);
        } catch (IOException e) {
            logger.error("Error when attempt applyTransaction operation {}", operation, e);
            return null;
        }
    }

    private Record getRecord(RaftClientReply raftClientReply) throws IOException {
        return RaftResponse.toRecord(raftClientReply);
    }

    private RaftClient getRaftClient() {
        // TODO: Improve raft fault tolerance
        Map<String, InetSocketAddress> id2addr = new HashMap<>();
        id2addr.put("p1", new InetSocketAddress(HOSTNAME, 3000));
        id2addr.put("p2", new InetSocketAddress(HOSTNAME, 3500));
        id2addr.put("p3", new InetSocketAddress(HOSTNAME, 4000));
        List<RaftPeer> addresses = id2addr.entrySet()
                .stream()
                .map(e -> new RaftPeer(RaftPeerId.valueOf(e.getKey()), e.getValue()))
                .collect(Collectors.toList());
        final RaftGroup raftGroup = RaftGroup.valueOf(RaftGroupId.valueOf(ByteString.copyFromUtf8(RAFT_GROUP_ID)), addresses);
        RaftProperties raftProperties = new RaftProperties();
        return RaftClient.newBuilder()
                .setProperties(raftProperties)
                .setRaftGroup(raftGroup)
                .setClientRpc(new GrpcFactory(new Parameters())
                        .newRaftClientRpc(ClientId.randomId(), raftProperties))
                .build();
    }
}
