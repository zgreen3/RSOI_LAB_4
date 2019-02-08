package smirnov.bn.apigateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BlockingQueue;

import smirnov.bn.apigateway.info_model_patterns.BlockingQueueMessageElementsInfo;

@Component
public class ScheduledQueuedHttpRequestsExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledQueuedHttpRequestsExecutor.class);

    private static volatile Boolean isFirstThread = true;

    private BlockingQueue currentQueue;

    private BlockingQueueMessageElementsInfo blockingQueueMessageElementsInfo;

    private RestTemplate restTemplateForScheduledExecutor = new RestTemplate();


    //https://spring.io/guides/gs/scheduling-tasks/
    //https://www.baeldung.com/spring-scheduled-tasks (:)
    @Scheduled(fixedRate = 5000, initialDelay = 10000) //N.B.: в миллисекундах, т.е. 1 сек. = 1000 мc
    public void executeQueuedHttpRequest() {

        logger.info("executeQueuedHttpRequest() in ScheduledQueuedHttpRequestsExecutor in apigateway.controller - [another] START");

        if (isFirstThread) {
            //в первом потоке ничего не делаем (т.к. это основной поток сервиса):
            isFirstThread = false;
        } else {
            currentQueue = ApiGatewayController.getServicesDmlBblockingQueue();
            try {
                blockingQueueMessageElementsInfo =
                        (BlockingQueueMessageElementsInfo) currentQueue.take();
            } catch (InterruptedException ie) {
                logger.error("InterruptedException during execution of ScheduledQueuedHttpRequestsExecutor", ie);
            }

            restTemplateForScheduledExecutor.exchange(blockingQueueMessageElementsInfo.getUri(),
                    blockingQueueMessageElementsInfo.getHttpMethod(),
                    blockingQueueMessageElementsInfo.getHttpEntity(), Object.class);
        }
    }
}