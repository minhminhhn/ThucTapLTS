package store.polyfood.thuctap.controller;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.polyfood.thuctap.models.entities.ProductReview;
import store.polyfood.thuctap.models.entities.User;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.services.UserService;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                    (jsonElement, type, jsonDeserializationContext) ->
                            LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(),
                                    DateTimeFormatter.ISO_DATE_TIME))
            .create();

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> create(@RequestBody String request) {
        User user = gson.fromJson(request, User.class);
        Response response = userService.createNew(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Object>>> getAll(@RequestParam(defaultValue = "0") int page ,
                                                                @RequestParam(defaultValue = "10") int pageSize) {
        Response<Map<String, Object>> response = userService.getAll(page, pageSize);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> update(@RequestBody String request) {
        User user = gson.fromJson(request, User.class);
        Response response = userService.update(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    public ResponseEntity<Response<User>> getById() {
        Response<User> response = userService.getUser();
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/findbyname", method = RequestMethod.GET)
    public ResponseEntity<Response<User>> findByName(@RequestParam String fullName) {
        Response<User> response = userService.findByName(fullName);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getproductreview", method = RequestMethod.GET)
    public ResponseEntity<Response<Set<ProductReview>>> getProductReview(@RequestParam int id) {
        Response<Set<ProductReview>> response = userService.getProductReview(id);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/gettotalprice", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Double>>> getTotalPrice(@RequestParam int id) {
        Response<Map<String, Double>> response = userService.getTotalPriceInCart(id);
        return  ResponseEntity.status(response.getStatus()).body(response);
    }
}
