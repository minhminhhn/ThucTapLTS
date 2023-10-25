package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import store.polyfood.thuctap.models.entities.ProductImage;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.Map;

public interface IProductImageService {
    public Response createNew(ProductImage request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(ProductImage request);
    public Response delete(int id);
    public Response<ProductImage> getById(int id);
}
