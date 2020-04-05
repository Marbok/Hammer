package testHelpers;

import lombok.Data;

@Data
public class Polyhedron {

    private int[] sides;

    public Polyhedron(int[] sides) {
        this.sides = sides;
    }
}
