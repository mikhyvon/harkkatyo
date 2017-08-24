package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.GenericRepository;

public interface UserRepository extends GenericRepository<UserId, User> {
    UserId checkUsernameAndPassword(String username, String password);
}
