package environment_centre;

import car.Car;
import car.engine.DieselEngine;
import car.engine.PetrolEngine;
import city.RoadSystem;
import environment_centre.helpcar.HelpCar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static constants.Constants.ICE_CARS_IN_TOWN_LIMIT;
import static constants.Constants.POLLUTION_DIESEL_ENGINE_LIMIT;
import static constants.Constants.POLLUTION_PETROL_ENGINE_LIMIT;

public class EnvironmentCentre {

    private List<Car> registeredCarsInTown;
    private RoadSystem roadSystem;
    private AtomicInteger pollutionAmount = new AtomicInteger(0);
    private Thread timer;
    private AtomicBoolean timerStarted = new AtomicBoolean(false);
    private Set<Car> carsWantingHelp = new HashSet<>();
    private boolean helpCarOnDuty = false;

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
        if (pollutionAmount.get() >= POLLUTION_DIESEL_ENGINE_LIMIT && car.getEngineType() instanceof DieselEngine) {
             return this.startTimerAndRestrictDriving(car);
        } else if (pollutionAmount.get() >= POLLUTION_PETROL_ENGINE_LIMIT && car.getEngineType() instanceof PetrolEngine) {
            return this.startTimerAndRestrictDriving(car);
        }
        return true;
    }

    private synchronized boolean startTimerAndRestrictDriving(Car car) throws InterruptedException {
        if (!timerStarted.get()) {
            System.err.println(pollutionAmount.get());
            timerStarted.set(true);
            System.err.println("DIESEL or/and GASOLINE CARS SHOULD NOT PASS");
            timer = new Thread(new Timer(this));
            timer.start();
        }
        car.thinkAboutEngineReplacement();
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
                car.getEngineType() instanceof PetrolEngine).collect(Collectors.toList()).size() >= ICE_CARS_IN_TOWN_LIMIT;
    }

    public synchronized void callForHelp(Car car) {
        carsWantingHelp.add(car);
        if (!helpCarOnDuty) hitTheRoadHelpCar(car);
    }

    private void hitTheRoadHelpCar(Car car) {
        HelpCar helpCar = new HelpCar(car.getPosition(), carsWantingHelp,this);
        helpCar.start();
        helpCarOnDuty = true;
        System.err.println("Help Car On Duty!");
    }

    public void finishHelpCarDuty() {
        helpCarOnDuty = false;
        System.err.println("Help Car Finishes It's Duty!");
    }

    public RoadSystem getCityRoadSystem() {
        return roadSystem;
    }

}
