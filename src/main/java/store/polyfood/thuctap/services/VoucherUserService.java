package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.Account;
import store.polyfood.thuctap.models.entities.User;
import store.polyfood.thuctap.models.entities.VoucherUser;
import store.polyfood.thuctap.models.responobject.Response;
import store.polyfood.thuctap.repositories.UserRepo;
import store.polyfood.thuctap.repositories.VoucherUserRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherUserService implements IVoucherUserService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private VoucherUserRepo voucherUserRepo;


    @Override
    public Response<List<VoucherUser>> getAllVoucher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  (String) authentication.getPrincipal();
        Account account = (Account) accountService.loadUserByUsername(username);
        User user = userRepo.findByAccountId(account.getAcountId());
        return new Response<>(LocalDateTime.now().toString(), 200,
                null, "Success", voucherUserRepo.findAllByUserId(user.getUserId()));
    }

    @Override
    public Response<VoucherUser> getById(int id) {
        VoucherUser voucherUser = voucherUserRepo.findById(id).orElse(null);
        if(voucherUser == null) {
            return new Response(LocalDateTime.now().toString(),
                    404, "VoucherUser not found", null);
        }
        return new Response<>(LocalDateTime.now().toString(),
                200, null, "Success", voucherUser);
    }
}
