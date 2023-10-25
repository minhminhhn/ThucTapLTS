package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.*;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.ProductRepo;
import store.polyfood.thuctap.repositories.ProductReviewRepo;
import store.polyfood.thuctap.repositories.UserRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service

public class ProductReviewService implements IProductReviewService{

    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductReviewRepo productReviewRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public Response createNew(ProductReview request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        User user = userRepo.findByAccountId(account.getAcountId());
        if (user == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "User not found", null);
        }
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        request.setUser(user);
        request.setProduct(product);
        request.setCreatedAt(LocalDateTime.now());
        productReviewRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");

    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<ProductReview> pagedData = productReviewRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all product review success", responseData);
    }

    @Override
    public Response update(ProductReview request) {
        ProductReview productReview = productReviewRepo.findById(request.getProductReviewId()).orElse(null);
        if (productReview == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product review not found", null);
        }
        User user = userRepo.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "User not found", null);
        }
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        request.setUser(user);
        request.setProduct(product);
        request.setCreatedAt(productReview.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        productReviewRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        ProductReview productReview = productReviewRepo.findById(id).orElse(null);
        if (productReview == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product review not found", null);
        }
        productReviewRepo.delete(productReview);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<ProductReview> getById(int id) {
        ProductReview productReview = productReviewRepo.findById(id).orElse(null);
        if (productReview == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product review not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", productReview);
    }
}
