package sd.nosql;

import com.google.protobuf.ByteString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import sd.nosql.prototype.Record;
import sd.nosql.prototype.service.impl.RaftClientServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.stream.LongStream;

public class RaftClientServiceImplTest {
    private RaftClientServiceImpl raftClientService = new RaftClientServiceImpl();

    @Test
    void shouldApplyTransactionParallel() {
        Record newRecord = Record.newBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setData(ByteString.copyFrom("{\"message\": \" Some message\"}", StandardCharsets.UTF_8))
                .build();

        LongStream.range(0L, 10000L).parallel().forEach( key -> {
            String operation = "set " + key + " " + newRecord.toString();
            Record recordResponse = this.raftClientService.applyTransaction(operation);
            Assertions.assertNotNull(recordResponse);
        });
    }

    @Test
    void shouldQueryTransaction() {
        LongStream.range(0L, 10000L).parallel().forEach( key -> {
            String operation = "get " + key;
            Record recordResponse = this.raftClientService.query(operation);
            Assertions.assertNotNull(recordResponse);
        });
    }
}
