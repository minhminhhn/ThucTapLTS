package store.polyfood.thuctap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.polyfood.thuctap.models.entities.Voucher;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, Integer> {
    Voucher findByVoucherCode(String voucherCode);
}
