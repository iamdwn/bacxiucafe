package dozun.game.services;

import dozun.game.payloads.dtos.WalletDTO;
import dozun.game.entities.UserEntity;
import dozun.game.entities.WalletEntity;
import dozun.game.repositories.UserRepository;
import dozun.game.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {
    private UserRepository userRepository;
    private WalletRepository walletRepository;

    @Autowired
    public WalletService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public void charge(WalletDTO walletDTO) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndStatusTrue(walletDTO.getUsername());
        Optional<WalletEntity> walletEntity = walletRepository.findByUser(userEntity.get());

        if (!userEntity.isPresent()) throw new RuntimeException("not found user");

        if (!walletEntity.isPresent()) throw new RuntimeException("not found user");

        walletEntity.get().setBalance(walletEntity.get().getBalance() + walletDTO.getChargeAmount());

        walletRepository.save(walletEntity.get());
    }
}
