package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import store.polyfood.thuctap.models.entities.Account;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.Map;

public interface IAccountService extends UserDetailsService {
    public Response createNew(Account request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(Account request);
    public Response delete(int id);
    public Response<Account> getAccount();
    public Response<Map<String, Integer>> getStatus(int id);
}
