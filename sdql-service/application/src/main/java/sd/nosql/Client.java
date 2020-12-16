package sd.nosql;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.DatabaseServiceGrpc;
import sd.nosql.prototype.Key;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        logger.info("Channel: {}", channel);

        DatabaseServiceGrpc.DatabaseServiceBlockingStub stub = DatabaseServiceGrpc.newBlockingStub(channel);


        /*
        RecordResult r1 = stub.set(RecordInput.newBuilder()
                .setKey(50L)
                .setRecord(Record.newBuilder()
                        .setVersion(43)
                        .setTimestamp(System.currentTimeMillis())
                        .setData(ByteString.copyFrom("{\"message\": \" Some message\"}", StandardCharsets.UTF_8))
                        .build())
                .build());
        logger.info("Set :: {}", r1);

         */

        /*
        Record update = Record.newBuilder()
                .setVersion(43)
                .setTimestamp(System.currentTimeMillis())
                .setData(ByteString.copyFrom("{\"message\": \" Some message 123 new\"}", StandardCharsets.UTF_8))
                .build();

        logger.info("TestAndSet :: {}", stub.testAndSet(RecordUpdate.newBuilder().setOldVersion(2).setRecord(update).setKey(50L).build()));

         */


        //logger.info("Del :: {}", stub.del(Key.newBuilder().setKey(50L).build()));

        //logger.info("DelVersion :: {}", stub.delVersion(Version.newBuilder().setVersion(3).setKey(10L).build()));


        //logger.info("Get: {}", stub.get(Key.newBuilder().setKey(50L).build()));

        //RecordResult r2 = stub.del(Key.newBuilder().setKey(21L).build());
        logger.info("Get: {}", stub.get(Key.newBuilder().setKey(50L).build()));
        channel.shutdown();
    }

    /*
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        logger.info("Channel: {}", channel);

        DatabaseServiceGrpc.DatabaseServiceFutureStub stub = DatabaseServiceGrpc.newFutureStub(channel);

        stub.set(RecordInput.newBuilder()
                .setKey(1606078612219L)
                .setRecord(Record.newBuilder()
                        .setTimestamp(System.currentTimeMillis())
                        .setData(ByteString.copyFrom("{\"message\": \" Some message\"}", StandardCharsets.UTF_8))
                        .build())
                .build());

        var last = stub.testAndSet(
                RecordUpdate.newBuilder()
                        .setOldVersion(1)
                        .setKey(1606078612219L)
                        .setRecord(Record.newBuilder()
                                .setTimestamp(System.currentTimeMillis())
                                .setData(ByteString.copyFrom("{\"message\": \" Some message\"}", StandardCharsets.UTF_8))
                                .build())
                        .build());

        last.addListener(()-> logger.info("Gotten"), MoreExecutors.directExecutor());
        Futures.addCallback(last, new FutureCallback<>() {
            @Override
            public void onSuccess(RecordResult recordResult) {
                    logger.info("Result: {}", recordResult);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        }, MoreExecutors.directExecutor());
        while (true) {
            if (last.isDone())
                break;
            Thread.sleep(100);
        }
        logger.info("Current Record: {}", stub.get(Key.newBuilder().setKey(1606078612219L).build()));
        channel.shutdown();
    }

     */
}
    