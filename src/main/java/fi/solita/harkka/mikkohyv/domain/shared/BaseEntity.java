package fi.solita.harkka.mikkohyv.domain.shared;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<I extends Identity> {
    @EmbeddedId
    private Identity id;

    protected BaseEntity() {
    }

    public BaseEntity(I id) {
        this.id = id;
    }

    public I getId() {
        return (I)id;
    }
}
