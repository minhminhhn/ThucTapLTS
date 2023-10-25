package store.polyfood.thuctap.services;

import store.polyfood.thuctap.models.entities.VoucherUser;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.VoucherUserRepo;

import java.util.List;

public interface IVoucherUserService {
    public Response<List<VoucherUser>> getAllVoucher();
    public Response<VoucherUser> getById(int id);
}
