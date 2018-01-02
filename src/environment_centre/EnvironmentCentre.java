package environment_centre;

import car.Car;
import city.RoadSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EnvironmentCentre {

    private List<Car> registeredCarsInTown;
    private RoadSystem roadSystem;
    private AtomicInteger pollutionAmount = new AtomicInteger(0);

    public EnvironmentCentre(RoadSystem roadSystem) {
        this.registeredCarsInTown = new ArrayList<>();
        this.roadSystem = roadSystem;
    }

    public void registerCarInTown(Car car) {
        if (!registeredCarsInTown.contains(car)) registeredCarsInTown.add(car);
    }

    public void sendData(Car car) {
        int pollutionEmitted = (int) car.getEngineType().emitPollution() * 50;
        pollutionAmount.addAndGet(pollutionEmitted);
    }

    public boolean askPermission(Car car) {
        if ()

    }

    public RoadSystem getCityRoadsystem() {
        return roadSystem;
    }

}
