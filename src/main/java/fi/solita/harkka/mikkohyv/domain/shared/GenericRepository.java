package fi.solita.harkka.mikkohyv.domain.shared;

public interface GenericRepository<I extends Identity, E extends BaseEntity<I>> {
    E findById(I entityId);
    void store(E entity);
    void delete(E entity);
    I generateId();
}
