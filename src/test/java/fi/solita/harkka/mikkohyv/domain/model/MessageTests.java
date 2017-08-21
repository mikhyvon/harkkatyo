package fi.solita.harkka.mikkohyv.domain.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {fi.solita.harkka.mikkohyv.application.MikkohyvApplication.class})
@SpringBootTest
@ActiveProfiles("test")
public class MessageTests {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TopicRepository topicRepository;

    @Test
    public void messageText_AddMessage_True(){

        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");

        MessageId messageId = messageRepository.generateId();
        Message newMessage = new Message(messageId, newTopic, "Viesti on pitkä");
        messageRepository.store(newMessage);
        newTopic.addMessage(newMessage);

        topicRepository.store(newTopic);

        assertTrue(true);
        Topic fetchedTopic = topicRepository.findById(topicId);
        List<Message> fetchedMessages = fetchedTopic.getMessage();
        assertNotNull(fetchedMessages);
        assertTrue("No messages found.",fetchedMessages.size() > 0);
    }

    @Test
    public void messageText_IsCorrectText_True(){
        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");

        MessageId messageId1 = messageRepository.generateId();
        Message newMessage1 = new Message(messageId1, newTopic, "Viesti on pitkä");
        messageRepository.store(newMessage1);
        newTopic.addMessage(newMessage1);

        MessageId messageId2 = messageRepository.generateId();
        Message newMessage2 = new Message(messageId2, newTopic, "Viesti on vielä pitempi");
        messageRepository.store(newMessage2);
        newTopic.addMessage(newMessage2);

        topicRepository.store(newTopic);

        assertTrue(true);
        Topic fetchedTopic = topicRepository.findById(topicId);
        Message fetchedMessage = fetchedTopic.getMessageById(messageId2);
        assertNotNull(fetchedMessage);
        System.out.println(fetchedMessage.text());
        assertEquals(fetchedMessage.text(), newMessage2.text());
    }

    @Test
    public void contextLoads() {
    }

}
