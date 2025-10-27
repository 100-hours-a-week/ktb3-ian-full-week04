package ktb3.full.community.infrastructure.database.identifier;

public interface IdentifierGenerator<T, ID> {

    ID generate(T entity);
}
