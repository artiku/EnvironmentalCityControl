package car.engine;

public class LemonadeEngine implements Engine {
    @Override
    public float emitPollution() {
        return 0.5f;
    }

    @Override
    public String toString() {
        return "Lemonade";
    }
}
