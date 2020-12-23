package sd.nosql.request;

import sd.nosql.utils.RaftOperation;

public class RaftProtoRequest {
    private RaftOperation.Operation operation;
    private Long key;
    private String data;

    public RaftProtoRequest(RaftOperation.Operation operation, Long key, String data) {
        this.operation = operation;
        this.key = key;
        this.data = data;
    }

    public RaftProtoRequest(RaftOperation.Operation operation, Long key) {
        this.operation = operation;
        this.key = key;
    }

    public RaftOperation.Operation getOperation() {
        return operation;
    }

    public Long getKey() {
        return key;
    }

    public String getData() {
        return data;
    }
}
