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
    UserRepository userRepository;

    @Autowired
    TimeService timeService;


    @Test
    public void topicBuilder_BuilderWorks_True(){
        User newUser = new User.UserBuilder().setName("Kayttaja").setRole("Admin").setPassword("salasana").build();
        userRepository.store(newUser);
        Topic newTopic = new Topic.TopicBuilder().setName("Aihe").setDate().setUserId(newUser.id()).build();
        topicRepository.store(newTopic);
        Message newMessage = new Message.MessageBuilder().setText("Viesti on pitkä").setTopic(newTopic).setUserId(newUser.id()).setDate().build();
        messageRepository.store(newMessage);

        assertTrue(newTopic.name() == "Aihe");
    }

    @Test
    public void topicName_NameIsCorrect_True() {
        User newUser = new User.UserBuilder().setName("Kayttaja").setRole("Admin").setPassword("salasana").build();
        userRepository.store(newUser);
        Topic newTopic = new Topic.TopicBuilder().setName("Aihe").setDate().setUserId(newUser.id()).build();
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(newTopic.id());
        assertNotNull(fetchedTopic);
        assertEquals(fetchedTopic.name(), "Aihe");
    }

    @Test
    public void topicName_ChangeNameWhenTopicHasMessages_False(){
        User newUser = new User.UserBuilder().setName("Kayttaja").setRole("Admin").setPassword("salasana").build();
        userRepository.store(newUser);
        Topic newTopic = new Topic.TopicBuilder().setName("Aihe").setDate().setUserId(newUser.id()).build();
        topicRepository.store(newTopic);
        Message newMessage = new Message.MessageBuilder().setText("Viesti on pitkä").setTopic(newTopic).setUserId(newUser.id()).setDate().build();
        messageRepository.store(newMessage);
        newTopic.addMessage(newMessage);

        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(newTopic.id());
        List<Message> fetchedMessages = fetchedTopic.getMessage();
        assertNotNull(fetchedMessages);
        assertTrue("No messages found.",fetchedMessages.size() > 0);
        assertFalse("TopicName didn't change", fetchedTopic.updateName("UusiAihe"));

        Topic fetchedTopic2 = topicRepository.findById(newTopic.id());
        assertEquals("Topic is original","Aihe",fetchedTopic2.name());

    }

    @Test
    public void topicName_ChangeNameWhenTopicHasNoMessages_True(){
        User newUser = new User.UserBuilder().setName("Kayttaja").setRole("Admin").setPassword("salasana").build();
        userRepository.store(newUser);
        Topic newTopic = new Topic.TopicBuilder().setName("Aihe").setDate().setUserId(newUser.id()).build();
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(newTopic.id());
        List<Message> fetchedMessages = fetchedTopic.getMessage();
        assertNotNull(fetchedMessages);
        assertFalse("Messages found.",fetchedMessages.size() > 0);
        assertTrue("TopicName was changed", fetchedTopic.updateName("UusiAihe"));

       Topic fetchedTopic2 = topicRepository.findById(newTopic.id());
       assertNotEquals("Topic is not original","Aihe",fetchedTopic2.name());
    }

    @Test
    public void createdDate_DateIsCorrect_True(){
        User newUser = new User.UserBuilder().setName("Kayttaja").setRole("Admin").setPassword("salasana").build();
        userRepository.store(newUser);
        Date topicDate = timeService.now();
        Topic newTopic = new Topic.TopicBuilder().setName("Aihe").setDate(topicDate).setUserId(newUser.id()).build();
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(newTopic.id());
        assertNotNull(fetchedTopic);
        assertEquals(fetchedTopic.createdDate(), topicDate);
    }

    @Test
    public void removeTopic_TopicIsRemoved_True(){
        User newUser = new User.UserBuilder().setName("Kayttaja").setRole("Admin").setPassword("salasana").build();
        userRepository.store(newUser);
        Date topicDate = timeService.now();
        Topic newTopic = new Topic.TopicBuilder().setName("Aihe").setDate(topicDate).setUserId(newUser.id()).build();
        topicRepository.store(newTopic);

        Topic fetchedTopic = topicRepository.findById(newTopic.id());
        assertNotNull(fetchedTopic);
        topicRepository.delete(fetchedTopic);
        assertNull(topicRepository.findById(newTopic.id()));
    }

    //TODO Fix ERRORS --- SQL Error: 3401, SQLState: 22001 --- data exception: string data, right truncation;  table: TOPIC column: CREATOR_ID
    /*@Test
    public void topic_ListAllTopics_True(){
        User newUser = new User.UserBuilder().setName("Kayttaja").setRole("Admin").setPassword("salasana").build();
        userRepository.store(newUser);
        Topic newTopic1 = new Topic.TopicBuilder().setName("Aihe1").setDate(new Date(2017,6,6,4,22,22)).setUserId(newUser.id()).build();
        topicRepository.store(newTopic1);

        Topic newTopic2 = new Topic.TopicBuilder().setName("Aihe2").setDate(new Date(2017,6,6,2,22,22)).setUserId(newUser.id()).build();
        topicRepository.store(newTopic2);

        Topic newTopic3 = new Topic.TopicBuilder().setName("Aihe3").setDate(new Date(2017,6,6,1,22,22)).setUserId(newUser.id()).build();
        topicRepository.store(newTopic3);

        for(Topic t: topicRepository.listAll()){
            System.out.println(t.name() + " -- " + t.createdDate());
        }
        assertTrue(topicRepository.listAll().size() == 3);
    }

    @Test
    public void topics_ViewAllTopicsByEssentialData_True(){
        User newUser = new User.UserBuilder().setName("Kayttaja").setRole("Admin").setPassword("salasana").build();
        userRepository.store(newUser);
        Topic newTopic1 = new Topic.TopicBuilder().setName("Aihe1").setDate(new Date(2017,6,6,4,22,22)).setUserId(newUser.id()).build();
        topicRepository.store(newTopic1);

            Message newMessage1 = new Message.MessageBuilder().setText("aViesti on pitkä11").setTopic(newTopic1).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,22,24)).build();
            messageRepository.store(newMessage1);
            newTopic1.addMessage(newMessage1);

            Message newMessage2 = new Message.MessageBuilder().setText("bViesti on vielä pitempi12").setTopic(newTopic1).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,23,35)).build();
            messageRepository.store(newMessage2);
            newTopic1.addMessage(newMessage2);

            Message newMessage3 = new Message.MessageBuilder().setText("Viesti on vielä vielä pitempi13").setTopic(newTopic1).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,25,46)).build();
            messageRepository.store(newMessage3);
            newTopic1.addMessage(newMessage3);

        Topic newTopic2 = new Topic.TopicBuilder().setName("Aihe2").setDate(new Date(2017,6,6,2,22,22)).setUserId(newUser.id()).build();
        topicRepository.store(newTopic2);

            Message newMessage4 = new Message.MessageBuilder().setText("aViesti on pitkä22").setTopic(newTopic2).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,24,24)).build();
            messageRepository.store(newMessage4);
            newTopic1.addMessage(newMessage4);

            Message newMessage5 = new Message.MessageBuilder().setText("bViesti on vielä pitempi23").setTopic(newTopic2).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,21,35)).build();
            messageRepository.store(newMessage5);
            newTopic1.addMessage(newMessage5);

        Topic newTopic3 = new Topic.TopicBuilder().setName("Aihe3").setDate(new Date(2017,6,6,1,22,22)).setUserId(newUser.id()).build();
        topicRepository.store(newTopic3);

            Message newMessage6 = new Message.MessageBuilder().setText("bViesti on vielä pitempi33").setTopic(newTopic3).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,16,35)).build();
            messageRepository.store(newMessage6);
            newTopic1.addMessage(newMessage6);


        for(Topic t: topicRepository.listAll()){
            System.out.println(t.name() + " -- " + t.createdDate() + " -- " + t.latestMessageDate());
        }
        assertTrue(topicRepository.listAll().size() == 3);
    }

    @Test
    public void topicMessages_MessagesArrangedByDate_True(){
        User newUser = new User.UserBuilder().setName("Kayttaja").setRole("Admin").setPassword("salasana").build();
        userRepository.store(newUser);
        Topic newTopic1 = new Topic.TopicBuilder().setName("Aihe1").setDate(new Date(2017,6,6,4,22,22)).setUserId(newUser.id()).build();
        topicRepository.store(newTopic1);

        Message newMessage1 = new Message.MessageBuilder().setText("aViesti on pitkä11").setTopic(newTopic1).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,22,24)).build();
        messageRepository.store(newMessage1);
        newTopic1.addMessage(newMessage1);

        Message newMessage2 = new Message.MessageBuilder().setText("bViesti on vielä pitempi12").setTopic(newTopic1).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,21,35)).build();
        messageRepository.store(newMessage2);
        newTopic1.addMessage(newMessage2);

        Message newMessage3 = new Message.MessageBuilder().setText("Viesti on vielä vielä pitempi13").setTopic(newTopic1).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,25,46)).build();
        messageRepository.store(newMessage3);
        newTopic1.addMessage(newMessage3);

        Message newMessage4 = new Message.MessageBuilder().setText("Viesti on vielä vielä pitempi14").setTopic(newTopic1).setUserId(newUser.id()).setDate(new Date(2017,6,6,6,25,45)).build();
        messageRepository.store(newMessage4);
        newTopic1.addMessage(newMessage4);


        topicRepository.store(newTopic1);

        Topic fetchedTopic = topicRepository.findById(newTopic1.id());
        assertNotNull(fetchedTopic);
        for( Message vr : fetchedTopic.getMessage()) {
            System.out.println(vr.createdDate() + " | " + vr.text());
        }
        //TODO Doesn't work because of @OrderBy is not set correctly
        assertEquals("Second message is first", newMessage2, fetchedTopic.getMessage().get(0));
    }*/



}
