package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.Payment;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.PaymentRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public Response createNew(Payment request) {
        request.setCreatedAt(LocalDateTime.now());
        paymentRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");

    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<Payment> pagedData = paymentRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all payment success", responseData);
    }

    @Override
    public Response update(Payment request) {
        Payment payment = paymentRepo.findById(request.getPaymentId()).orElse(null);
        if (payment == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Payment not found", null);
        }
        request.setCreatedAt(payment.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        paymentRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        Payment payment = paymentRepo.findById(id).orElse(null);
        if (payment == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Payment not found", null);
        }
        paymentRepo.delete(payment);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<Payment> getById(int id) {
        Payment payment = paymentRepo.findById(id).orElse(null);
        if (payment == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Payment not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", payment);
    }
}
