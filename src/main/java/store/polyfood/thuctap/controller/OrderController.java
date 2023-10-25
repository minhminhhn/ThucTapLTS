package store.polyfood.thuctap.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import store.polyfood.thuctap.models.entities.OrderDetail;
import store.polyfood.thuctap.models.entities.Orders;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.services.OrderService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                    (jsonElement, type, jsonDeserializationContext) ->
                            LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(),
                                    DateTimeFormatter.ISO_DATE_TIME))
            .create();

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> create(@RequestBody String request) throws UnsupportedEncodingException {
        Orders orders = gson.fromJson(request, Orders.class);
        Response response = orderService.createNew(orders);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Object>>> getAll(@RequestParam(defaultValue = "0") int page ,
                                                                @RequestParam(defaultValue = "10") int pageSize) {
        Response<Map<String, Object>> response = orderService.getAll(page, pageSize);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> update(@RequestBody String request) {
        Orders orders = gson.fromJson(request, Orders.class);
        Response response = orderService.update(orders);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

//    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//    public ResponseEntity<Response> delete(@RequestParam int id) {
//        Response response = orderService.delete(id);
//        return ResponseEntity.status(response.getStatus()).body(response);
//    }

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public ResponseEntity<Response<Orders>> getById(@RequestParam int id) {
        Response<Orders> response = orderService.getById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/updatepayment", method = RequestMethod.PUT)
    public ResponseEntity<Response> updatePayment(@RequestParam int id,
                                                  @RequestParam int paymentId) {
        Response response = orderService.updatePayment(id, paymentId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/updatestatusorder", method = RequestMethod.PUT)
    public ResponseEntity<Response> updateStatusOrder(@RequestParam int id,
                                                  @RequestParam int statusOrderId) {
        Response response = orderService.updateStatus(id, statusOrderId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @RequestMapping(value = "/getorderdetails", method = RequestMethod.GET)
    public ResponseEntity<Response<Set<OrderDetail>>> getOrderDetails(@RequestParam int id) {
        Response<Set<OrderDetail>> response = orderService.getOrderDetails(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getorders", method = RequestMethod.GET)
    public ResponseEntity<Response<List<Orders>>> getOrders() {
        return ResponseEntity.status(200).body(orderService.getOrders());
    }
}
