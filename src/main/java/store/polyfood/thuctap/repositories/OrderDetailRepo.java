package store.polyfood.thuctap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.polyfood.thuctap.models.entities.OrderDetail;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
}
