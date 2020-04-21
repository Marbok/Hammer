package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Encapsulate work with json file
 */
public class JsonProcessor {

    private final String path;

    /**
     * @param path path to the json file, can't be null
     */
    public JsonProcessor(String path) {
        if (path == null) throw new IllegalArgumentException("File's name can't be null");
        this.path = path;
    }

    /**
     * Method to deserialize JSON content to the passed class
     *
     * @throws IOException if {@link JsonProcessor#path} have some problems.
     *                     For instance, the file doesn't exist or has incompatible structure.
     */
    public <T> T parse(Class<T> clazz) throws IOException {
        String json = readJson(path);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    private String readJson(String fileName) throws IOException {
        StringBuilder buffer = new StringBuilder();
        Files.lines(Paths.get(fileName), StandardCharsets.UTF_8).forEach(buffer::append);
        return buffer.toString();
    }
}
