package store.polyfood.thuctap.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.polyfood.thuctap.models.entities.CartItem;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.services.CartItemService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping(value = "api/cartitem")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                    (jsonElement, type, jsonDeserializationContext) ->
                            LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(),
                                    DateTimeFormatter.ISO_DATE_TIME))
            .create();

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> create(@RequestBody String request) {
        CartItem cartItem = gson.fromJson(request, CartItem.class);
        Response response =cartItemService.createNew(cartItem);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Object>>> getAll(@RequestParam(defaultValue = "0") int page ,
                                                                @RequestParam(defaultValue = "10") int pageSize) {
        Response<Map<String, Object>> response = cartItemService.getAll(page, pageSize);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> update(@RequestBody String request) {
        CartItem cartItem = gson.fromJson(request, CartItem.class);
        Response response = cartItemService.update(cartItem);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Response> delete(@RequestParam int id) {
        Response response = cartItemService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public ResponseEntity<Response<CartItem>> getById(@RequestParam int id) {
        Response<CartItem> response = cartItemService.getById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/updatequantity", method = RequestMethod.PUT)
    public ResponseEntity<Response> updateQuantity(@RequestParam int cartItemId,
                                                   @RequestParam int quantity){
        Response response = cartItemService.updateQuantity(cartItemId, quantity);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/gettotal", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Object>>> getTotalPrice(@RequestParam int id) {
        Response<Map<String, Object>> response = cartItemService.getTotalPrice(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
