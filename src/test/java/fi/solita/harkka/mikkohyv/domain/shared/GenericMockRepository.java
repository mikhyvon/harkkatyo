package fi.solita.harkka.mikkohyv.domain.shared;

import java.util.HashMap;

public abstract class GenericMockRepository<I extends Identity, E extends BaseEntity<I>> implements GenericRepository<I,E> {
    private final HashMap<I, E> store = new HashMap<>();

    @Override
    public E findById(I entityId) {
        return store.get(entityId);
    }

    @Override
    public void store(E entity) {
        store.put(entity.getId(), entity);
    }

    @Override
    public void delete(E entity) { store.remove(entity.getId(), entity); }

}
