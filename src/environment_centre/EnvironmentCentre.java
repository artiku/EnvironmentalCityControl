package environment_centre;

import car.Car;
import car.engine.DieselEngine;
import car.engine.PetrolEngine;
import city.RoadSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EnvironmentCentre {

    private List<Car> registeredCarsInTown;
    private RoadSystem roadSystem;
    private AtomicInteger pollutionAmount = new AtomicInteger(0);
    private Thread timer;
    private boolean timerStarted = false;

    public EnvironmentCentre(RoadSystem roadSystem) {
        this.registeredCarsInTown = new ArrayList<>();
        this.roadSystem = roadSystem;
    }

    public synchronized void registerCarInTown(Car car) {
        if (!registeredCarsInTown.contains(car)) {
            registeredCarsInTown.add(car);
            car.setEnvironmentCentre(this);
        }
    }

    public synchronized void sendData(Car car) {
        int pollutionEmitted = (int) car.getEngineType().emitPollution() * 50;
        pollutionAmount.addAndGet(pollutionEmitted);
    }

    public synchronized void askPermission(Car car) throws InterruptedException {
        System.err.println(pollutionAmount.get());
        if (pollutionAmount.get() >= 4000 && car.getEngineType() instanceof DieselEngine) {
            this.startTimer();
        } else if (pollutionAmount.get() >= 5000 && car.getEngineType() instanceof PetrolEngine) {
            this.startTimer();
        }
    }

    private synchronized void startTimer() throws InterruptedException {
        if (!timerStarted) {
            timer = new Thread(new Timer(this));
            timer.start();
            wait();
        }
    }

    synchronized void pollutionNullifier() {
        if (numberOfInnerCombustionEnginesAboveLimit()) {
            int newValue = (int) (pollutionAmount.get() * 0.4);
            pollutionAmount.set(newValue);
        } else {
            pollutionAmount.set(0);
        }
        notifyAll();
        timerStarted = false;
    }

    private boolean numberOfInnerCombustionEnginesAboveLimit() {
        return registeredCarsInTown.parallelStream().filter(car -> car.getEngineType() instanceof DieselEngine ||
                car.getEngineType() instanceof PetrolEngine).collect(Collectors.toList()).size() >= 70;
    }

    public RoadSystem getCityRoadSystem() {
        return roadSystem;
    }

}
