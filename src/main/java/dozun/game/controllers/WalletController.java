package dozun.game.controllers;

import dozun.game.payloads.dtos.WalletDTO;
import dozun.game.enums.ResponseStatus;
import dozun.game.models.ResponseObject;
import dozun.game.services.WalletService;
import dozun.game.utils.TokenChecker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/wallet")
public class WalletController {
    private WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/charge")
    public ResponseEntity<ResponseObject> bet(@RequestHeader("Authorization") String token,
                                              @Valid @RequestBody WalletDTO walletDTO){
        try {
            if (TokenChecker.checkToken(token)) {
                walletService.charge(walletDTO);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(dozun.game.enums.ResponseStatus.SUCCESS, "success", ""));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(dozun.game.enums.ResponseStatus.BAD_REQUEST, "failed", "charge failed"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), ""));
        }
    }
}
