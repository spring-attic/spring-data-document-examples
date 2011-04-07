package org.springframework.data.mongodb.examples.custsvc.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.examples.custsvc.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
