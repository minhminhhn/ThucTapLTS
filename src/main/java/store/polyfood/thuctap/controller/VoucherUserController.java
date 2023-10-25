package store.polyfood.thuctap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.polyfood.thuctap.models.entities.Voucher;
import store.polyfood.thuctap.models.entities.VoucherUser;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.services.VoucherUserService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/voucheruser")
public class VoucherUserController {
    @Autowired
    private VoucherUserService voucherUserService;

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Response<List<VoucherUser>>> getAllVouchers(){
        Response<List<VoucherUser>> response = voucherUserService.getAllVoucher();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @RequestMapping(value = "/getbycode", method = RequestMethod.GET)
    public ResponseEntity<Response<VoucherUser>> getVoucherByCode(@RequestParam int voucherUserId){
        Response<VoucherUser> response = voucherUserService.getById(voucherUserId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
