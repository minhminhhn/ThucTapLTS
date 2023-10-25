package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.*;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.OrderDetailRepo;
import store.polyfood.thuctap.repositories.OrderRepo;
import store.polyfood.thuctap.repositories.ProductRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderDetailService implements IOrderDetailService{

    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;


    @Override
    public Response createNew(OrderDetail request) {
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        Orders orders = orderRepo.findById(request.getOrderId()).orElse(null);
        if (orders == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Order not found", null);
        }
        request.setPriceTotal(request.getQuantity() * (request.getProduct().getPrice() *
                (100 - request.getProduct().getDiscount())/100));
        request.setProduct(product);
        request.setOrders(orders);
        request.setCreatedAt(LocalDateTime.now());

        orderDetailRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");
    }

    @Override
    public Response<Map<String, Object>> getAll(int page, int pageSize) {
        Pageable pageRequest = PageRequest.of(page, pageSize);
        Page<OrderDetail> pagedData = orderDetailRepo.findAll(pageRequest);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", pagedData.getContent());
        responseData.put("currentPage", pagedData.getNumber());
        responseData.put("totalItems", pagedData.getTotalElements());
        responseData.put("totalPages", pagedData.getTotalPages());
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Get all order detail success", responseData);
    }

    @Override
    public Response update(OrderDetail request) {
        OrderDetail orderDetail = orderDetailRepo.findById(request.getOrderDetailId()).orElse(null);
        if (orderDetail == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Order detail not found", null);
        }
        Product product = productRepo.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Product not found", null);
        }
        Orders orders = orderRepo.findById(request.getOrderId()).orElse(null);
        if (orders == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Order not found", null);
        }
        request.setProduct(product);
        request.setOrders(orders);
        request.setCreatedAt(orderDetail.getCreatedAt());
        request.setUpdatedAt(LocalDateTime.now());
        orderDetailRepo.save(request);
        return new Response<>(LocalDateTime.now().toString(), 200, null ,"Success");

    }

    @Override
    public Response delete(int id) {
        OrderDetail orderDetail = orderDetailRepo.findById(id).orElse(null);
        if (orderDetail == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Order detail not found", null);
        }
        orderDetailRepo.delete(orderDetail);
        return new Response(LocalDateTime.now().toString(),
                200, null, "Delete success");
    }

    @Override
    public Response<OrderDetail> getById(int id) {
        OrderDetail orderDetail = orderDetailRepo.findById(id).orElse(null);
        if (orderDetail == null) {
            return  new Response<>(LocalDateTime.now().toString(),
                    404, "Order detail not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", orderDetail);
    }
}
