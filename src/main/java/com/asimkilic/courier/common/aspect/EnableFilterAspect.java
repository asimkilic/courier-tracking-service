package com.asimkilic.courier.common.aspect;

import com.asimkilic.courier.common.annotation.EnableDefaultFilter;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class EnableFilterAspect {

    private final EntityManager entityManager;

    @Before("@annotation(com.asimkilic.courier.common.annotation.EnableDefaultFilter) && @annotation(enableDefaultFilter)")
    public void enableFilters(EnableDefaultFilter enableDefaultFilter) {
        Session session = entityManager.unwrap(Session.class);
        String[] filters = enableDefaultFilter.filterNames();
        for (String filter : filters) {
            session.enableFilter(filter);
        }
    }
}
