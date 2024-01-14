package com.save.cep.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SqsMessagePublisherService {

    @Value("${sqs.url}")
    private String queueUrl;

    private final AmazonSQS amazonSQS;

    @Autowired
    public SqsMessagePublisherService(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    public void sendMessage(String message) {
        log.info("SQS - Start sending message for SQS: "+message);
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message)
                .withDelaySeconds(5);
        amazonSQS.sendMessage(send_msg_request);
        log.info("SQS - Finish sending message for SQS: "+message);
    }
}
