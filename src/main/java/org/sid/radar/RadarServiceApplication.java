package org.sid.radar;
import org.sid.radar.entites.Radar;
import org.sid.radar.repositoy.RadarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class RadarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadarServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(RadarRepository radarRepository, RepositoryRestConfiguration repositoryRestConfiguration) {
		repositoryRestConfiguration.exposeIdsFor(Radar.class);
		return args -> {
			Random random = new Random();

			for (int i = 0; i < 10; i++) {
				double vitesseMax = random.nextDouble() * 200;  // Valeur aléatoire entre 0 et 200
				double longitude = random.nextDouble() * 360 - 180;  // Valeur aléatoire entre -180 et 180
				double latitude = random.nextDouble() * 180 - 90;  // Valeur aléatoire entre -90 et 90

				Radar radar = Radar.builder()
						.vitesseMax(vitesseMax)
						.longitude(longitude)
						.latitude(latitude)
						.build();

				radarRepository.save(radar);
			}

			System.out.println("Insertion des radars réussie !");
		};
	}
}
