package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.BaseEntity;
import fi.solita.harkka.mikkohyv.infra.RealTimeService;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.*;

@Entity
public class Topic extends BaseEntity<TopicId> {
    private String name;
    private Date createdDate;
    private Date latestMessageDate;
    @Column(columnDefinition = "uuid")
    private UserId creatorId;


    @SortNatural
    @OrderBy("created_date DESC")
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy="messageTopic", orphanRemoval=true)
    private List<Message> messages = new ArrayList<>();

    protected Topic() {
    }


    public Topic(TopicId id, String topicName, Date createdDate, UserId creatorUserId) {
        super(id);
        this.name = topicName;
        this.creatorId = creatorUserId;
        this.createdDate = createdDate;
    }

    public TopicId id() { return this.getId(); }

    public String name() {  return this.name; }

    public Date latestMessageDate() {
        return this.latestMessageDate;
    }

    public Date createdDate() {
        return this.createdDate;
    }


    public boolean updateName(String topicName){
        if (messages.size() > 0)
            return false;
        this.name = topicName;
        return true;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        this.latestMessageDate = message.createdDate();
    }

    @SortNatural
    @OrderBy("created_date DESC")
    public List<Message> getMessage() {
        return messages;
    }

    public Message getMessageById(MessageId messageId){
        //messages.stream().anyMatch(m -> m.getId().equals(messageId));
        for(Message mes : this.messages){
            if (mes.getId().equals(messageId)){
                return mes;
            }
        }
        return null;
    }

    public static TopicBuilder builder(){
        return new TopicBuilder();
    }

    public static class TopicBuilder {
        private RealTimeService realTimeService = new RealTimeService();
        private UserId userId;
        private String name;
        private Date createdDate;

        public TopicBuilder setName(final String name) {
            this.name = name;
            return this;
        }

        public TopicBuilder setDate(final Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public TopicBuilder setDate() {
            this.createdDate = realTimeService.now();
            return this;
        }

        public TopicBuilder setUserId(UserId userId) {
            this.userId = userId;
            return this;
        }

        public Topic build() {
            return new Topic(new TopicId(), this.name, this.createdDate, this.userId);
        }

    }
}
