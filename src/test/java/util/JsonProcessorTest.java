package util;

import metadata.json.JsonFileDefinition;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonProcessorTest {

    @Test
    void parse() throws IOException {
        var processor = new JsonProcessor("src/test/resources/json_parse_test.json");

        var contextDefinition = processor.parse(JsonFileDefinition.class);

        assertTrue(contextDefinition.getImports().contains("one"));
        assertTrue(contextDefinition.getImports().contains("two"));
        var beanMeta = contextDefinition.getBeans().get(0);
        assertEquals("water", beanMeta.getBeanName());
        assertEquals("ru.marbok.sleep.NewClass", beanMeta.getClassName());
        assertEquals("char", beanMeta.getConstructor().get(0).getType());
        assertEquals("c", beanMeta.getConstructor().get(0).getValue());
    }
}