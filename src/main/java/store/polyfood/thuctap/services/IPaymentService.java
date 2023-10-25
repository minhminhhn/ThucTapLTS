package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import store.polyfood.thuctap.models.entities.Payment;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.Map;

public interface IPaymentService {
    public Response createNew(Payment request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(Payment request);
    public Response delete(int id);
    public Response<Payment> getById(int id);

}
