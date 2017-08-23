package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {fi.solita.harkka.mikkohyv.application.MikkohyvApplication.class})
@SpringBootTest
@ActiveProfiles("test")
public class TopicTests {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TimeService timeService;

    @Test
    @Transactional
    public void topicName_NameIsCorrect_True() {
        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(topicId);
        assertNotNull(fetchedTopic);
        assertEquals(fetchedTopic.name(), "Aihe");
    }

    @Test
    @Transactional
    public void topicName_ChangeNameWhenTopicHasMessages_False(){
        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");

        MessageId messageId = messageRepository.generateId();
        Message newMessage = new Message(messageId, newTopic, "Viesti on pitkä");
        messageRepository.store(newMessage);
        newTopic.addMessage(newMessage);

        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(topicId);
        List<Message> fetchedMessages = fetchedTopic.getMessage();
        assertNotNull(fetchedMessages);
        assertTrue("No messages found.",fetchedMessages.size() > 0);
        assertFalse("TopicName didn't change", fetchedTopic.updateName("UusiAihe"));

        Topic fetchedTopic2 = topicRepository.findById(topicId);
        assertEquals("Topic is original","Aihe",fetchedTopic2.name());

    }

    @Test
    @Transactional
    public void topicName_ChangeNameWhenTopicHasNoMessages_True(){
        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");

        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(topicId);
        List<Message> fetchedMessages = fetchedTopic.getMessage();
        assertNotNull(fetchedMessages);
        assertFalse("Messages found.",fetchedMessages.size() > 0);
        assertTrue("TopicName was changed", fetchedTopic.updateName("UusiAihe"));

       Topic fetchedTopic2 = topicRepository.findById(topicId);
       assertNotEquals("Topic is not original","Aihe",fetchedTopic2.name());
    }

    @Test
    @Transactional
    public void createdDate_DateIsCorrect_True(){
        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");
        Date topicDate = timeService.now();
        newTopic.setCreatedDate(topicDate);
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(topicId);
        assertNotNull(fetchedTopic);
        assertEquals(fetchedTopic.createdDate(), topicDate);
    }

    @Test
    @Transactional
    public void removeTopic_TopicIsRemoved_True(){
        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");
        Date topicDate = timeService.now();
        newTopic.setCreatedDate(topicDate);
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(topicId);
        assertNotNull(fetchedTopic);
        topicRepository.delete(fetchedTopic);
        assertNull(topicRepository.findById(topicId));
    }

    @Test
    @Transactional
    public void topic_ListAllTopics_True(){
        TopicId topicId3 = topicRepository.generateId();
        Topic newTopic3 = new Topic(topicId3, "Aihe3");
        newTopic3.setCreatedDate(new Date(2017,6,6,4,22,22));
        topicRepository.store(newTopic3);

        TopicId topicId1 = topicRepository.generateId();
        Topic newTopic1 = new Topic(topicId1, "Aihe1");
        newTopic1.setCreatedDate(new Date(2017,6,6,2,22,22));
        topicRepository.store(newTopic1);

        TopicId topicId2 = topicRepository.generateId();
        Topic newTopic2 = new Topic(topicId2, "Aihe2");
        newTopic2.setCreatedDate(new Date(2017,6,6,1,22,22));
        topicRepository.store(newTopic2);

        for(Topic t: topicRepository.listAll()){
            System.out.println(t.name() + " -- " + t.createdDate());
        }
        assertTrue(topicRepository.listAll().size() == 3);
    }

    @Test
    public void contextLoads() {
    }


}
