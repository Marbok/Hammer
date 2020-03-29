package testHelpers;

import lombok.Data;

@Data
public class Circle {
    private int radius;
    private boolean isColor;

    public Circle(int radius, boolean isColor) {
        this.radius = radius;
        this.isColor = isColor;
    }
}
