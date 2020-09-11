package example;

import annotations.Bean;

@Bean
public class OldEngine implements Engine {
    @Override
    public void start() {
        System.out.println("ph-ph");
    }
}
