package abbreviations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ABBSearch {

    private final Parser parser = new Parser();

    private final IO io = new IO();

    private List<String> sortedAbbreviations = new ArrayList<>();

    public void execute() {
        this.sortedAbbreviations.addAll(parser.getAbbreviations(io.readFile()));
        Collections.sort(this.sortedAbbreviations);
        io.writeToFile(this.sortedAbbreviations);
    }

    public static void main(String[] args) {
        ABBSearch search = new ABBSearch();
        search.execute();
    }
}
