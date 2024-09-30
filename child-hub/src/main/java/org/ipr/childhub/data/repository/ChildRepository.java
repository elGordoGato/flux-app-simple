package org.ipr.childhub.data.repository;

import org.ipr.childhub.data.entity.Child;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ChildRepository extends ReactiveSortingRepository<Child, Long>, ReactiveCrudRepository<Child, Long> {
}
