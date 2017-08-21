package fi.solita.harkka.mikkohyv.domain.shared;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@MappedSuperclass
public class Identity implements Serializable {
    @Column(columnDefinition = "uuid")
    protected final UUID id;

    protected Identity() {
        id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identity)) return false;

        Identity identity = (Identity) o;

        return id.equals(identity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
