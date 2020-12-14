package sd.nosql.prototype.service;

public interface RaftClientService {
    String query(String operation);
    String applyTransaction(String operation);
}
