package metadata.json;

import lombok.Data;

import java.util.List;

@Data
public class JsonFileDefinition {
    private List<String> imports;
    private List<BeanMeta> beans;
}
