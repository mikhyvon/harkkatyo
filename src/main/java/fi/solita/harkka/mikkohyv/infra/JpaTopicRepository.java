package fi.solita.harkka.mikkohyv.infra;

import fi.solita.harkka.mikkohyv.domain.model.Topic;
import fi.solita.harkka.mikkohyv.domain.model.TopicId;
import fi.solita.harkka.mikkohyv.domain.model.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

@EntityScan("fi.solita.harkka.mikkohyv.domain.model.Topic")
@Repository
public class JpaTopicRepository implements TopicRepository {
    private final EntityManager em;

    @Autowired
    public JpaTopicRepository(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(Topic.class);
    }

    @Override
    public Topic findById(TopicId entityId) {
        return em.find(Topic.class, entityId);
    }

    @Override
    public void store(Topic entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Topic entity) {
        em.remove(entity);
    }

    @Override
    public TopicId generateId() {
        return new TopicId();
    }

    @Override
    public List<Topic> listAll(){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Topic> cq = cb.createQuery(Topic.class);
        Root<Topic> rootEntry = cq.from(Topic.class);
        cq.orderBy(cb.asc(rootEntry.get("latestMessageDate")), cb.desc(rootEntry.get("name")));
        CriteriaQuery<Topic> all = cq.select(rootEntry);
        TypedQuery<Topic> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

}
