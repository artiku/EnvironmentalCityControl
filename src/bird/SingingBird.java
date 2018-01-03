package bird;

import car.engine.DieselEngine;
import car.engine.ElectricEngine;
import car.engine.LemonadeEngine;
import car.engine.PetrolEngine;
import environment_centre.EnvironmentCentre;

import java.util.stream.Collectors;

import static constants.Constants.BIRD_SING_COOLDOWN;
import static constants.Constants.CONSOLE_RED_LOG_ON;
import static constants.Constants.POLLUTION_BIRD_LIMIT;


public class SingingBird extends Thread {

    private EnvironmentCentre environmentCentre;

    public SingingBird(EnvironmentCentre environmentCentre) {
        this.environmentCentre = environmentCentre;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                this.sing();
                if (CONSOLE_RED_LOG_ON) singMonitor();
                    Thread.sleep(BIRD_SING_COOLDOWN);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sing() {
        if (environmentCentre.getCityPollution() < POLLUTION_BIRD_LIMIT) {
            System.err.println("Puhas õhk on puhas õhk on rõõmus linnu elu!");
        } else {
            System.err.println("Inimene tark, inimene tark – saastet täis on linnapark");
        }
    }

    private void singMonitor() {
        int dieselCars = environmentCentre.getRegisteredCarsInTown().parallelStream().filter(
                car -> car.getEngineType() instanceof DieselEngine).collect(Collectors.toList()).size();
        int petrolCars = environmentCentre.getRegisteredCarsInTown().parallelStream().filter(
                car -> car.getEngineType() instanceof PetrolEngine).collect(Collectors.toList()).size();
        int lemonadeCars = environmentCentre.getRegisteredCarsInTown().parallelStream().filter(
                car -> car.getEngineType() instanceof LemonadeEngine).collect(Collectors.toList()).size();
        int electricCars = environmentCentre.getRegisteredCarsInTown().parallelStream().filter(
                car -> car.getEngineType() instanceof ElectricEngine).collect(Collectors.toList()).size();
                String sf = String.format("Pollution is %s; Number of Diesel cars: %s, Petrol cars: %s, Lemonade cars: %s, Electric cars: %s",
                        environmentCentre.getCityPollution(), dieselCars, petrolCars, lemonadeCars, electricCars);
        System.err.println(sf);

    }
}
