package store.polyfood.thuctap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.polyfood.thuctap.models.entities.Carts;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Carts, Integer> {

    Carts findAllByUserId(int userId);
}
