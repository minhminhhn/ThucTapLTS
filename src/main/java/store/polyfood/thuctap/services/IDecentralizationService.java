package store.polyfood.thuctap.services;

import org.springframework.http.ResponseEntity;
import store.polyfood.thuctap.models.entities.Account;
import store.polyfood.thuctap.models.entities.Decentralization;
import store.polyfood.thuctap.models.responobject.Response;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public interface IDecentralizationService {
    public Response createNew(Decentralization request);
    public Response<Map<String, Object>> getAll(int page, int pageSize);
    public Response update(Decentralization request);
    public Response delete(int id);
    public Response<Decentralization> getById(int id);
    public Response<Set<Account>> getAccount(int id);
}
