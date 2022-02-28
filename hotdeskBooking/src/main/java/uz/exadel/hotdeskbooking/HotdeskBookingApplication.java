package uz.exadel.hotdeskbooking;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class HotdeskBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotdeskBookingApplication.class, args);
    }

}
