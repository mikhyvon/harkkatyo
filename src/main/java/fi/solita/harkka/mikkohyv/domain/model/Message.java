package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message extends BaseEntity<MessageId>{
    private String text;
    private Date createdDate;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="topic_id")
    private Topic messageTopic;

    protected Message() {
    }

    public Message(MessageId id, Topic topic, String messageText) {
        super(id);
        this.text = messageText;
        this.messageTopic = topic;
    }

    public String text() {
        return this.text;
    }

    public void updateMessageText(String messageText){
        this.text = messageText;

    }

    public Date createdDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final Date createdDate){  this.createdDate = createdDate; }

    public TopicId topicId(){
        return messageTopic.getId();
    }
}