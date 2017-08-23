package fi.solita.harkka.mikkohyv.application;

import fi.solita.harkka.mikkohyv.domain.model.Message;
import fi.solita.harkka.mikkohyv.domain.model.User;
import fi.solita.harkka.mikkohyv.domain.model.UserId;
import fi.solita.harkka.mikkohyv.domain.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@EntityScan("fi.solita.harkka.mikkohyv.domain.model.User")
@Repository
public class JpaUserRepository implements UserRepository {
    private final EntityManager em;

    @Autowired
    public JpaUserRepository(JpaContext context) { this.em = context.getEntityManagerByManagedType(User.class); }

    @Override
    public User findById(UserId entityId) {
        return em.find(User.class, entityId);
    }

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
}
