package su.huiliang.kafkapoc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "someTopic")
class KafkaPocApplicationTests {

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    @Autowired
    private KafkaTemplate<String, String> template;


    @Test
    void contextLoads() {
    }

    @Test
    public void test() {
        template.send("someTopic", "a", "bar");
    }

}
