package testHelpers;

import lombok.Data;

@Data
public class Square {
    private double side;
    private String name;

    public Square(double side) {
        this.side = side;
    }
}
