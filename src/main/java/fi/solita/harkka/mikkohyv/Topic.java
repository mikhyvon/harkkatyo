package fi.solita.harkka.mikkohyv;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
public class Topic {
    @Id
    private UUID id;
    private String topicName;
    private Date topicCreateDate;
    private UUID userId;

    public Topic(UUID uuid, String topicName) {
        this.id = uuid;
        this.topicName = topicName;
    }

    public UUID getId(){
        return id;
    }

}
