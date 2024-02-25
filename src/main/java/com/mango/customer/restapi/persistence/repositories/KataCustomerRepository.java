package com.mango.customer.restapi.persistence.repositories;

import com.mango.customer.restapi.persistence.entities.KataCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface KataCustomerRepository extends JpaRepository<KataCustomer, Long> {
    Optional<KataCustomer> findById(final Long id);

	Optional<KataCustomer> findFirstByEmailIgnoreCase(final String email);


}
