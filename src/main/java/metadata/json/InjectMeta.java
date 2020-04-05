package metadata.json;

import lombok.Data;

@Data
public class InjectMeta {
    private String value;
    private String[] values;
    private String ref;
    private String[] refs;
    private String type;
    private String name;
}
