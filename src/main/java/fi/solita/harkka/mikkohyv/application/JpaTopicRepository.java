package fi.solita.harkka.mikkohyv.application;

import fi.solita.harkka.mikkohyv.domain.model.Topic;
import fi.solita.harkka.mikkohyv.domain.model.TopicId;
import fi.solita.harkka.mikkohyv.domain.model.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
