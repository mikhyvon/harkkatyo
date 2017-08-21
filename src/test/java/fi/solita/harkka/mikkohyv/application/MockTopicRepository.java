package fi.solita.harkka.mikkohyv.application;

import fi.solita.harkka.mikkohyv.domain.model.Topic;
import fi.solita.harkka.mikkohyv.domain.model.TopicId;
import fi.solita.harkka.mikkohyv.domain.model.TopicRepository;
import fi.solita.harkka.mikkohyv.domain.shared.GenericMockRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MockTopicRepository extends GenericMockRepository<TopicId, Topic> implements TopicRepository {
    @Override
    public TopicId generateId() {
        return new TopicId();
    }
}
