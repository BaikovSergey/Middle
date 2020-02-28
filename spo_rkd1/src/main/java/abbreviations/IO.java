package abbreviations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class IO {

    private final String inputPath =  "C:" + File.separator + "spo_rkd" + File.separator + "input.txt";

    private final String outputPath = "C:" + File.separator + "spo_rkd" + File.separator +  "result.txt";

    public String readFile() {
        Path path = Paths.get(this.inputPath);
        String result = "";
        try {
            result = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void writeToFile(List<String> output) {
        Path path = Paths.get(this.outputPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString(), false))) {
            for (String string: output) {
                writer.write(string);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
