package uz.exadel.hotdeskbooking;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@RequiredArgsConstructor
@EnableSwagger2
public class HotdeskBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotdeskBookingApplication.class, args);
    }

}
