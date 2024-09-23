package org.ipr.childhub.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ipr.childhub.data.entity.Child;
import org.ipr.childhub.data.repository.ChildRepository;
import org.ipr.childhub.service.ChildService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@ConditionalOnProperty("${data.init.enabled}")
@Component
@Slf4j
@RequiredArgsConstructor
class DataInitializr implements CommandLineRunner {

    private final ChildService childService;
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

    @Override
    public void run(String[] args) {
        log.info("start data initialization  ...");
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            childService.giveGiftsAndSave(new Child(null, names.get(random.nextInt(names.size())), (byte) random.nextInt(18), null), true).subscribe();
        }
        log.info("complete data initialization");
    }

}
