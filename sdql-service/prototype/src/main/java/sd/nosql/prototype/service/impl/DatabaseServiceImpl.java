package sd.nosql.prototype.service.impl;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.Record;
import sd.nosql.prototype.*;
import sd.nosql.prototype.service.RaftClientService;

public class DatabaseServiceImpl extends DatabaseServiceGrpc.DatabaseServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseServiceImpl.class);
    private final RaftClientService raftClientService = new RaftClientServiceImpl();

    @Override
    public void get(Key request, StreamObserver<RecordResult> responseObserver) {
        try {
            logger.info("get::{}", request.getKey());
            String operation = "get " + request.getKey();
            Record record = raftClientService.query(operation);
            if (record != null) {
                setResponse(responseObserver, ResultType.SUCCESS, record);
            } else {
                setResponse(responseObserver, ResultType.ERROR, null);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("Error when get :: {}", request, e);
        }
    }

    @Override
    public void set(RecordInput request, StreamObserver<RecordResult> responseObserver) {
        try {
            logger.info("set::");
            String getOperation = "get " + request.getKey();
            Record record = raftClientService.query(getOperation);
            if (record == null) {
                Record newRecord = request.getRecord().toBuilder().setVersion(1).build();
                String data = newRecord.toString();
                String operation = "set " + request.getKey() + " " + data;
                raftClientService.applyTransaction(operation);
                setResponse(responseObserver, ResultType.SUCCESS, null);
            } else {
                setResponse(responseObserver, ResultType.ERROR, record);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("Error when set :: {}", request, e);
        }
    }

    @Override
    public void del(Key request, StreamObserver<RecordResult> responseObserver) {
        try {
            logger.info("del::{}", request);
            String getOperation = "get " + request.getKey();
            Record record = raftClientService.query(getOperation);
            if (record != null) {
                String operation = "del " + request.getKey();
                Record removedRecord = raftClientService.applyTransaction(operation);
                setResponse(responseObserver, ResultType.SUCCESS, removedRecord);
            } else {
                setResponse(responseObserver, ResultType.ERROR, null);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("Error when del :: {}", request, e);
        }
    }

    @Override
    public void delVersion(Version request, StreamObserver<RecordResult> responseObserver) {
        try {
            logger.info("delVersion::");
            String getOperation = "get " + request.getKey();
            Record record = raftClientService.query(getOperation);
            if (record != null && record.getVersion() == request.getVersion()) {
                String operation = "del " + request.getKey();
                Record removedRecord = raftClientService.applyTransaction(operation);
                setResponse(responseObserver, ResultType.SUCCESS, removedRecord);
            } else if (record != null){
                setResponse(responseObserver, ResultType.ERROR_WV, record);
            } else {
                setResponse(responseObserver, ResultType.ERROR_NE, null);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("Error when delVersion :: {}", request, e);
        }
    }

    @Override
    public void testAndSet(RecordUpdate request, StreamObserver<RecordResult> responseObserver) {
        try {
            logger.info("testAndSet::");
            String getOperation = "get " + request.getKey();
            Record record = raftClientService.query(getOperation);
            if (record != null && record.getVersion() == request.getOldVersion()) {
                Record newRecord = request.getRecord().toBuilder().setVersion(record.getVersion() + 1).build();
                String data = newRecord.toString();
                String operation = "set " + request.getKey() + " " + data;
                Record newRecordResponse = raftClientService.applyTransaction(operation);
                setResponse(responseObserver, ResultType.SUCCESS, newRecordResponse);
            } else if (record != null) {
                setResponse(responseObserver, ResultType.ERROR_WV, record);
            } else {
                setResponse(responseObserver, ResultType.ERROR_NE, null);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("Error when testAndSet :: {}", request, e);
        }
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