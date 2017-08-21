package fi.solita.harkka.mikkohyv.application;

import fi.solita.harkka.mikkohyv.domain.model.Message;
import fi.solita.harkka.mikkohyv.domain.model.MessageId;
import fi.solita.harkka.mikkohyv.domain.model.MessageRepository;
import fi.solita.harkka.mikkohyv.domain.shared.GenericMockRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MockMessageRepository extends GenericMockRepository<MessageId, Message> implements MessageRepository {
    @Override
    public MessageId generateId() { return new MessageId(); }
}
