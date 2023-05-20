package org.sid.radar.webs;

import org.sid.radar.entites.Radar;
import org.sid.radar.repositoy.RadarRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/radars")
public class RestAPI {
    private RadarRepository radarRepository;

    public RestAPI(RadarRepository radarRepository) {
        this.radarRepository = radarRepository;
    }

    @GetMapping("/radars")
    public List<Radar> getAllRadar() {
        return radarRepository.findAll();
    }
    @GetMapping("/radars/{id}")
    public Radar radar(@PathVariable Long id){
        return radarRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("radar not found")));
    }
    @PostMapping("/radars")

    public Radar saveRadar(@RequestBody Radar radar){

        return radarRepository.save(radar);
    }
    @PutMapping("/radars/{id}")
    public Radar updateRadar( @PathVariable Long id ,@RequestBody Radar radar){
        Radar radar1=new Radar();

        return radarRepository.save(radar);
    }
    @DeleteMapping("/comptes/{id}")
    public boolean deleteRadar(@PathVariable Long id){
        radarRepository.deleteById(id);
        return true;
    }


}
