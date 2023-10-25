package store.polyfood.thuctap.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.polyfood.thuctap.models.entities.OrderDetail;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.services.OrderDetailService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping(value = "api/orderdetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                    (jsonElement, type, jsonDeserializationContext) ->
                            LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(),
                                    DateTimeFormatter.ISO_DATE_TIME))
            .create();

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> create(@RequestBody String request) {
        OrderDetail orderDetail = gson.fromJson(request, OrderDetail.class);
        Response response = orderDetailService.createNew(orderDetail);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Object>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int pageSize) {
        Response<Map<String, Object>> response = orderDetailService.getAll(page, pageSize);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> update(@RequestBody String request) {
        OrderDetail orderDetail = gson.fromJson(request, OrderDetail.class);
        Response response = orderDetailService.update(orderDetail);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Response> delete(@RequestParam int id) {
        Response response = orderDetailService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public ResponseEntity<Response<OrderDetail>> getById(@RequestParam int id) {
        Response<OrderDetail> response = orderDetailService.getById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
