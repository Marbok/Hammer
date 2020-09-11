package example;

import annotations.Bean;
import annotations.Inject;

@Bean
public class Lada implements Car {

    private int fuel = 30;

    private Engine engine;
    private Turbo turbo;

    @Inject
    public Lada(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void drive() {
        engine.start();
        if (turbo != null) {
            turbo.turbo();
            fuel -= 2;
        }
        fuel -= 1;
        System.out.println("drive Lada");
    }

    @Override
    public int showFuel() {
        return fuel;
    }

    @Inject
    public void setTurbo(Turbo turbo) {
        this.turbo = turbo;
    }
}
