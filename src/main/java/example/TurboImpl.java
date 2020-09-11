package example;

import annotations.Bean;
import annotations.InitMethod;
import annotations.Scope;

import java.util.Random;

import static beaninfo.Scope.PROTOTYPE;

@Bean
@Scope(PROTOTYPE)
public class TurboImpl implements Turbo {

    private int power = new Random().nextInt(50);

    @Override
    public void turbo() {
        System.out.println("Tuuurbooo:" + power + "h.p");
    }

    @InitMethod
    public void init() {
        System.out.println("Yea!!! I've bought TURBOOO!!!");
    }
}
