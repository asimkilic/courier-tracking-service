package com.asimkilic.courier.common.repository;

import com.asimkilic.courier.common.entity.BaseEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.util.Assert;

@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseEntity<I>, I> extends JpaRepository<T, I> {

    @Override
    @Query("select e from #{#entityName} e where e.id =:id and e.deleted = false")
    Optional<T> findById(@Param("id") I id);

    @Override
    @Modifying
    @Query("update #{#entityName} e set e.deleted=true where e.id =:id")
    void deleteById(@Param("id") I id);


    @Override
    default void delete(T entity) {
        Assert.notNull(entity, "The entity must not be null!");
        deleteById(entity.getId());
    }

    @Override
    default void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }


    @Override
    @Modifying
    @Query("update #{#entityName} e set e.deleted=true")
    void deleteAll();

    default void hardDelete(T entity) {
        Assert.notNull(entity, "The entity must not be null!");
        hardDeleteById(entity.getId());
    }

    @Modifying
    @Query("delete #{#entityName} e where e.id =:id")
    void hardDeleteById(@Param("id") I id);
}
