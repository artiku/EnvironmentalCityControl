package city.crossroad;

import car.Car;

import java.util.concurrent.atomic.AtomicInteger;

public class MaintenanceCrossroad extends Crossroad {

    private AtomicInteger carNumberInQueue = new AtomicInteger(0);

    public MaintenanceCrossroad() {
    }

    @Override
    public void driveThrough(Car car) {
        System.out.println(String.format("Car #%s on the maintenance", car.getCarId()));
        try {
            int timeToWait = 50 * carNumberInQueue.incrementAndGet();
            Thread.sleep(timeToWait);
            carNumberInQueue.decrementAndGet();
            System.out.println(String.format("%s finished maintenance at #%s crossroad and waited for %s ms", car, this.getCrossroadId(), timeToWait));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
