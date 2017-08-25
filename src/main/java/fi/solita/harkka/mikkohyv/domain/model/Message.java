package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.BaseEntity;
import fi.solita.harkka.mikkohyv.infra.RealTimeService;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message extends BaseEntity<MessageId>{
    private String text;
    private Date createdDate;
    @Column(columnDefinition = "uuid")
    private UserId creatorId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="topic_id")
    private Topic messageTopic;

    protected Message() {
    }

    public Message(MessageId id, Topic topic, String messageText, UserId creatorUserId, Date createdDate) {
        super(id);
        this.text = messageText;
        this.messageTopic = topic;
        this.creatorId = creatorUserId;
        this.createdDate = createdDate;
    }

    public String text() {
        return this.text;
    }



    public Date createdDate() {
        return this.createdDate;
    }

    public void updateMessageText(String messageText){ this.text = messageText; }

    public TopicId topicId(){
        return messageTopic.getId();
    }

    public MessageId messageId(){return getId();}

    public static Message.MessageBuilder builder(){
        return new Message.MessageBuilder();
    }


    public static class MessageBuilder {
        private RealTimeService realTimeService = new RealTimeService();
        private String text;
        private Date createdDate;
        private UserId creatorId;
        private Topic messageTopic;


        public MessageBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public MessageBuilder setDate(final Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public MessageBuilder setDate() {
            this.createdDate = realTimeService.now();
            return this;
        }

        public MessageBuilder setUserId(UserId creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public MessageBuilder setTopic(Topic messageTopic){
            this.messageTopic = messageTopic;
            return this;
        }

        public Message build() {
            return new Message(new MessageId(), this.messageTopic, this.text, this.creatorId, this.createdDate);
        }
    }
}