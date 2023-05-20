package org.sid.radar.webs;

import org.sid.radar.entites.Radar;
import org.sid.radar.repositoy.RadarRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller

public class GraphQlAPI {
    private RadarRepository radarRepository;

    public GraphQlAPI(RadarRepository radarRepository) {
        this.radarRepository = radarRepository;
    }

    @QueryMapping
    public List<Radar> radarsList(){
        return radarRepository.findAll();
    }
    @QueryMapping
    public Radar radarById(@Argument Long id){
        return radarRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("radar %s not found" ,id)));
    }

    @MutationMapping
    public Radar _addRadar(@Argument Radar radar){
        return radarRepository.save(radar);
    }

    @MutationMapping
    public boolean deleteRadar(@Argument Long id){
                radarRepository.deleteById(id);
                return true;
    }




}
