package fi.solita.harkka.mikkohyv.domain.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.apache.catalina.util.ConcurrentDateFormat.GMT;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {fi.solita.harkka.mikkohyv.application.MikkohyvApplication.class})
@SpringBootTest
@ActiveProfiles("test")
public class TopicTests {


    @Test
    public void topicName_NameIsCorrect_True() {
        TopicRepository topicRepository = new MockTopicRepository();

        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(topicId);
        assertNotNull(fetchedTopic);
        assertEquals(fetchedTopic.name(), "Aihe");
    }

    @Test
    public void createdDate_DateIsCorrect_True(){
        TopicRepository topicRepository = new MockTopicRepository();

        TopicId topicId = topicRepository.generateId();
        long topicDate = 1503300000;
        Topic newTopic = new Topic(topicId, "Aihe");
        newTopic.setCreatedDate(topicDate);
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(topicId);
        assertNotNull(fetchedTopic);
        System.out.println(fetchedTopic.createdDate());
        assertEquals(fetchedTopic.createdDate(), topicDate);
    }

    @Test
    public void contextLoads() {
    }


}
