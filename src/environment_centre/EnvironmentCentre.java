package environment_centre;

import car.Car;
import car.engine.DieselEngine;
import car.engine.PetrolEngine;
import city.RoadSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EnvironmentCentre {

    private List<Car> registeredCarsInTown;
    private RoadSystem roadSystem;
    private AtomicInteger pollutionAmount = new AtomicInteger(0);
    private Thread timer;
    private AtomicBoolean timerStarted = new AtomicBoolean(false);

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

    public synchronized boolean askPermission(Car car) throws InterruptedException {
        System.err.println(pollutionAmount.get());
        if (pollutionAmount.get() >= 4000 && car.getEngineType() instanceof DieselEngine) {
             return this.startTimer();
        } else if (pollutionAmount.get() >= 5000 && car.getEngineType() instanceof PetrolEngine) {
            return this.startTimer();
        }
        return true;
    }

    private synchronized boolean startTimer() throws InterruptedException {
        if (!timerStarted.get()) {
            System.err.println(pollutionAmount.get());
            timerStarted.set(true);
            System.err.println("DIESEL or/and GASOLINE CARS SHOULD NOT PASS");
            timer = new Thread(new Timer(this));
            timer.start();
        }
        wait();
        return false;
    }

    synchronized void pollutionNullifier() {
        if (numberOfInnerCombustionEnginesAboveLimit()) {
            int newValue = (int) (pollutionAmount.get() * 0.4);
            pollutionAmount.set(newValue);
        } else {
            pollutionAmount.set(0);
        }
        System.err.println("PURIFIED to " + pollutionAmount.get());
        notifyAll();
        timerStarted.set(false);
    }

    private boolean numberOfInnerCombustionEnginesAboveLimit() {
        return registeredCarsInTown.parallelStream().filter(car -> car.getEngineType() instanceof DieselEngine ||
                car.getEngineType() instanceof PetrolEngine).collect(Collectors.toList()).size() >= 70;
    }

    public RoadSystem getCityRoadSystem() {
        return roadSystem;
    }

}
