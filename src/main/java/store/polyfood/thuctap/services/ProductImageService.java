package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.Product;
import store.polyfood.thuctap.models.entities.ProductImage;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.ProductImageRepo;
import store.polyfood.thuctap.repositories.ProductRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductImageService implements IProductImageService {

    @Autowired
    private ProductImageRepo productImageRepo;
    @Autowired
    private ProductRepo productRepo;

    @Override
    public Response createNew(ProductImage request) {
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        request.setProduct(product);
        request.setCreatedAt(LocalDateTime.now());
        productImageRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<ProductImage> pagedData = productImageRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all product image success", responseData);
    }

    @Override
    public Response update(ProductImage request) {
        ProductImage productImage = productImageRepo.findById(request.getProductImageId()).orElse(null);
        if (productImage == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product image not found", null);
        }
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        request.setProduct(product);
        request.setCreatedAt(productImage.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        productImageRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        ProductImage productImage = productImageRepo.findById(id).orElse(null);
        if (productImage == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product image not found", null);
        }
        productImageRepo.delete(productImage);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<ProductImage> getById(int id) {
        ProductImage productImage = productImageRepo.findById(id).orElse(null);
        if (productImage == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product image not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", productImage);
    }
}
