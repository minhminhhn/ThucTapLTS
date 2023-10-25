package store.polyfood.thuctap.services;

import store.polyfood.thuctap.models.entities.Voucher;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.List;

public interface IVoucherService {
    public Response<List<Voucher>> getAllVouchers();
    public Response<Voucher> getVoucherByCode(String voucherCode);
}
