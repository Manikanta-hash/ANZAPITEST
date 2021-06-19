package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataProviderReader {
    public Object[][] readInputsFromFile(String relativePath, String separator) throws IOException {

        InputStream stream = ClassLoader.getSystemResourceAsStream(relativePath+"input.txt");
        InputStreamReader isr = new InputStreamReader(stream , StandardCharsets.UTF_8.name());
        BufferedReader buffer = new BufferedReader(isr);

        List<List<String>> data = new ArrayList<>();

        try {
            String line;

            while ((line = buffer.readLine()) != null) {
                String[] vals = line.trim().split(separator +"\\s+");
                List<String> rowData = Arrays.asList(vals);
                data.add(rowData);
            }
        } finally {
            stream.close();
            isr.close();
            buffer.close();
        }

        int rows = data.size();
        int cols = data.get(0).size();

        Object[][] matrix = new Object[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                matrix[row][col] = data.get(row).get(col);
            }
        }

        return matrix;
    }

}