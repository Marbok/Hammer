package example;

import context.AnnotationContext;
import context.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationContext("example");
        Car car = context.getBean(Car.class);
        System.out.println(car.showFuel());
        car.drive();
        System.out.println(car.showFuel());

        Turbo turbo = context.getBean(Turbo.class);
        turbo.turbo();
    }
}
