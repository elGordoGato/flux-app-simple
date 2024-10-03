package org.ipr.childhub;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ipr.childhub.data.entity.Child;
import org.ipr.childhub.data.repository.ChildRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Random;

@ConditionalOnProperty("data.init.enabled")
@Component
@Slf4j
@RequiredArgsConstructor
class DataInitializr implements CommandLineRunner {
    private final Random random = new Random();

    private final ChildRepository childRepository;
    private final List<String> names = List.of(
            "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hank", "Ivy", "Jack",
            "Kara", "Liam", "Mona", "Nate", "Olivia", "Paul", "Quinn", "Rose", "Sam", "Tina",
            "Uma", "Vince", "Wendy", "Xander", "Yara", "Zane", "Aaron", "Bella", "Cody", "Diana",
            "Ethan", "Fiona", "George", "Holly", "Ian", "Jill", "Kyle", "Laura", "Mike", "Nina",
            "Oscar", "Penny", "Quincy", "Rita", "Steve", "Tara", "Ursula", "Victor", "Will", "Xena",
            "Yvonne", "Zach", "Amber", "Blake", "Carmen", "Derek", "Ella", "Felix", "Gina", "Harry",
            "Isla", "Jake", "Kylie", "Leo", "Mia", "Noah", "Olga", "Pete", "Quinn", "Ralph",
            "Sara", "Tom", "Ulysses", "Vera", "Wade", "Ximena", "Yosef", "Zara", "Aiden", "Brooke",
            "Carter", "Daisy", "Eli", "Faith", "Gabe", "Hannah", "Isaac", "Jade", "Kurt", "Lily"
    );

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }


    @Override
    public void run(String[] args) {
        Flux.range(1, 100)
                .doFirst(() -> log.info("start data initialization  ..."))
                .map(i -> getRandomChild())
                .flatMap(childRepository::save)
                .doAfterTerminate(() -> log.info("complete data initialization"))
                .subscribe();
    }

    private Child getRandomChild() {
        return Child.builder()
                .id(null)
                .name(names.get(random.nextInt(names.size())))
                .age((byte) random.nextInt(18))
                .createdAt(null)
                .build();
    }

}
