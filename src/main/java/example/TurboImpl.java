package example;

import annotations.Bean;
import annotations.InitMethod;

@Bean
public class TurboImpl implements Turbo {
    @Override
    public void turbo() {
        System.out.println("Tuuurbooo");
    }

    @InitMethod
    public void init() {
        System.out.println("Yea!!! I've bought TURBOOO!!!");
    }
}
