package org.ipr.childhub.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ipr.childhub.data.repository.ChildRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class DataInitializr implements CommandLineRunner {

    private final ChildRepository child;

    @Override
    public void run(String[] args) {
        log.info("start data initialization  ...");
/*        this.child.deleteAll()
                .subscribe(null,
                        null,
                        () -> log.info("done initialization"));*/

    }

}
