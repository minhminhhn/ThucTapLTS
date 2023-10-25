package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.Account;
import store.polyfood.thuctap.models.entities.Carts;
import store.polyfood.thuctap.models.entities.User;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.CartRepo;
import store.polyfood.thuctap.repositories.UserRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    AccountService accountService;

    @Override
    public Response createNew(Carts request) {
//        User user = userRepo.findById(request.getUserId()).orElse(null);
//        if (user == null) {
//            return  new Response<>(LocalDateTime.now().toString(),
//                    404, "User not found", null);
//        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        User user = userRepo.findByAccountId(account.getAcountId());
        request.setUser(user);
        request.setCreatedAt(LocalDateTime.now());
        cartRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Carts> pagedData = cartRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all cart success", responseData);
    }
    @Override
    public Response update(Carts request) {
        Carts carts = cartRepo.findById(request.getCartId()).orElse(null);
        if (carts == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Cart not found", null);
        }
        User user = userRepo.findById(request.getUserId()).orElse(null);
        if (user == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "User not found", null);
        }
        request.setUser(user);
        request.setCreatedAt(carts.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        cartRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        Carts carts = cartRepo.findById(id).orElse(null);
        if (carts == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Cart not found", null);
        }
        cartRepo.delete(carts);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<Carts> getCart() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        User user = userRepo.findByAccountId(account.getAcountId());
        Carts carts = cartRepo.findAllByUserId(user.getUserId());
//        if (carts.isEmpty()) {
//            return  new Response<>(LocalDateTime.now().toString(),
//                    404, "Cart not found", null);
//        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", carts);
    }
}
