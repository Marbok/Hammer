package metadata.json;

import lombok.Data;

import java.util.List;

/**
 * Top-level definition. <br>
 */
@Data
public class JsonFileDefinition {
    /**
     * Set of paths to other json files
     */
    private List<String> imports;

    /**
     * Set of objects descriptions and their dependencies
     */
    private List<BeanMeta> beans;
}
