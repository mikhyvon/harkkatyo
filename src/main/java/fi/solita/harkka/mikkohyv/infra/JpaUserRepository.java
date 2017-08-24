package fi.solita.harkka.mikkohyv.infra;

import fi.solita.harkka.mikkohyv.domain.model.User;
import fi.solita.harkka.mikkohyv.domain.model.UserId;
import fi.solita.harkka.mikkohyv.domain.model.UserRepository;
import fi.solita.harkka.mikkohyv.domain.shared.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

@EntityScan("fi.solita.harkka.mikkohyv.domain.model.User")
@Repository
public class JpaUserRepository implements UserRepository {
    private final EntityManager em;

    @Autowired
    public JpaUserRepository(JpaContext context) { this.em = context.getEntityManagerByManagedType(User.class); }

    @Override
    public User findById(UserId entityId) { return em.find(User.class, entityId); }

    @Override
    public void store(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(entity);
    }

    @Override
    public UserId generateId() {
        return new UserId();
    }

    @Override
    public List<User> listAll(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);
        TypedQuery<User> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public UserId checkUsernameAndPassword(String username, String password){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry).where(cb.equal( rootEntry.get("name" ), username )).where(cb.equal(rootEntry.get("password"), password));
        TypedQuery<User> allQuery = em.createQuery(all);
        List<User> allUsers = allQuery.getResultList();
        if (allUsers.isEmpty()) {
            return null;
        }
        else if (allUsers.size() == 1) {
            return allUsers.get(0).getId();
        }
        throw new NonUniqueResultException();
    }
}
