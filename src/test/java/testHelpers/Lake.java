package testHelpers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Lake {
    List<Integer> numbers;
    List<Circle> circles;
}
