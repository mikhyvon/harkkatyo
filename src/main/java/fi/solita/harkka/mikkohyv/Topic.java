package fi.solita.harkka.mikkohyv;

import java.util.Date;
import java.util.UUID;

public class Topic {

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
