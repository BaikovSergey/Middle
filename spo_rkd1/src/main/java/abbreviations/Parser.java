package abbreviations;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {


    public Set<String> getAbbreviations(String input) {
        Set<String> result = new HashSet<>();
        String regexp = "([А-Я/d&-]+)|([A-Z/d&-]+)";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher;
        String text = removeCommas(input);
        String[] arr = text.split("[ /]");
        for (String string: arr) {
            if (string.length() >= 2) {
                matcher = pattern.matcher(string);
                if (matcher.matches()) {
                    result.add(string);
                }
            }
        }
        return result;
    }

    public String removeCommas(String input) {
        return input.replaceAll(",", "");
    }

}
