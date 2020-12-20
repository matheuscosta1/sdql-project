package sd.nosql.prototype.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.representation.ConcurrencyModel;
import sd.nosql.prototype.representation.QueueRequest;
import sd.nosql.prototype.service.QueueProcessorService;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class QueueProcessorServiceImpl implements QueueProcessorService {
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("queue-processor-thread-%d").build();
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50, namedThreadFactory);

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
                    QueueRequest request1 = concurrencyModel.getQueue().poll();
                    switch (request1.getOperation()) {
                        case GET -> databaseService.get(request1.getKeyInput(), request1.getResponseObserver());
                        case SET -> databaseService.set(request1.getRecordInput(), request1.getResponseObserver());
                        case DEL -> databaseService.del(request1.getKeyInput(), request1.getResponseObserver());
                        case DEL_VERSION -> databaseService.delVersion(request1.getVersion(), request1.getResponseObserver());
                        case TEST_SET -> databaseService.testAndSet(request1.getRecordUpdate(), request1.getResponseObserver());
                    }
                }
                concurrencyModel.setIDLE();
                logger.info("Stopping dequeue thread for: {}", recordKey);

            });
        }
    }


}
