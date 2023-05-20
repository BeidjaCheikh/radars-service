package org.sid.radar.web.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.sid.radar.entites.Radar;
import org.sid.radar.feign.ImmatriculationFeignClient;
import org.sid.radar.feign.InfractionFeignClient;
import org.sid.radar.model.Infraction;
import org.sid.radar.model.Vehicle;
import org.sid.radar.repositoy.RadarRepository;
import org.sid.radar.web.grpc.stubs.RadarOuterClass;
import org.sid.radar.web.grpc.stubs.RadarServiceGrpc;

import java.util.Date;
@GrpcService

public class RadarGrpcService extends RadarServiceGrpc.RadarServiceImplBase  {
    private final RadarRepository radarRepository;
    private final InfractionFeignClient infractionFeignClient;
    private final ImmatriculationFeignClient immatriculationFeignClient;

    public RadarGrpcService(RadarRepository radarRepository, InfractionFeignClient infractionFeignClient, ImmatriculationFeignClient immatriculationFeignClient) {
        this.radarRepository = radarRepository;
        this.infractionFeignClient = infractionFeignClient;
        this.immatriculationFeignClient = immatriculationFeignClient;
    }


    @Override
    public void detectInfraction(RadarOuterClass.DetectRequest request, StreamObserver<RadarOuterClass.Infraction> responseObserver) {
        Long radarId = request.getRadarId();
        Long vehicleId = request.getVehicleId();
        Double vehicleSpeed = request.getSpeed();

        if (radarRepository.existsById(radarId) && immatriculationFeignClient.isVehicleExistsById(vehicleId)) {
            Radar radar = radarRepository.findById(radarId).get();
            Vehicle vehicle = immatriculationFeignClient.getVehicle(vehicleId);
            if (vehicleSpeed > radar.getVitesseMax()) {
                Infraction infraction = Infraction.builder()
                        .id(null)
                        .date(new Date().toString())
                        .vehicleSpeed(vehicleSpeed)
                        .radarMaxSpeed(radar.getVitesseMax())
                        .amount((vehicleSpeed - radar.getVitesseMax()) * 100)
                        .radarId(radar.getId())
                        .vehicleId(vehicle.getId())
                        .vehicle(vehicle)
                        .radar(radar)
                        .build();
                infraction = infractionFeignClient.saveInfraction(infraction);

                infraction.setVehicle(vehicle);
                infraction.setRadar(radar);

                RadarOuterClass.Infraction response = RadarOuterClass.Infraction.newBuilder()
                        .setId(infraction.getId())
                        .setDate(infraction.getDate())
                        .setVehicleSpeed(infraction.getVehicleSpeed())
                        .setRadarMaxSpeed(infraction.getRadarMaxSpeed())
                        .setAmount(infraction.getAmount())
                        .setRadarId(infraction.getRadarId())
                        .setVehicleId(infraction.getVehicleId())
                        .setVehicle(RadarOuterClass.Vehicle.newBuilder()
                                .setId(infraction.getVehicle().getId())
                                .setMatricule(infraction.getVehicle().getMatricule())
                                .setMarque(infraction.getVehicle().getMarque())
                                .setModel(infraction.getVehicle().getModel())
                                .build())
                        .setRadar(RadarOuterClass.Radar.newBuilder()
                                .setId(infraction.getRadar().getId())
                                .setLongitude(infraction.getRadar().getLongitude())
                                .setLatitude(infraction.getRadar().getLatitude())
                                .setMaxSpeed(infraction.getRadar().getVitesseMax())
                                .build())
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        }

    }
}
