package fi.solita.harkka.mikkohyv.application;

import fi.solita.harkka.mikkohyv.domain.model.MessageRepository;
import fi.solita.harkka.mikkohyv.domain.model.TopicRepository;
import fi.solita.harkka.mikkohyv.domain.shared.TimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestMockConfiguration {
    @Bean
    @Primary
    public TopicRepository topicRepository() {
        return new MockTopicRepository();
    }

    @Bean
    @Primary
    public MessageRepository messageRepository() {
        return new MockMessageRepository();
    }

    @Bean
    @Primary
    public TimeService timeService() {
        return new MockTimeService();
    }
}
