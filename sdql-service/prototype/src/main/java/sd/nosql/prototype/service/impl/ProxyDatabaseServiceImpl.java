package sd.nosql.prototype.service.impl;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.*;
import sd.nosql.prototype.enums.Operation;
import sd.nosql.prototype.representation.QueueRequest;
import sd.nosql.prototype.service.QueueProcessorService;

import java.util.concurrent.atomic.AtomicInteger;

public class ProxyDatabaseServiceImpl extends DatabaseServiceGrpc.DatabaseServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(ProxyDatabaseServiceImpl.class);
    private final QueueProcessorService queueProcessorService = new QueueProcessorServiceImpl();

    @Override
    public void get(Key request, StreamObserver<RecordResult> responseObserver) {
        QueueRequest queueRequest = QueueRequest.builder()
                .setRandomID()
                .setOperation(Operation.GET)
                .setKey(request)
                .setResponseObserver(responseObserver).createQueueRequest();
        queueProcessorService.addToQueue(request.getKey(), queueRequest);
        queueProcessorService.startDequeue(request.getKey());
    }

    @Override
    public void set(RecordInput request, StreamObserver<RecordResult> responseObserver) {
        QueueRequest queueRequest = QueueRequest.builder()
                .setRandomID()
                .setOperation(Operation.SET)
                .setRecordInput(request)
                .setResponseObserver(responseObserver).createQueueRequest();
        queueProcessorService.addToQueue(request.getKey(), queueRequest);
        queueProcessorService.startDequeue(request.getKey());
    }


    @Override
    public void del(Key request, StreamObserver<RecordResult> responseObserver) {
        QueueRequest queueRequest = QueueRequest.builder()
                .setRandomID()
                .setOperation(Operation.DEL)
                .setKey(request)
                .setResponseObserver(responseObserver).createQueueRequest();
        queueProcessorService.addToQueue(request.getKey(), queueRequest);
        queueProcessorService.startDequeue(request.getKey());
    }

    @Override
    public void delVersion(Version request, StreamObserver<RecordResult> responseObserver) {
        QueueRequest queueRequest = QueueRequest.builder()
                .setRandomID()
                .setOperation(Operation.DEL_VERSION)
                .setVersion(request)
                .setResponseObserver(responseObserver).createQueueRequest();
        queueProcessorService.addToQueue(request.getKey(), queueRequest);
        queueProcessorService.startDequeue(request.getKey());
    }

    @Override
    public void testAndSet(RecordUpdate request, StreamObserver<RecordResult> responseObserver) {
        QueueRequest queueRequest = QueueRequest.builder()
                .setRandomID()
                .setOperation(Operation.TEST_SET)
                .setRecordUpdate(request)
                .setResponseObserver(responseObserver).createQueueRequest();
        queueProcessorService.addToQueue(request.getKey(), queueRequest);
        queueProcessorService.startDequeue(request.getKey());
    }
}