package testHelpers;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
public class Fish {
    Map<String, Circle> map;
    Set<Polyhedron> refsSet;
    Set<Character> charsSet;
}