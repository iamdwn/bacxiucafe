//package dozun.game.controllers;
//
//import dozun.game.payloads.dtos.WalletDTO;
//import dozun.game.enums.ResponseStatus;
//import dozun.game.models.ResponseObject;
//import dozun.game.payloads.requests.WalletRequest;
//import dozun.game.services.JwtService;
//import dozun.game.services.WalletService;
//import dozun.game.utils.TokenChecker;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
////@CrossOrigin
//@Tag(name = "Wallet")
////@Api(tags="Wallet")
//@RequestMapping("/api/v1/user/wallet")
//@SecurityRequirement(name = "bearerAuth")
//public class WalletController {
//    private WalletService walletService;
//    private JwtService jwtService;
//
//    @Autowired
//    public WalletController(WalletService walletService, JwtService jwtService) {
//        this.walletService = walletService;
//        this.jwtService = jwtService;
//    }
//
//    @PostMapping("/charge")
//    public ResponseEntity<ResponseObject> charge(HttpServletRequest request,
//                                              @Valid @RequestBody WalletDTO walletDTO) {
//        try {
//            String token = request.getHeader("Authorization");
//            if (TokenChecker.checkToken(token)) {
//                walletService.charge(walletDTO);
//                return ResponseEntity.status(HttpStatus.OK)
//                        .body(new ResponseObject(dozun.game.enums.ResponseStatus.SUCCESS, "success", ""));
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseObject(dozun.game.enums.ResponseStatus.BAD_REQUEST, "failed", "charge failed"));
//        } catch (RuntimeException ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), "charge failed"));
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<ResponseObject> getWallet(HttpServletRequest request) {
//        try {
//            String token = request.getHeader("Authorization");
//            if (TokenChecker.checkToken(token)) {
//                String username = jwtService.extractTokenToGetUser(token);
//                return ResponseEntity.status(HttpStatus.OK)
//                        .body(new ResponseObject(dozun.game.enums.ResponseStatus.SUCCESS, "success", walletService.getWallet(username)));
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseObject(dozun.game.enums.ResponseStatus.BAD_REQUEST, "failed", "get failed"));
//        } catch (RuntimeException ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseObject(ResponseStatus.BAD_REQUEST, ex.getMessage(), "get failed"));
//        }
//    }
//}
