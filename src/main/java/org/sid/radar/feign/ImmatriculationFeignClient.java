package org.sid.radar.feign;

import org.sid.radar.model.Owner;
import org.sid.radar.model.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "IMMATRICULATION-SERVICE")

public interface ImmatriculationFeignClient {
    // Check if owner exists
    @GetMapping("/api/owners/exist/{id}")
    boolean isOwnerExistsById(@PathVariable("id") Long id);

    // Get owner
    @GetMapping("/api/owners/{id}")
    Owner getOwner(@PathVariable("id") Long id);

    // Check if vehicle exists
    @GetMapping("/vehicles/exist/{id}")
    boolean isVehicleExistsById(@PathVariable("id") Long id);

    // Get vehicle
    @GetMapping("/vehicles/{id}")
    Vehicle getVehicle(@PathVariable("id") Long id);
}
