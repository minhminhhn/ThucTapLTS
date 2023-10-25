package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.Voucher;
import store.polyfood.thuctap.models.entities.VoucherUser;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.VoucherRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherService implements IVoucherService{

    @Autowired
    private VoucherRepo voucherRepo;

    @Override
    public Response<List<Voucher>> getAllVouchers() {
        return new Response<>(LocalDateTime.now().toString(),
                200, null,"Success", voucherRepo.findAll());
    }

    @Override
    public Response<Voucher> getVoucherByCode(String voucherCode) {
        Voucher voucher = voucherRepo.findByVoucherCode(voucherCode);
        if(voucher == null) {
            return new Response(LocalDateTime.now().toString(),
                    404, "Voucher not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", voucher);
    }
}
