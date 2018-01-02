package car.engine;

public class ElectricEngine implements Engine {
    @Override
    public float emitPollution() {
        return 0.1f;
    }

    @Override
    public String toString() {
        return "Electric";
    }
}
