package city.crossroad;

import car.Car;
import environment_centre.EnvironmentCentre;

public class CityEntranceCrossroad extends Crossroad{

    private EnvironmentCentre environmentCentre;

    public CityEntranceCrossroad(EnvironmentCentre environmentCentre) {
        this.environmentCentre = environmentCentre;
    }

    @Override
    public void driveThrough(Car car) {
        this.environmentCentre.registerCarInTown(car);
    }
}
