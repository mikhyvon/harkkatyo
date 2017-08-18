package fi.solita.harkka.mikkohyv;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class MockTopicRepository extends GenericRepository<Topic> {

    public void addTopic(Topic topic){
        repositoryData.add(topic);
    }
    public void removeTopic(Topic topic) {
        repositoryData.remove(topic);
    }

    public void updateTopic(Topic topic){

    }
    public List<Topic> queryTopics(){
        return repositoryData;
    }

    public Topic findID(UUID uid){
        return repositoryData.stream().filter( i -> i.getId() == uid).findFirst().orElse(null);
    }

}
