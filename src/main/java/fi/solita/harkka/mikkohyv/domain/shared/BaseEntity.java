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

    // cast is okay since type parameter I must be subclass of Identity anyway
    @SuppressWarnings("unchecked")
    public I getId() {
        return (I)id;
    }
}
