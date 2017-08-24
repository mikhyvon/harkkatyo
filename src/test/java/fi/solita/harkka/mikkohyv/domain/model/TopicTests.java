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
public class TopicTests {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    TimeService timeService;

    @Test
    public void topicName_NameIsCorrect_True() {
        TopicId topicId = topicRepository.generateId();
        Topic newTopic = new Topic(topicId, "Aihe");
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(topicId);
        assertNotNull(fetchedTopic);
        assertEquals(fetchedTopic.name(), "Aihe");
    }

    @Test
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
    public void topics_ViewAllTopicsByEssentialData_True(){
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


        for(Topic t: topicRepository.listAll()){
            System.out.println(t.name() + " -- " + t.createdDate() + " -- " + t.latestMessageDate());
        }
        assertTrue(topicRepository.listAll().size() == 3);
    }

    @Test
    public void topicMessages_MessagesArrangedByDate_True(){
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
            System.out.println(vr.createdDate() + " | " + vr.text());
        }
        //TODO Doesn't work because of @OrderBy is not set correctly
        assertEquals("Second message is first", newMessage2, fetchedTopic.getMessage().get(0));
    }

    @Test
    public void contextLoads() {
    }


}
