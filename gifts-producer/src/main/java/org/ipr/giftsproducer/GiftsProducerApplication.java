package org.ipr.giftsproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class GiftsProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiftsProducerApplication.class, args);
    }

}
