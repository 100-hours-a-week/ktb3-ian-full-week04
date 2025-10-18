package ktb3.full.week04.infrastructure.database.identifier;

public interface IdentifierGenerator<T, ID> {

    ID generate(T entity);
}
