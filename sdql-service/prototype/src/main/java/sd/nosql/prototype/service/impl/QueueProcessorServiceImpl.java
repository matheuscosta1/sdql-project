package sd.nosql.prototype.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.representation.ConcurrencyModel;
import sd.nosql.prototype.representation.QueueRequest;
import sd.nosql.prototype.service.QueueProcessorService;

import java.util.HashMap;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class QueueProcessorServiceImpl implements QueueProcessorService {
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("queue-processor-thread-%d").build();
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1000, namedThreadFactory);

    private static final Logger logger = LoggerFactory.getLogger(QueueProcessorServiceImpl.class);
    private final DatabaseServiceImpl databaseService = new DatabaseServiceImpl();
    private final HashMap<Long, ConcurrencyModel> concurrencyControl = new HashMap<>();


    @Override
    public String addToQueue(Long key, QueueRequest queueRequest) {
        ConcurrencyModel concurrencyModel = concurrencyControl.getOrDefault(key, new ConcurrencyModel());
        concurrencyControl.putIfAbsent(key, concurrencyModel);
        try {
            concurrencyModel.getQueue().offer(queueRequest, 30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return null;
        }
        return queueRequest.getId();
    }

    @Override
    public void startDequeue(Long recordKey) {
        ConcurrencyModel concurrencyModel = concurrencyControl.getOrDefault(recordKey, new ConcurrencyModel());
        if (!concurrencyModel.isRunning()) {
            logger.info("Starting dequeue thread for: {}", recordKey);
            concurrencyModel.setIsRunning();
            executor.submit(() -> {
                while (!concurrencyModel.getQueue().isEmpty()) {
                    QueueRequest queueRequest = concurrencyModel.getQueue().poll();

                    switch (queueRequest.getOperation()) {
                        case GET -> databaseService.get(queueRequest.getKeyInput(), queueRequest.getResponseObserver());
                        case SET -> databaseService.set(queueRequest.getRecordInput(), queueRequest.getResponseObserver());
                        case DEL -> databaseService.del(queueRequest.getKeyInput(), queueRequest.getResponseObserver());
                        case DEL_VERSION -> databaseService.delVersion(queueRequest.getVersion(), queueRequest.getResponseObserver());
                        case TEST_SET -> databaseService.testAndSet(queueRequest.getRecordUpdate(), queueRequest.getResponseObserver());
                    }
                }
                concurrencyModel.setIDLE();
                logger.info("Stopping dequeue thread for: {}", recordKey);
                logger.info("Current queue: {}", concurrencyControl.values().stream().map(ConcurrencyModel::getQueue).map(BlockingQueue::size).filter(i -> i > 0).collect(Collectors.toList()));
                logger.info("Current active threads: {}", executor.getActiveCount());
            });
        }
    }


}
