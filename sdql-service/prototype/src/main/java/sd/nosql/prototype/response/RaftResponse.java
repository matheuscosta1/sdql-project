package sd.nosql.prototype.response;

import com.google.protobuf.TextFormat;
import org.apache.ratis.protocol.RaftClientReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.Record;

import java.nio.charset.StandardCharsets;

public class RaftResponse {
    private static final Logger logger = LoggerFactory.getLogger(RaftResponse.class);

    public static Record toRecord(RaftClientReply raftClientReply) {
        try {
            String stringReply = raftClientReply.getMessage().getContent().toString(StandardCharsets.UTF_8);
            if (!stringReply.isBlank()) {
                Record.Builder builder = Record.newBuilder();
                TextFormat.getParser().merge(stringReply, builder);
                return builder.build();
            } else {
                return null;
            }
        } catch (TextFormat.ParseException e) {
            logger.error("Error when trying to convert RaftClientReply to Record", e);
            return null;
        }
    }
}
