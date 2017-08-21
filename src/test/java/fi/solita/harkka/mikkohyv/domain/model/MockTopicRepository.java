package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.GenericMockRepository;

public class MockTopicRepository extends GenericMockRepository<TopicId, Topic> implements TopicRepository {
    @Override
    public TopicId generateId() {
        return new TopicId();
    }
}
