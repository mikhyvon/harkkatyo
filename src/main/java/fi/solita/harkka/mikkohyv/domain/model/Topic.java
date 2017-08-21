package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.BaseEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Topic extends BaseEntity<TopicId> {
    private String name;
    private Date createdDate;

    protected Topic() {
    }

    public Topic(TopicId id, String topicName) {
        super(id);
        this.name = topicName;
    }

    public String name() {
        return this.name;
    }

    public Date createdDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final Date createdDate){
        this.createdDate = createdDate;
    }
}
