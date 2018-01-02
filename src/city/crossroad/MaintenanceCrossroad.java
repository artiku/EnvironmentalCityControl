package city.crossroad;

import car.Car;
import constants.Constants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

import static constants.Constants.FINISHED_MAINTENANCE_FILENAME;
import static constants.Constants.MAINTENANCE_TIME;

public class MaintenanceCrossroad extends Crossroad {

    private AtomicInteger carNumberInQueue = new AtomicInteger(0);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");

    public MaintenanceCrossroad() {
    }

    @Override
    public void driveThrough(Car car) {
        try {
            System.out.println(String.format("Car #%s on the maintenance", car.getCarId()));

            int timeToWait = MAINTENANCE_TIME * carNumberInQueue.incrementAndGet();
            Thread.sleep(timeToWait);
            carNumberInQueue.decrementAndGet();

            String textInfo = String.format("%s finished maintenance at #%s crossroad and waited for %s ms\n",
                    car, this.getCrossroadId(), timeToWait);
            System.out.print(textInfo);

            writeIntoLogFile(textInfo, FINISHED_MAINTENANCE_FILENAME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void writeIntoLogFile(String textInfo, String filename) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            textInfo = sdf.format(timestamp) + ": " + textInfo;
            Files.write(Paths.get(filename), textInfo.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
