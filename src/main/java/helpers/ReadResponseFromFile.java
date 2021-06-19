package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ReadResponseFromFile {
    private final static Logger LOGGER= LoggerFactory.getLogger(ReadResponseFromFile.class);
    public String readResponseFromFile(String fileName, String baseURL) {
        JSONObject jsonObject = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            InputStream is = ReadResponseFromFile.class.getClassLoader().getResourceAsStream(baseURL + fileName);
            Reader buffer = new InputStreamReader(is, StandardCharsets.UTF_8.name());
            jsonObject = objectMapper.readValue(buffer, JSONObject.class);
        } catch (FileNotFoundException fe) {
            LOGGER.error("File could not be found {} \n", fileName, fe);
        } catch (Exception e) {
            LOGGER.error("An error occurred {} \n", fileName, e);
        }
        return jsonObject.toString();
    }
}
