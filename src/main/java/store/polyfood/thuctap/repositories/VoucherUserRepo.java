package store.polyfood.thuctap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.polyfood.thuctap.models.entities.VoucherUser;

import java.util.List;

@Repository
public interface VoucherUserRepo extends JpaRepository<VoucherUser, Integer> {
    List<VoucherUser> findAllByUserId(int userId);
}
