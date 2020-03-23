import metadata.json.BeanMeta;
import metadata.json.JsonFileDefinition;
import org.junit.jupiter.api.Test;
import util.JsonProcessor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonProcessorTest {

    @Test
    void parse() throws IOException {
        JsonProcessor processor = new JsonProcessor("src/test/resources/json_parse_test.json");

        JsonFileDefinition contextDefinition = processor.parse(JsonFileDefinition.class);

        assertTrue(contextDefinition.getImports().contains("one"));
        assertTrue(contextDefinition.getImports().contains("two"));
        BeanMeta beanMeta = contextDefinition.getBeans().get(0);
        assertEquals("water", beanMeta.getBeanName());
        assertEquals("ru.marbok.sleep.NewClass", beanMeta.getClassName());
        assertEquals("volume", beanMeta.getConstructor().get(0).getNameAttr());
        assertEquals(5, beanMeta.getConstructor().get(0).getValue());
    }
}