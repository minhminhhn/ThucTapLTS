package store.polyfood.thuctap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.polyfood.thuctap.models.entities.Orders;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer> {
    List<Orders> findAllByUserId(int userId);
}
