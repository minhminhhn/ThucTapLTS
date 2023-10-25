package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.Account;
import store.polyfood.thuctap.models.entities.Decentralization;
import store.polyfood.thuctap.models.entities.User;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.AccountRepo;
import store.polyfood.thuctap.repositories.DecentralizationRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private DecentralizationRepo decentralizationRepo;

    @Override
    public Response createNew(Account request) {
        Account account = accountRepo.findByUserName(request.getUsername());
        if(account != null) {
                return new Response<>(LocalDateTime.now().toString(),
                        409, "Username already exists", null);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setDecentralizationId(2);
        Decentralization decentralization = decentralizationRepo.findById(request.getDecentralizationId()).orElse(null);
        if (decentralization == null) {
            return new Response<>(LocalDateTime.now().toString(),
                    404, "Decentralization not found", null);
        }
        request.setDecentralization(decentralization);
        request.setCreatedAt(LocalDateTime.now());
        accountRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), HttpStatus.CREATED.value(), null, "Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Account> pagedData = accountRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all account success", responseData);
    }

    @Override
    public Response update(Account request) {
        Account account = accountRepo.findById(request.getAcountId()).orElse(null);
        if (account == null) {
            return new Response<>(LocalDateTime.now().toString(),
                    404, "Account not found", null);
        }
        Decentralization decentralization = decentralizationRepo.findById(request.getDecentralizationId()).orElse(null);
        if (decentralization == null) {
            return new Response<>(LocalDateTime.now().toString(),
                    404, "Decentralization not found", null);
        }
        request.setDecentralization(decentralization);
        request.setPassword(account.getPassword());
        request.setCreatedAt(account.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        accountRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        Account account = accountRepo.findById(id).orElse(null);
        if (account == null) {
            return new Response<>(LocalDateTime.now().toString(),
                    404, "Account not found", null);
        }
        accountRepo.delete(account);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<Account> getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) loadUserByUsername(username);
        if (account == null) {
            return new Response<>(LocalDateTime.now().toString(),
                    404, "Account not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(), 200,
                null, "Success", account);
    }

    @Override
    public Response<Map<String, Integer>> getStatus(int id) {
        Account account = accountRepo.findById(id).orElse(null);
        if (account == null) {
            return new Response<>(LocalDateTime.now().toString(),
                    404, "Account not found", null);
        }
        Map<String, Integer> status = new HashMap<>();
        status.put("status", account.getStatus());
        return new Response<>(LocalDateTime.now().toString(), 200,
                null, "Success", status);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findByUserName(username);

        if (account == null) {
            throw new UsernameNotFoundException("Account not found: " + username);
        }
        return account;
    }

}
