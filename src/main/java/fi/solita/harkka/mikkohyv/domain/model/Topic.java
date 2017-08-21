package fi.solita.harkka.mikkohyv.domain.model;

import fi.solita.harkka.mikkohyv.domain.shared.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

import static java.lang.System.in;

@Entity
public class Topic extends BaseEntity<TopicId> {
    private String name;
    private Date createdDate;

    @OneToMany(mappedBy="messageTopic")
    private List<Message> messages;

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

    public void addMessage(Message message) {
        if(messages == null){ messages = new ArrayList<Message>(); }
        messages.add(message);
    }

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
