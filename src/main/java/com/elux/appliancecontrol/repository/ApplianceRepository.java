package com.elux.appliancecontrol.repository;

import com.elux.appliancecontrol.model.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplianceRepository extends JpaRepository<Appliance, Integer> {

    Optional<Appliance> findByApplianceId(String applianceID);

    void deleteByApplianceId(String applianceID);

    boolean existsByApplianceId(String applianceId);

}
