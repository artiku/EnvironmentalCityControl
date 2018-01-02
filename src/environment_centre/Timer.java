package environment_centre;

public class Timer implements Runnable {
    private EnvironmentCentre environmentCentre;

    public Timer(EnvironmentCentre centre){
        this.environmentCentre = centre;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            environmentCentre.pollutionNullifier();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
