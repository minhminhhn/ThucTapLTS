package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import store.polyfood.thuctap.models.entities.ProductReview;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.Map;

public interface IProductReviewService {
    public Response createNew(ProductReview request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(ProductReview request);
    public Response delete(int id);
    public Response<ProductReview> getById(int id);
}
