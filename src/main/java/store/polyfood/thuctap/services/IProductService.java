package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import store.polyfood.thuctap.models.entities.Product;
import store.polyfood.thuctap.models.entities.ProductImage;
import store.polyfood.thuctap.models.entities.ProductType;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IProductService {
    public Response createNew(Product request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(Product request);
    public Response delete(int id);
    public Response<Product> getById(int id);
    public Response<Map<String, Double>> getFinalPrice(int id);
    public Response<Set<ProductImage>> getProductImages(int id);
    public Response<Map<String, Double>> getTotalPointReview(int id);
    public Response<Map<String, Object>> getNumberOfView(int productId);
    public Response<Set<Product>> relatedProducts(int productId);
}
