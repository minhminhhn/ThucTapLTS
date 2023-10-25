package store.polyfood.thuctap.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.polyfood.thuctap.models.entities.Product;
import store.polyfood.thuctap.models.entities.ProductImage;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.services.ProductService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                    (jsonElement, type, jsonDeserializationContext) ->
                            LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(),
                                    DateTimeFormatter.ISO_DATE_TIME))
            .create();

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> create(@RequestBody String request) {
        Product product = gson.fromJson(request, Product.class);
        Response response = productService.createNew(product);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Object>>> getAll(@RequestParam(defaultValue = "0") int page ,
                                                                @RequestParam(defaultValue = "10") int pageSize) {
        Response<Map<String, Object>> response = productService.getAll(page, pageSize);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> update(@RequestBody String request) {
        Product product = gson.fromJson(request, Product.class);
        Response response = productService.update(product);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Response> delete(@RequestParam int id) {
        Response response = productService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public ResponseEntity<Response<Product>> getById(@RequestParam int id) {
        Response<Product> response = productService.getById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getfinalfrice", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Double>>> getFinalPrice(@RequestParam int id) {
        Response<Map<String, Double>> response = productService.getFinalPrice(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getproductimage", method = RequestMethod.GET)
    public ResponseEntity<Response<Set<ProductImage>>> getProductImage(@RequestParam int id) {
        Response<Set<ProductImage>> response = productService.getProductImages(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/numberofview", method = RequestMethod.GET)
    public ResponseEntity<Response<Map<String, Object>>> getNumberOfViews(@RequestParam int productId) {
        Response<Map<String, Object>> response = productService.getNumberOfView(productId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/relatedproducts", method = RequestMethod.GET)
    public ResponseEntity<Response<Set<Product>>> relatedProducts(@RequestParam int productId) {
        Response<Set<Product>> response = productService.relatedProducts(productId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
