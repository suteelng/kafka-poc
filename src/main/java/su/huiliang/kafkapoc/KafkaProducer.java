package su.huiliang.kafkapoc;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class KafkaProducer {

    public static final String TOPIC_SWISH = "transaction-swish";
    public static final String TOPIC_CARD = "transaction-card";

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducer.class, args);
    }

    @Autowired
    private KafkaTemplate<String, Transaction> template;

    private final CountDownLatch latch = new CountDownLatch(3);

    TransactionGenerator transactionGenerator = new TransactionGenerator();

    public void run(String... args) throws Exception {
        this.template.send(TOPIC_SWISH, transactionGenerator.next());
        latch.await(60, TimeUnit.SECONDS);
        log.info("All received");
    }

    @KafkaListener(topics = "transaction")
    public void listen(ConsumerRecord<String, Transaction> cr) throws Exception {
        log.info(cr.toString());
        latch.countDown();
    }

    @Bean
    public NewTopic swishTopic() {
        return TopicBuilder.name(TOPIC_SWISH)
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic cardTopic() {
        return TopicBuilder.name(TOPIC_CARD)
                .partitions(1)
                .build();
    }

}
