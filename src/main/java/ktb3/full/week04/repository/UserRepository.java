package ktb3.full.week04.repository;

public interface UserRepository {

    boolean existsByEmail(String email);
}
