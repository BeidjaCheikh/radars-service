package org.sid.radar.model;

import lombok.*;
import org.sid.radar.entites.Radar;
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Infraction {
    private Long id;
    private String date;
    private Long radarId;
    private Long vehicleId;
    private double vehicleSpeed;
    private double radarMaxSpeed;
    private double amount;
    private Vehicle vehicle;
    private Radar radar;
}
