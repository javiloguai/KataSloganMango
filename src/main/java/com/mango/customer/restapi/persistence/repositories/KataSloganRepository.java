package com.mango.customer.restapi.persistence.repositories;

import com.mango.customer.restapi.persistence.entities.KataSlogan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KataSloganRepository extends JpaRepository<KataSlogan, Long> {

    List<KataSlogan> findAllByCustomerId(final Long customerId);

	long countByCustomerId(final Long customerId);

}
