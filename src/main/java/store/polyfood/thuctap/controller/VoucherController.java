package store.polyfood.thuctap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.polyfood.thuctap.models.entities.Voucher;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.services.VoucherService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/voucher")
public class VoucherController {
    @Autowired
    private  VoucherService voucherService;

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Response<List<Voucher>>> getAllVouchers(){
        Response<List<Voucher>> response = voucherService.getAllVouchers();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getbycode", method = RequestMethod.GET)
    public ResponseEntity<Response<Voucher>> getVoucherByCode(@RequestParam String code){
        Response<Voucher> response = voucherService.getVoucherByCode(code);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
