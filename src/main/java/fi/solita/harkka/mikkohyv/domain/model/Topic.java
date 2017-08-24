package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.BaseEntity;
import org.hibernate.annotations.SortNatural;
import javax.persistence.*;
import java.util.*;

@Entity
public class Topic extends BaseEntity<TopicId> {
    private String name;
    private Date createdDate;
    private Date latestMessageDate;

    @SortNatural
    @OrderBy("created_date DESC")
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy="messageTopic", orphanRemoval=true)
    private List<Message> messages = new ArrayList<>();

    protected Topic() {
    }

    public Topic(TopicId id, String topicName) {
        super(id);
        this.name = topicName;
    }

    public String name() {  return this.name; }

    public boolean updateName(String topicName){
        if (messages.size() > 0)
            return false;
        this.name = topicName;
        return true;
    }

    public Date latestMessageDate() {
        return this.latestMessageDate;
    }

    public Date createdDate() {
        return this.createdDate;
    }

    public void setCreatedDate(final Date createdDate){
        this.createdDate = createdDate;
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
}
