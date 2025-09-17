package com.aaronjosh.real_estate_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaronjosh.real_estate_app.models.PropertyEntity;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {
}
