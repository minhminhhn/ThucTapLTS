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
import store.polyfood.thuctap.repositories.AccountRepo;
import store.polyfood.thuctap.repositories.UserRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AccountService accountService;

    @Override
    public Response createNew(User request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        if (account == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Account not found", null);
        }
        request.setAccount(account);
        request.setCreatedAt(LocalDateTime.now());
        userRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<User> pagedData = userRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all user success", responseData);
    }

    @Override
    public Response update(User request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        User user = userRepo.findByAccountId(account.getAcountId());
        if (user == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "User not found", null);
        }
        request.setUserId(user.getUserId());
        request.setAccount(account);
        request.setCreatedAt(user.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        userRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        User user = userRepo.findByAccountId(account.getAcountId());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", user);
    }

    @Override
    public Response<User> findByName(String fullName) {
        User user = userRepo.findByFullName(fullName);
        if (user == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "User not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", user);
    }

    @Override
    public Response<Set<ProductReview>> getProductReview(int id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "User not found", null);
        }

        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", user.getProductReviews());
    }

    @Override
    public Response<Map<String, Double>> getTotalPriceInCart(int id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "User not found", null);
        }
        Double price = (double) 0;
        for(Carts cart : user.getCarts()) {
            for(CartItem item : cart.getCartItems()) {
                price = item.getQuantity() * (item.getProduct().getPrice() * (100 - item.getProduct().getDiscount())/100);
            }
        }
        Map<String, Double> totalPrice = new HashMap<>();
        totalPrice.put("totalPrice", price);
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", totalPrice);
    }

    @Override
    public Response<Set<VoucherUser>> getAllVouchers(int id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "User not found", null);
        }
        Set<VoucherUser> voucherUsers = user.getVoucherUsers();
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", voucherUsers);
    }
}
