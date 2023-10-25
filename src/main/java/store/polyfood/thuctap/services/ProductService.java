package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.*;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.ProductRepo;
import store.polyfood.thuctap.repositories.ProductTypeRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ProductTypeRepo productTypeRepo;

    @Override
    public Response createNew(Product request) {
        ProductType productType = productTypeRepo.findById(request.getProductTypeId()).orElse(null);
        if (productType == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product type not found", null);
        }
        request.setProductType(productType);
        request.setCreatedAt(LocalDateTime.now());
        productRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Product> pagedData = productRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all product success", responseData);
    }

    @Override
    public Response update(Product request) {
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        ProductType productType = productTypeRepo.findById(request.getProductTypeId()).orElse(null);
        if (productType == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product type not found", null);
        }
        request.setProductType(productType);
        request.setCreatedAt(product.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        productRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        productRepo.delete(product);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<Product> getById(int id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        product.setNumberOfView(product.getNumberOfView()+1);
        productRepo.save(product);
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", product);
    }

    @Override
    public Response<Map<String, Double>> getFinalPrice(int id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        Map<String, Double> finalPrice = new HashMap<>();
        finalPrice.put("price", product.getPrice()*(100-product.getDiscount())/100);
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", finalPrice);
    }

    @Override
    public Response<Set<ProductImage>> getProductImages(int id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", product.getProductImages());
    }

    @Override
    public Response<Map<String, Double>> getTotalPointReview(int id) {
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        double sumPoint = 0;
        for(ProductReview productReview : product.getProductReviews()) {
            sumPoint += productReview.getPointEvaluation();
        }
        Map<String, Double> resultPoint = new HashMap<>();
        resultPoint.put("totalPoint", sumPoint/product.getProductReviews().size());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", resultPoint);
    }

    @Override
    public Response<Map<String, Object>> getNumberOfView(int productId) {
        Product product = productRepo.findById(productId).orElse(null);
        if(product == null) {
            return new Response<>(LocalDateTime.now().toString(), HttpStatus.NOT_FOUND.value(),
                    "Product not found", null);
        }
        Map<String , Object> resutl = new HashMap<>();
        resutl.put("productId", productId);
        resutl.put("munbreOfView", product.getNumberOfView());

        return new Response<>(LocalDateTime.now().toString(), 200, null, "Success", resutl);
    }

    @Override
    public Response<Set<Product>> relatedProducts(int productId) {
        Product product = productRepo.findById(productId).orElse(null);
        if(product == null) {
            return new Response<>(LocalDateTime.now().toString(), HttpStatus.NOT_FOUND.value(),
                    "Product not found", null);
        }
        ProductType productType = productTypeRepo.findById(product.getProductTypeId()).orElse(null);
        assert productType != null;
        Set<Product> products = productType.getProducts();
        products.remove(product);
        return new Response<>(LocalDateTime.now().toString(), 200, null, "Success", products);
    }
}
