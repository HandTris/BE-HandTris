package jungle.HandTris;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class HandTrisApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandTrisApplication.class, args);
    }

}
