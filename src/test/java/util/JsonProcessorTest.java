package util;

import metadata.json.BeanMeta;
import metadata.json.InjectMeta;
import metadata.json.JsonFileDefinition;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonProcessorTest {

    @Test
    void parse() throws IOException {
        JsonProcessor processor = new JsonProcessor("src/test/resources/json_parse_test.json");

        JsonFileDefinition actual = processor.parse(JsonFileDefinition.class);

        JsonFileDefinition expected = new JsonFileDefinition()
                .setImports(Arrays.asList("one", "two"))
                .setBeans(Collections.singletonList(
                        new BeanMeta().setBeanName("water")
                                .setClassName("ru.marbok.sleep.NewClass")
                                .setScope("prototype")
                                .setConstructor(Collections.singletonList(
                                        new InjectMeta().setType("char").setValue("c")
                                ))
                                .setSetters(Collections.singletonList(
                                        new InjectMeta().setType("int").setName("count").setValue("5")
                                ))
                ));

        assertEquals(expected, actual);
    }
}