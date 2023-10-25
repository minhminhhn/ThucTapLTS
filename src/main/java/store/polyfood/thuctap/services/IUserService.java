package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import store.polyfood.thuctap.models.entities.ProductReview;
import store.polyfood.thuctap.models.entities.User;
import store.polyfood.thuctap.models.entities.VoucherUser;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.Map;
import java.util.Set;

public interface IUserService {
    public Response createNew(User request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(User request);
    public Response<User> getUser();
    public Response<User> findByName(String fullName);
    public Response<Set<ProductReview>> getProductReview(int id);
    public Response<Map<String, Double>> getTotalPriceInCart(int id);
    public Response<Set<VoucherUser>> getAllVouchers(int id);
}
