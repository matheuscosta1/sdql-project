package sd.nosql.prototype.service;

import sd.nosql.prototype.representation.QueueRequest;

public interface QueueProcessorService {
    String addToQueue(Long key, QueueRequest queueRequest);

    void startDequeue(Long recordKey) ;
}
