package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ConvertRequestResponseToDTOObject {
    public Object convertRequestResponseToDTO(String result, Class c) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object resultDTO= mapper.readValue(result,c);
        return resultDTO;

    }
}
