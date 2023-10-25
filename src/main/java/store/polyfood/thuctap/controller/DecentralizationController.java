package store.polyfood.thuctap.controller;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.polyfood.thuctap.models.entities.Account;
import store.polyfood.thuctap.models.entities.Decentralization;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.services.DecentralizationService;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/decentralization")
public class DecentralizationController {

    @Autowired
    private DecentralizationService decentralizationService;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                    (jsonElement, type, jsonDeserializationContext) ->
                            LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(),
                                    DateTimeFormatter.ISO_DATE_TIME))
            .create();

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> create(@RequestBody String request) {
        Decentralization decentralization = gson.fromJson(request, Decentralization.class);
        Response response = decentralizationService.createNew(decentralization);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Object>>> getAll(@RequestParam(defaultValue = "0") int page ,
                                                                @RequestParam(defaultValue = "10") int pageSize) {
        Response<Map<String, Object>> response = decentralizationService.getAll(page, pageSize);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> update(@RequestBody String request) {
        Decentralization decentralization = gson.fromJson(request, Decentralization.class);
        Response response = decentralizationService.update(decentralization);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Response> delete(@RequestParam int id) {
        Response response = decentralizationService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public ResponseEntity<Response<Decentralization>> getById(@RequestParam int id) {
        Response<Decentralization> response = decentralizationService.getById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getaccount", method = RequestMethod.GET)
    public ResponseEntity<Response<Set<Account>>> getAccount(@RequestParam int id) {
        Response<Set<Account>> response = decentralizationService.getAccount(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
