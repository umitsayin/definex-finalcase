package com.definex.repository;

import com.definex.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreditRepository extends JpaRepository<Credit, UUID> {
    Optional<List<Credit>> findCreditByCustomerIdentityNumberAndCustomerDateOfBirth(String identityNumber,
                                                                            Date dateOfBirth);
}
