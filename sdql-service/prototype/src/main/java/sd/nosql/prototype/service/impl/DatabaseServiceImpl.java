package sd.nosql.prototype.service.impl;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.Record;
import sd.nosql.prototype.*;
import sd.nosql.prototype.service.RaftClientService;

import java.nio.charset.StandardCharsets;

public class DatabaseServiceImpl extends DatabaseServiceGrpc.DatabaseServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseServiceImpl.class);
    private final RaftClientService raftClientService = new RaftClientServiceImpl();

    public DatabaseServiceImpl() {}

    @Override
    public void get(Key request, StreamObserver<RecordResult> responseObserver) {
        logger.info("get::{}", request.getKey());
        String operation = "get " + request.getKey();
        String raftResponse = raftClientService.query(operation); // TODO: Use response
        responseObserver.onNext(RecordResult.newBuilder()
                .setResultType(ResultType.SUCCESS)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void set(RecordInput request, StreamObserver<RecordResult> responseObserver) {
        // TODO: With problems because of the record
        try {
            logger.info("set::");
            String getOperation = "get " + request.getKey();
            String value = raftClientService.query(getOperation);
            if (value == null) {
                Record record = request.getRecord().toBuilder().setVersion(1).build();
                String data = record.toByteString().toString(StandardCharsets.UTF_8);
                String operation = "set " + request.getKey() + " " + data;
                raftClientService.applyTransaction(operation);
                setResponse(responseObserver, ResultType.SUCCESS, null);
            } else {
                setResponse(responseObserver, ResultType.ERROR, null); // TODO: Should return record that alrady exists
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            // TODO: Try again
            e.printStackTrace();
        }
    }

    @Override
    public void del(Key request, StreamObserver<RecordResult> responseObserver) {
        try {
            logger.info("del::{}", request);
            String getOperation = "get " + request.getKey();
            String value = raftClientService.query(getOperation);
            if (value != null) {
                String operation = "del " + request.getKey();
                raftClientService.applyTransaction(operation);
                setResponse(responseObserver, ResultType.SUCCESS, null); // TODO: Should return record that was removed
            } else {
                setResponse(responseObserver, ResultType.ERROR, null);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            // TODO: Try again
            e.printStackTrace();
        }
    }

    @Override
    public void delVersion(Version request, StreamObserver<RecordResult> responseObserver) {
        logger.info("delVersion::");
        // TODO: Has to fix record saving first
    }

    @Override
    public void testAndSet(RecordUpdate request, StreamObserver<RecordResult> responseObserver) {
        logger.info("testAndSet::");
        // TODO: Has to fix record saving first
    }

    private void setResponse(StreamObserver<RecordResult> responseObserver, ResultType resultType, Record record) {
        if (record == null) {
            responseObserver.onNext(RecordResult.newBuilder()
                    .setResultType(resultType)
                    .build());
        } else {
            responseObserver.onNext(RecordResult.newBuilder()
                    .setResultType(resultType)
                    .setRecord(record)
                    .build());
        }
    }
}