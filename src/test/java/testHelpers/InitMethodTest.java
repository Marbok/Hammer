package testHelpers;

import annotations.InitMethod;
import lombok.Data;

@Data
@CalcScore
public class InitMethodTest {

    private int level;
    private int factor;
    private int score;
    private int beforeScore;
    private int afterScore;

    public InitMethodTest(int level) {
        this.level = level;
    }

    @InitMethod
    public void init() {
        score = level * factor;
    }
}
