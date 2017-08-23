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
public class MessageTests {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TimeService timeService;

    @Test
    @Transactional
    public void messageText_AddMessage_True(){

        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");
        topicRepository.store(newTopic);

        Topic oldTopic = topicRepository.findById(topicId);
        MessageId messageId = messageRepository.generateId();
        Message newMessage = new Message(messageId, oldTopic, "Viesti on pitkä");
        messageRepository.store(newMessage);
        newTopic.addMessage(newMessage);

        Topic fetchedTopic = topicRepository.findById(topicId);
        List<Message> fetchedMessages = fetchedTopic.getMessage();
        assertNotNull(fetchedMessages);
        assertTrue("No messages found.",fetchedMessages.size() > 0);
    }

    @Test
    @Transactional
    public void messageText_ChangeMessage_True(){
        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");
        topicRepository.store(newTopic);

        Topic oldTopic = topicRepository.findById(topicId);
        MessageId messageId = messageRepository.generateId();
        Message newMessage = new Message(messageId, oldTopic, "Viesti on pitkä");
        messageRepository.store(newMessage);
        newTopic.addMessage(newMessage);

        Message oldMessage = messageRepository.findById(messageId);
        oldMessage.updateMessageText("Viesti on uudempi");

        Message changedMessage = messageRepository.findById(messageId);
        assertNotEquals("MessageText is different", "Viesti on pitkä", changedMessage.text());
    }

    @Test
    @Transactional
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

        Topic fetchedTopic = topicRepository.findById(topicId);
        Message fetchedMessage = fetchedTopic.getMessageById(messageId2);
        assertNotNull(fetchedMessage);
        assertEquals(fetchedMessage.text(), newMessage2.text());
    }

    @Test
    @Transactional
    public void removeMessages_MessagesAreRemoved_True(){
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

        Topic fetchedTopic = topicRepository.findById(topicId);
        assertNotNull(fetchedTopic);
        topicRepository.delete(fetchedTopic);
        assertNull(topicRepository.findById(topicId));
        assertNull(messageRepository.findById(messageId2));
    }

    @Test
    @Transactional
    public void messageDate_MessagesArrangedByDate_True(){
        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");

        MessageId messageId1 = messageRepository.generateId();
        Message newMessage1 = new Message(messageId1, newTopic, "aViesti on pitkä");
        newMessage1.setCreatedDate(new Date(2017,6,6,6,22,22));
        messageRepository.store(newMessage1);
        newTopic.addMessage(newMessage1);

        MessageId messageId2 = messageRepository.generateId();
        Message newMessage2 = new Message(messageId2, newTopic, "bViesti on vielä pitempi");
        newMessage2.setCreatedDate(new Date(2017,6,6,6,20,22));
        messageRepository.store(newMessage2);
        newTopic.addMessage(newMessage2);

        MessageId messageId3 = messageRepository.generateId();
        Message newMessage3 = new Message(messageId3, newTopic, "eViesti on vielä vielä pitempi");
        newMessage3.setCreatedDate(new Date(2017,6,6,6,23,22));
        messageRepository.store(newMessage3);
        newTopic.addMessage(newMessage3);

        MessageId messageId4 = messageRepository.generateId();
        Message newMessage4 = new Message(messageId4, newTopic, "cViesti on vielä vielä vielä pitempi");
        newMessage4.setCreatedDate(new Date(2017,6,6,6,21,22));
        messageRepository.store(newMessage4);
        newTopic.addMessage(newMessage4);


        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(topicId);
        assertNotNull(fetchedTopic);
        for( Message vr : fetchedTopic.getMessage()) {
            System.out.println(vr.createdDate() + " -T- " + vr.text());
        }
        //TODO Doesn't work because of @OrderBy is not order correctly
        assertEquals("Second message is first", newMessage2, fetchedTopic.getMessage().get(0));
    }


    @Test
    public void contextLoads() {
    }

}
