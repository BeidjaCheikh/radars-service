package org.sid.radar.feign;

import org.sid.radar.model.Infraction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "INFRACTION-SERVICE")

public interface InfractionFeignClient {
    @GetMapping("/infractions/{id}")
    Infraction getInfraction(@PathVariable("id") Long id);
    @GetMapping("/infractions/radar/{id}")
    List<Infraction> getInfractionsByRadarId(@PathVariable("id") Long id);

    @PostMapping("/infractions")
    Infraction saveInfraction(@RequestBody Infraction infraction);
}
