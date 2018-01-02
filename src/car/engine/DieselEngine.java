package car.engine;

public class DieselEngine implements Engine {
    @Override
    public float emitPollution() {
        return 3f;
    }

    @Override
    public String toString() {
        return "Diesel";
    }
}
