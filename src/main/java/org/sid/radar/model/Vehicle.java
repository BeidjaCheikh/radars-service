package org.sid.radar.model;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Vehicle {
    private Long id;
    private String matricule;
    private String marque;
    private int puissanceFiscale;
    private String model;

    private Owner owner;
}
