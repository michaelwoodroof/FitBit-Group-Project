import java.util.*;
import java.util.regex.*;

public class Regex {

    public static String regexPatterns(Integer patternNumber) {

        // Regex Patterns
        // Characters
        // . any Character
        // \d any Digit
        // \D any non-digit
        // \s any whitespace
        // \S any non-whitespace
        // \w a word character [a-zA-Z_0-9]
        // \W and non-word character
        // [a-z] or [A-Z] only single Character from Alphabet


        // Modifiers
        // {X} X being this must appear X Times
        // X|Y must be X or Y
        // - Range i.e. [1-7], [A-E1-2] (not E1)
        // ^ not this i.e. [^abc] not character: 'a', 'b' or 'c'
        // ? Appears once or no times
        // {X,Y} appears between X and Y times

        // Example Patterns
        switch (patternNumber) {
            case 0: return "[a-zA-Z]+[-][a-zA-Z]+||[a-zA-Z]+"; // Pattern for First Name and Last Name
            case 1: return "[\\D]+[@][\\D]+[.][\\D]+"; // Pattern for Emails
            case 2: return "[+][\\d]{1,3}[\\d]{10}||[0][\\d]{10}"; // Pattern for Phone Number
            case 3: return "[3][01]||[12][\\d]||[123456789]"; // Pattern for Day
            case 4: return "[2][0-4][0-5][\\d]|[0-1][\\d][0-5][\\d]"; // 24 Hrs
        }
        return "invalid";
    }

    // Useful Function
    public static Boolean regexChecker(String userInput, String regexPattern) {
        return Pattern.matches(regexPattern,userInput.trim());
    }

}
