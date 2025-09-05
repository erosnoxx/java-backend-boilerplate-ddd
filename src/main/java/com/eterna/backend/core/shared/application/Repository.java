package com.eterna.backend.core.shared.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface Repository<D, ID> {
    D save(D entity);
    List<D> saveBatch(Iterable<D> entities);

    void delete(D entity);
    void deleteAll(Iterable<D> entities);
    void deleteAll();

    Optional<D> findById(ID id);
    boolean existsById(ID id);

    long count();
    <C extends Criteria> long count(C criteria);

    Page<D> findAll(Pageable pageable);
    List<D> findAll();
    <C extends Criteria> Page<D> findAll(C criteria, Pageable pageable);
}
