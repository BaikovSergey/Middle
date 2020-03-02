package abbreviations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private final Set<String> listOfExceptions = new HashSet<>();

    private final Set<String> exceptionsToAdd = new HashSet<>();

    public Set<String> getAbbreviations(String input) {
        Set<String> result = new HashSet<>();
        String text = removeSymbols(input);
        this.exceptionsToAdd.addAll(checkTextForExceptions(text));
        text = removeExceptionsFromTxt(this.exceptionsToAdd, text);
        String[] arr = text.split("[ /]");




        String mainRegexp = "([А-Я/d&-]+)|([A-Z/d&-]+)";
        String additRegexp = "//((.*?)//)";

        Pattern pattern1 = Pattern.compile(mainRegexp);
        Pattern pattern2 = Pattern.compile(additRegexp);
        Matcher mainMatcher;
        Matcher additMatcher;


        for (int i = 0; i < arr.length; i++) {
            String abbreviation = arr[i];
            if (abbreviation.length() >= 2) {
                mainMatcher = pattern1.matcher(abbreviation);
                if (i <= arr.length - 2) {
                    additMatcher = pattern2.matcher(arr[i + 1]);
                    if (mainMatcher.matches()) {
                        if (additMatcher.matches()) {
                            result.add(abbreviation + " " + arr[i + 1]);
                            i += 1;
                        } else {
                            result.add(abbreviation);
                        }
                    }
                }
            }
        }
        result.addAll(this.exceptionsToAdd);
        return result;
    }

    private Set<String> checkTextForExceptions(String input) {
        Set<String> result = new HashSet<>();
        addListOfExceptions();
        for (String string: this.listOfExceptions) {
            if (input.matches("(?s).*\\b(" + string + ")\\b.*")) {
                result.add(string);
            }
        }
        return result;
    }

    private void addListOfExceptions() {
        this.listOfExceptions.add("ГОСТ Р");
        this.listOfExceptions.add("ГОСТ РВ");
        this.listOfExceptions.add("ГОСТ В");
        this.listOfExceptions.add("ГОСТ РО");
        this.listOfExceptions.add("ГОСТ ВД");
        this.listOfExceptions.add("СанПиН");
        this.listOfExceptions.add("GPS/ГЛОНАСС");
        this.listOfExceptions.add("ГЛОНАСС/GPS");
    }

    private String removeSymbols(String input) {
        return input.replaceAll("(,)|(\\.)|(;)|(:)", "");
    }

    private String removeExceptionsFromTxt(Set<String> exceptions, String input) {
        String result = input;
        for (String string: exceptions) {
            result = result.replaceAll(string, "");
        }
        return result;
    }

    public static void main(String[] args) {
        int count = 0;
        Parser parser = new Parser();
        parser.addListOfExceptions();
        String s = "Udwqdqd ГОСТ В чйцвцйв";
        for (String ss: parser.listOfExceptions) {
            if (s.matches("(?s).*\\b(" + ss + ")\\b.*")) {
                count++;
            }
        }
        System.out.println(count);
    }
}
