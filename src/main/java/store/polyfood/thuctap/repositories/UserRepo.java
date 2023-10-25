package store.polyfood.thuctap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.polyfood.thuctap.models.entities.User;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByFullName(String fullName);
    public User findByEmail(String email);

    public User findByAccountId(int accountId);
    
}
