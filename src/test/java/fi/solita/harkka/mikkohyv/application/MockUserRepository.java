package fi.solita.harkka.mikkohyv.application;

import fi.solita.harkka.mikkohyv.domain.model.User;
import fi.solita.harkka.mikkohyv.domain.model.UserId;
import fi.solita.harkka.mikkohyv.domain.model.UserRepository;
import fi.solita.harkka.mikkohyv.domain.shared.GenericMockRepository;

public class MockUserRepository extends GenericMockRepository<UserId, User> implements UserRepository {
    @Override
    public UserId generateId() { return new UserId(); }
}
