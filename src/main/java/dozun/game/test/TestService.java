package dozun.game.test;

import dozun.game.utils.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private RandomNumberGenerator randomNumberGenerator;

    @Autowired
    public TestService(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

//    public Long getRandom(){
//        return RandomNumberGenerator.getNumber();
//    }
}
