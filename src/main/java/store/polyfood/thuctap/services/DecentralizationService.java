package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.Account;
import store.polyfood.thuctap.models.entities.Decentralization;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.DecentralizationRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class DecentralizationService implements IDecentralizationService {

    @Autowired
    private DecentralizationRepo decentralizationRepo;


    @Override
    public Response createNew(Decentralization request) {
        request.setCreatedAt(LocalDateTime.now());
        decentralizationRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Decentralization> pagedData = decentralizationRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all decentralization success", responseData);
    }

    @Override
    public Response update(Decentralization request) {
        Decentralization decentralization = decentralizationRepo.findById(request.getDecentralizationId()).orElse(null);
        if (decentralization == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Decentralization not found", null);
        }
        request.setUpdatedAt(LocalDateTime.now());
        request.setCreatedAt(decentralization.getCreatedAt());
        decentralizationRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        Decentralization decentralization = decentralizationRepo.findById(id).orElse(null);
        if (decentralization == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Decentralization not found", null);
        }
        decentralizationRepo.delete(decentralization);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<Decentralization> getById(int id) {
        Decentralization decentralization = decentralizationRepo.findById(id).orElse(null);
        if (decentralization == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Decentralization not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(), 200,
                null, "Success", decentralization);
    }

    @Override
    public Response<Set<Account>> getAccount(int id) {
        Decentralization decentralization = decentralizationRepo.findById(id).orElse(null);
        if (decentralization == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Decentralization not found", null);
        }
            return new Response<>(LocalDateTime.now().toString(), 200,
                    null, "Success", decentralization.getAccounts());
    }
}
