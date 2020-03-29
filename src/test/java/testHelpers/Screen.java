package testHelpers;

import lombok.Data;

@Data
public class Screen {
    private Square square;
    private Circle circle;

    public Screen(Square square, Circle circle) {
        this.square = square;
        this.circle = circle;
    }
}
