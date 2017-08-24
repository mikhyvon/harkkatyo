package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MessageTests {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TimeService timeService;

    @Test
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
    public void message_ListAllMessages_True(){
        TopicId topicId3 = topicRepository.generateId();
        Topic newTopic3 = new Topic(topicId3, "Aihe3");
        newTopic3.setCreatedDate(new Date(2017,6,6,4,22,22));

        MessageId messageId13 = messageRepository.generateId();
        Message newMessage13 = new Message(messageId13, newTopic3, "aViesti on pitkä31");
        newMessage13.setCreatedDate(new Date(2017,6,6,6,22,24));
        messageRepository.store(newMessage13);
        newTopic3.addMessage(newMessage13);

        MessageId messageId23 = messageRepository.generateId();
        Message newMessage23 = new Message(messageId23, newTopic3, "bViesti on vielä pitempi32");
        newMessage23.setCreatedDate(new Date(2017,6,6,6,23,35));
        messageRepository.store(newMessage23);
        newTopic3.addMessage(newMessage23);

        MessageId messageId33 = messageRepository.generateId();
        Message newMessage33 = new Message(messageId33, newTopic3, "eViesti on vielä vielä pitempi33");
        newMessage33.setCreatedDate(new Date(2017,6,6,6,25,46));
        messageRepository.store(newMessage33);
        newTopic3.addMessage(newMessage33);
        topicRepository.store(newTopic3);

        //---

        TopicId topicId1 = topicRepository.generateId();
        Topic newTopic1 = new Topic(topicId1, "Aihe1");
        newTopic1.setCreatedDate(new Date(2017,6,6,2,21,22));

        MessageId messageId11 = messageRepository.generateId();
        Message newMessage11 = new Message(messageId11, newTopic1, "aViesti on pitkä14");
        newMessage11.setCreatedDate(new Date(2017,6,6,6,25,44));
        messageRepository.store(newMessage11);
        newTopic1.addMessage(newMessage11);

        topicRepository.store(newTopic1);

        //---

        TopicId topicId2 = topicRepository.generateId();
        Topic newTopic2 = new Topic(topicId2, "Aihe2");
        newTopic2.setCreatedDate(new Date(2017,6,6,1,23,22));

        MessageId messageId12 = messageRepository.generateId();
        Message newMessage12 = new Message(messageId12, newTopic2, "aViesti on pitkä21");
        newMessage12.setCreatedDate(new Date(2017,6,6,6,24,33));
        messageRepository.store(newMessage12);
        newTopic2.addMessage(newMessage12);

        MessageId messageId22 = messageRepository.generateId();
        Message newMessage22 = new Message(messageId22, newTopic2, "bViesti on vielä pitempi22");
        newMessage22.setCreatedDate(new Date(2017,6,6,6,23,55));
        messageRepository.store(newMessage22);
        newTopic2.addMessage(newMessage22);

        topicRepository.store(newTopic2);


        for(Message t: messageRepository.listAll()){
            System.out.println(t.text() + " -- " + t.createdDate());
        }
        System.out.println("Number of messages: " + messageRepository.listAll().size());
        assertTrue(messageRepository.listAll().size() == 6);
    }

    @Test
    public void contextLoads() {
    }

}
