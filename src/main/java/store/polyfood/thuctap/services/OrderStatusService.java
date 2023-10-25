package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.OrderStatus;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.OrderStatusRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderStatusService implements IOrderStatusService {

    @Autowired
    private OrderStatusRepo orderStatusRepo;


    @Override
    public Response createNew(OrderStatus request) {
        orderStatusRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null, "Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<OrderStatus> pagedData = orderStatusRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all order status success", responseData);
    }

    @Override
    public Response update(OrderStatus request) {
        OrderStatus orderStatus = orderStatusRepo.findById(request.getOrderStatusId()).orElse(null);
        if (orderStatus == null) {
            return new Response<>(LocalDateTime.now().toString(),
                    404, "Order status not found", null);
        }
        orderStatusRepo.save(request);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Update success");
    }

    @Override
    public Response delete(int id) {
        OrderStatus orderStatus = orderStatusRepo.findById(id).orElse(null);
        if (orderStatus == null) {
            return new Response<>(LocalDateTime.now().toString(),
                    404, "Order status not found", null);
        }
        orderStatusRepo.delete(orderStatus);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");

    }

    @Override
    public Response<OrderStatus> getById(int id) {
        OrderStatus orderStatus = orderStatusRepo.findById(id).orElse(null);
        if (orderStatus == null) {
            return new Response<>(LocalDateTime.now().toString(),
                    404, "Order status not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", orderStatus);
    }
}
