package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import store.polyfood.thuctap.models.entities.Product;
import store.polyfood.thuctap.models.entities.ProductType;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.Map;
import java.util.Set;

public interface IProductTypeService {
    public Response createNew(ProductType request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(ProductType request);
    public Response delete(int id);
    public Response<ProductType> getById(int id);
    public Response<Set<Product>> getProducts(int id);
}
