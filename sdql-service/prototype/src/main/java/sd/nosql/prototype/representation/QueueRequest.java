package sd.nosql.prototype.representation;

import io.grpc.stub.StreamObserver;
import sd.nosql.prototype.*;
import sd.nosql.prototype.enums.Operation;

import java.util.UUID;

public class QueueRequest {
    private final String id;
    private int retried = 0;
    private final Operation operation;
    private final RecordInput recordInput;
    private final RecordUpdate recordUpdate;
    private final Key keyInput;
    private final Version version;
    private final StreamObserver<RecordResult> responseObserver;

    public QueueRequest(String id, Operation operation, RecordInput recordInput, RecordUpdate recordUpdate, Key keyInput, Version version, StreamObserver<RecordResult> responseObserver) {
        this.id = id;
        this.operation = operation;
        this.recordInput = recordInput;
        this.recordUpdate = recordUpdate;
        this.keyInput = keyInput;
        this.version = version;
        this.responseObserver = responseObserver;
    }

    public String getId() {
        return id;
    }

    public int getRetried() {
        return retried;
    }

    public int getAndIncrementRetried() {
        return retried++;
    }

    public Operation getOperation() {
        return operation;
    }

    public RecordInput getRecordInput() {
        return recordInput;
    }

    public Key getKeyInput() {
        return keyInput;
    }

    public Version getVersion() {
        return version;
    }

    public StreamObserver<RecordResult> getResponseObserver() {
        return responseObserver;
    }

    public static Builder builder() {
        return new Builder();
    }

    public RecordUpdate getRecordUpdate() {
        return recordUpdate;
    }

    public static class Builder {
        private String id;
        private Operation operation;
        private RecordInput recordInput;
        private RecordUpdate recordUpdate;
        private Key keyInput;
        private Version version;
        private StreamObserver<RecordResult> responseObserver;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setRandomID() {
            this.id = UUID.randomUUID().toString();
            return this;
        }

        public Builder setOperation(Operation operation) {
            this.operation = operation;
            return this;
        }

        public Builder setRecordInput(RecordInput recordInput) {
            this.recordInput = recordInput;
            return this;
        }

        public Builder setRecordUpdate(RecordUpdate recordUpdate) {
            this.recordUpdate = recordUpdate;
            return this;
        }

        public Builder setKey(Key keyInput) {
            this.keyInput = keyInput;
            return this;
        }

        public Builder setVersion(Version version) {
            this.version = version;
            return this;
        }

        public Builder setResponseObserver(StreamObserver<RecordResult> streamObserver) {
            this.responseObserver = streamObserver;
            return this;
        }

        public QueueRequest createQueueRequest() {
            return new QueueRequest(id, operation, recordInput, recordUpdate, keyInput, version, responseObserver);
        }
    }
}
