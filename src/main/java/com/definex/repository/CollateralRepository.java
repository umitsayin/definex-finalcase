package com.definex.repository;

import com.definex.model.Collateral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CollateralRepository extends JpaRepository<Collateral, UUID> {
}
