package com.eterna.backend.infrastructure.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CriteriaUtils {

    public static <E, C> Predicate[] buildPredicates(
            C criteria,
            Root<E> root,
            CriteriaBuilder cb
    ) {
        List<Predicate> predicates = new ArrayList<>();

        for (Method method : criteria.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() != 0) continue;

            try {
                Object value = method.invoke(criteria);
                if (value != null) {
                    String fieldName = method.getName();
                    predicates.add(cb.equal(root.get(fieldName), value));
                }
            } catch (Exception e) {
                throw new RuntimeException("error building predicates", e);
            }
        }

        return predicates.toArray(new Predicate[0]);
    }
}
