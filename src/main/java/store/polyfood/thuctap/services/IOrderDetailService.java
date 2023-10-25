package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import store.polyfood.thuctap.models.entities.OrderDetail;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.Map;

public interface IOrderDetailService {
    public Response createNew(OrderDetail request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(OrderDetail request);
    public Response delete(int id);
    public Response<OrderDetail> getById(int id);
}
