package sd.nosql.prototype.service.impl;

import org.apache.ratis.client.RaftClient;
import org.apache.ratis.conf.Parameters;
import org.apache.ratis.conf.RaftProperties;
import org.apache.ratis.grpc.GrpcFactory;
import org.apache.ratis.protocol.*;
import org.apache.ratis.thirdparty.com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.service.RaftClientService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RaftClientServiceImpl implements RaftClientService {
    private static final Logger logger = LoggerFactory.getLogger(RaftClientServiceImpl.class);

    @Override
    public String query(String operation) {
        try {
            RaftClient client = getRaftClient();
            RaftClientReply raftClientReply = client.sendReadOnly(Message.valueOf(operation));
            String response = raftClientReply.getMessage().getContent().toString(StandardCharsets.UTF_8);
            client.close();
            logger.info("Response -> {}", response);
            if (!response.isBlank()) { return response; }
            else { return null; }
        } catch (IOException e) {
            // TODO: Handle
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String applyTransaction(String operation) {
        try {
            RaftClient client = getRaftClient();
            RaftClientReply raftClientReply = client.send(Message.valueOf(operation));
            String response = raftClientReply.getMessage().getContent().toString(StandardCharsets.UTF_8);
            client.close();
            if (!response.isBlank()) { return response; }
            else { return null; }
        } catch (IOException e) {
            // TODO: Handle
            e.printStackTrace();
            return null;
        }
    }

    private RaftClient getRaftClient() {
        String raftGroupId = "sdql-group-id___";

        Map<String, InetSocketAddress> id2addr = new HashMap<>();
        id2addr.put("p1", new InetSocketAddress("127.0.0.1", 3000));
        id2addr.put("p2", new InetSocketAddress("127.0.0.1", 3500));
        id2addr.put("p3", new InetSocketAddress("127.0.0.1", 4000));

        List<RaftPeer> addresses = id2addr.entrySet()
                .stream()
                .map(e -> new RaftPeer(RaftPeerId.valueOf(e.getKey()), e.getValue()))
                .collect(Collectors.toList());

        final RaftGroup raftGroup = RaftGroup.valueOf(RaftGroupId.valueOf(ByteString.copyFromUtf8(raftGroupId)), addresses);
        RaftProperties raftProperties = new RaftProperties();

        return RaftClient.newBuilder()
                .setProperties(raftProperties)
                .setRaftGroup(raftGroup)
                .setClientRpc(new GrpcFactory(new Parameters())
                        .newRaftClientRpc(ClientId.randomId(), raftProperties))
                .build();
    }
}
