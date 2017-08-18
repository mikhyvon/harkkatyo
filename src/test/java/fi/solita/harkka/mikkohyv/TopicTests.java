package fi.solita.harkka.mikkohyv;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicTests {

    @Test
    public void testSetTopic(){
        MockTopicRepository topicRepo = new MockTopicRepository();
        GenerateTopicId topicId = new GenerateTopicId();
        Topic newTopic = new Topic(topicId.id, "Aihe");
        topicRepo.addTopic(newTopic);

        Assert.assertTrue("Topic was not set",topicRepo.findID(topicId.id)!= null);
    }

    @Test
    public void contextLoads() {
    }
}
