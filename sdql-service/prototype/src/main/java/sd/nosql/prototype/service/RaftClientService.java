package sd.nosql.prototype.service;

import sd.nosql.prototype.Record;

public interface RaftClientService {
    Record query(String operation);
    Record applyTransaction(String operation);
}
