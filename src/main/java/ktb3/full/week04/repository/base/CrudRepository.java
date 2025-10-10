package ktb3.full.week04.repository.base;

import java.util.Optional;

public interface CrudRepository<T, ID> {

    ID save(T entity);

    void saveAll(Iterable<T> entities);

    Optional<T> findById(ID id);

    void update(T entity);

    void delete(T entity);
}
