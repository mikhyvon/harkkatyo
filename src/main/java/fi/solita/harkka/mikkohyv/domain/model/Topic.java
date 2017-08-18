package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.BaseEntity;

import java.util.Date;

public class Topic extends BaseEntity<TopicId> {
    private String name;
    private Date createdDate;

    public Topic(TopicId id, String topicName) {
        super(id);
        this.name = topicName;
    }

    public String name() {
        return this.name;
    }
}
