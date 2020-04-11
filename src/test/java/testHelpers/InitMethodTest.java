package testHelpers;

import annotations.InitMethod;
import lombok.Data;

@Data
public class InitMethodTest {

    private int level;
    private int factor;
    private int score;

    public InitMethodTest(int level) {
        this.level = level;
    }

    @InitMethod
    public void init() {
        score = level * factor;
    }
}
