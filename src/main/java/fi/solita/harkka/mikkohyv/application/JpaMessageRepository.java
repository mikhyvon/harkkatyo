package fi.solita.harkka.mikkohyv.application;

import fi.solita.harkka.mikkohyv.domain.model.Message;
import fi.solita.harkka.mikkohyv.domain.model.MessageId;
import fi.solita.harkka.mikkohyv.domain.model.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@EntityScan("fi.solita.harkka.mikkohyv.domain.model.Message")
@Repository
public class JpaMessageRepository implements MessageRepository {
    private final EntityManager em;

    @Autowired
    public JpaMessageRepository(JpaContext context) { this.em = context.getEntityManagerByManagedType(Message.class); }

    @Override
    public Message findById(MessageId entityId) {
        return em.find(Message.class, entityId);
    }

    @Override
    public void store(Message entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Message entity) {
        em.remove(entity);
    }

    @Override
    public MessageId generateId() {
        return new MessageId();
    }

    @Override
    public List<Message> listAll(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> cq = cb.createQuery(Message.class);
        Root<Message> rootEntry = cq.from(Message.class);
        CriteriaQuery<Message> all = cq.select(rootEntry);
        TypedQuery<Message> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
}
