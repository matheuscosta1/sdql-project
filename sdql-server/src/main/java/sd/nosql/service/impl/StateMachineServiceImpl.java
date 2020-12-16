package sd.nosql.service.impl;

import org.apache.ratis.proto.RaftProtos;
import org.apache.ratis.protocol.Message;
import org.apache.ratis.statemachine.TransactionContext;
import org.apache.ratis.statemachine.impl.BaseStateMachine;
import sd.nosql.request.RaftProtoRequest;
import sd.nosql.utils.RaftOperation;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static sd.nosql.utils.RaftOperation.Operation.DEL;
import static sd.nosql.utils.RaftOperation.Operation.SET;

public class StateMachineServiceImpl extends BaseStateMachine {
    private static final String SPLIT_REGEX = "\\s+";
    private final Map<Long, String> database = new ConcurrentHashMap<>();

    @Override
    public CompletableFuture<Message> query(Message request) {
        final String[] operation = request.getContent().toString(StandardCharsets.UTF_8).split(SPLIT_REGEX);
        RaftProtoRequest raftRequest = getRaftProtoRequest(operation);
        String response = database.get(raftRequest.getKey());
        LOG.debug("GET key {}, result {}", raftRequest.getKey(), response);
        if (response != null) {
            return CompletableFuture.completedFuture(Message.valueOf(response));
        } else {
            return CompletableFuture.completedFuture(Message.EMPTY);
        }
    }

    @Override
    public CompletableFuture<Message> applyTransaction(TransactionContext trx) {
        // TODO: Use record instead of string

        RaftProtos.LogEntryProto entry = trx.getLogEntry();
        RaftProtoRequest request = getRaftProtoRequest(entry);
        String response = applyOperation(request);
        if (response != null) {
            LOG.debug("{} key {} with value {}", request.getOperation(), request.getKey(), request.getData());
            return CompletableFuture.completedFuture(Message.valueOf(response));
        } else {
            // TODO: Handle
            LOG.debug("Error on operation {} key {} with value {}", request.getOperation(), request.getKey(), request.getData());
            return CompletableFuture.completedFuture(Message.EMPTY);
        }
    }

    private String applyOperation(RaftProtoRequest request) {
        if (request.getOperation() == SET) {
            LOG.info("Put {} in database", request.getKey());
            database.put(request.getKey(), request.getData());
            return this.database.get(request.getKey());
        } else if (request.getOperation() == DEL) {
            LOG.info("Removed {} key from database", request.getKey());
            return this.database.remove(request.getKey());
        } else {
            return null;
        }
    }

    private RaftOperation.Operation getOperation(String operation) {
        return RaftOperation.getRaftOperation(operation);
    }

    private RaftProtoRequest getRaftProtoRequest(RaftProtos.LogEntryProto entry) {
        String[] operationString = entry.getStateMachineLogEntry().getLogData().toString(StandardCharsets.UTF_8).split(SPLIT_REGEX);
        RaftOperation.Operation operation = getOperation(operationString[0]);
        Long key = Long.valueOf(operationString[1]);
        StringBuilder builder = new StringBuilder();
        for(int i=2;i<operationString.length;i++) {
            builder.append(operationString[i]).append(" ");
        }
        return new RaftProtoRequest(operation, key, builder.toString());
    }

    private RaftProtoRequest getRaftProtoRequest(String[] operationString) {
        RaftOperation.Operation operation = getOperation(operationString[0]);
        Long key = Long.valueOf(operationString[1]);
        return new RaftProtoRequest(operation, key);
    }
}