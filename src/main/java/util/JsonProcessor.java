package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonProcessor {

    private String fileName;

    public JsonProcessor(String fileName) {
        this.fileName = fileName;
    }

    public <T> T parse(Class<T> clazz) throws IOException {
        assert fileName != null;

        var json = readJson(fileName);

        var mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    private String readJson(String fileName) throws IOException {
        var buffer = new StringBuilder();
        Files.lines(Paths.get(fileName), StandardCharsets.UTF_8).forEach(buffer::append);
        return buffer.toString();

    }
}
