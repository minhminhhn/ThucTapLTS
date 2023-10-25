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
import store.polyfood.thuctap.repositories.CartItemRepo;
import store.polyfood.thuctap.repositories.CartRepo;
import store.polyfood.thuctap.repositories.ProductRepo;
import store.polyfood.thuctap.repositories.UserRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartItemService implements ICartItemService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductRepo productRepo;


    @Override
    public Response createNew(CartItem request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        User user = userRepo.findByAccountId(account.getAcountId());
        Carts carts = cartRepo.findAllByUserId(user.getUserId());
        if (carts == null) {
            Carts carts1 = new Carts();
            carts1.setUser(user);
            carts1.setCreatedAt(LocalDateTime.now());
            cartRepo.save(carts1);
            carts = carts1;
        }
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }

        request.setCarts(carts);
        request.setProduct(product);
        request.setCreatedAt(LocalDateTime.now());
        cartItemRepo.save(request);
        carts.setUpdatedAt(LocalDateTime.now());
        cartRepo.save(carts);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<CartItem> pagedData = cartItemRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all cart item success", responseData);
    }

    @Override
    public Response update(CartItem request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        User user = userRepo.findByAccountId(account.getAcountId());
        CartItem cartItem = cartItemRepo.findById(request.getCartItemId()).orElse(null);
        if (cartItem == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Cart item not found", null);
        }
        Carts carts  = cartItem.getCarts();
        if(!user.getCarts().contains(carts)) {
            return  new Response<>(LocalDateTime.now().toString(),
                    403, "Forbidden", null);
        }
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        request.setProduct(product);
        request.setCarts(carts);
        request.setCreatedAt(cartItem.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        cartItemRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        User user = userRepo.findByAccountId(account.getAcountId());
        CartItem cartItem = cartItemRepo.findById(id).orElse(null);
        if (cartItem == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Cart item not found", null);
        }
        Carts carts  = cartItem.getCarts();
        if(!user.getCarts().contains(carts)) {
            return  new Response<>(LocalDateTime.now().toString(),
                    403, "Forbidden", null);
        }
        cartItemRepo.delete(cartItem);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<CartItem> getById(int id) {
        CartItem cartItem = cartItemRepo.findById(id).orElse(null);
        if (cartItem == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Cart item not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", cartItem);
    }

    @Override
    public Response updateQuantity(int cartItemId, int newQuantity) {
        CartItem cartItem = cartItemRepo.findById(cartItemId).orElse(null);
        if (cartItem == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Cart item not found", null);
        }
        cartItem.setUpdatedAt(LocalDateTime.now());
        cartItem.setQuantity(newQuantity);
        cartItemRepo.save(cartItem);
        return new Response(LocalDateTime.now().toString(), 200,
                null, "Updated cart item successfully");
    }

    @Override
    public Response<Map<String, Object>> getTotalPrice(int cartItemId) {
        CartItem cartItem = cartItemRepo.findById(cartItemId).orElse(null);
        if (cartItem == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Cart item not found", null);
        }
        Double price = (cartItem.getProduct().getPrice() * (100 - cartItem.getProduct().getDiscount())/100);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cartItemId", cartItemId);
        result.put("price", price);
        return new Response<>(LocalDateTime.now().toString(), 200,
                null, "Successfully", result);
    }
}
