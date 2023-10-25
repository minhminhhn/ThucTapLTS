package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import store.polyfood.thuctap.models.entities.OrderStatus;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.Map;

public interface IOrderStatusService {
    public Response createNew(OrderStatus request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(OrderStatus request);
    public Response delete(int id);
    public Response<OrderStatus> getById(int id);

}
