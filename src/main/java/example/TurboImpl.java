package example;

import annotations.Bean;

@Bean
public class TurboImpl implements Turbo {
    @Override
    public void turbo() {
        System.out.println("Tuuurbooo");
    }
}
