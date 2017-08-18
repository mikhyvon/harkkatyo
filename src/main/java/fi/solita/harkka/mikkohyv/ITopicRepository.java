package fi.solita.harkka.mikkohyv;

import java.util.List;

public interface ITopicRepository {

    void addTopic(Topic topic);
    void removeTopic(Topic topic);
    void updateTopic(Topic topic);

    List<Topic> queryTopics();
}
