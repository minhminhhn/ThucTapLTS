package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import store.polyfood.thuctap.models.entities.Product;
import store.polyfood.thuctap.models.entities.ProductType;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.ProductTypeRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ProductTypeService implements IProductTypeService{

    @Autowired
    private ProductTypeRepo productTypeRepo;

    @Override
    public Response createNew(ProductType request) {
        request.setCreatedAt(LocalDateTime.now());
        productTypeRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<ProductType> pagedData = productTypeRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all product type success", responseData);
    }

    @Override
    public Response update(ProductType request) {
        ProductType productType = productTypeRepo.findById(request.getProductTypeId()).orElse(null);
        if (productType == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product type not found", null);
        }
        request.setCreatedAt(productType.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        productTypeRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        ProductType productType = productTypeRepo.findById(id).orElse(null);
        if (productType == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product type not found", null);
        }
        productTypeRepo.delete(productType);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<ProductType> getById(int id) {
        ProductType productType = productTypeRepo.findById(id).orElse(null);
        if (productType == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product type not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", productType);
    }

    @Override
    public Response<Set<Product>> getProducts(int id) {
        ProductType productType = productTypeRepo.findById(id).orElse(null);
        if (productType == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product type not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", productType.getProducts());
    }
}
