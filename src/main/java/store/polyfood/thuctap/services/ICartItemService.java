package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import store.polyfood.thuctap.models.entities.CartItem;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.Map;

public interface ICartItemService {
    public Response createNew(CartItem request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(CartItem request);
    public Response delete(int id);
    public Response<CartItem> getById(int id);
    public Response updateQuantity(int cartItemId, int newQuantity);
    public Response<Map<String, Object>> getTotalPrice(int cartItemId);
}
