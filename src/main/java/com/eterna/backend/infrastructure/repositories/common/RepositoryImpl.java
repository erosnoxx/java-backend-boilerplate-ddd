package com.eterna.backend.infrastructure.repositories.common;

import com.eterna.backend.core.shared.application.Criteria;
import com.eterna.backend.core.shared.application.EntityMapper;
import com.eterna.backend.core.shared.application.Repository;
import com.eterna.backend.infrastructure.utils.CriteriaUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class RepositoryImpl<D, ID, E, R extends JpaRepository<E, ID>, C extends Criteria>
        implements Repository<D, ID, C> {

    protected final R repository;
    protected final EntityManager em;
    protected final EntityMapper<D, E> mapper;
    private final Class<E> entityClass;

    public RepositoryImpl(R repository, EntityManager em, EntityMapper<D, E> mapper, Class<E> entityClass) {
        this.repository = repository;
        this.em = em;
        this.mapper = mapper;
        this.entityClass = entityClass;
    }

    @Override
    public D save(D entity) {
        E jpaEntity = mapper.toEntity(entity);
        E saved = repository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<D> saveBatch(Iterable<D> entities) {
        List<E> toSave =
                ((List<D>) entities).stream()
                        .map(mapper::toEntity)
                        .collect(Collectors.toList());

        List<E> saved = repository.saveAll(toSave);
        return saved.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(D entity) {
        repository.delete(mapper.toEntity(entity));
    }

    @Override
    public void deleteAll(Iterable<D> entities) {
        List<E> toDelete =
                ((List<D>) entities).stream()
                        .map(mapper::toEntity)
                        .collect(Collectors.toList());
        repository.deleteAll(toDelete);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Optional<D> findById(ID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long count(C criteria) {
        var cb = em.getCriteriaBuilder();
        var countQuery = cb.createQuery(Long.class);
        var root = countQuery.from(entityClass); // entityClass precisa ser passado no construtor

        countQuery.select(cb.count(root))
                .where(CriteriaUtils.buildPredicates(criteria, root, cb));

        return em.createQuery(countQuery).getSingleResult();
    }

    @Override
    public Page<D> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public List<D> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<D> findAll(C criteria, Pageable pageable) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(entityClass);
        var root = query.from(entityClass);

        query.where(CriteriaUtils.buildPredicates(criteria, root, cb));

        var typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<D> content = typedQuery.getResultList().stream()
                .map(mapper::toDomain)
                .toList();

        long total = count(criteria);

        return new PageImpl<>(content, pageable, total);
    }
}
