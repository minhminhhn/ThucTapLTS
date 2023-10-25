package store.polyfood.thuctap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.polyfood.thuctap.models.entities.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    Account findByUserName(String userName);

    Account findByResetPasswordToken (String resetPasswordToken);
}
