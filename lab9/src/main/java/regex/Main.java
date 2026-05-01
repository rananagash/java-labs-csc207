package regex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    /**
     * The Main method for this assignment.
     * You can optionally run this to interactively try the three methods.
     * @param args parameters are unused
     */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a string: ");
        final String userInput = scanner.nextLine();
        scanner.close();
        System.out.println("You entered \"" + userInput + "\"");
        System.out.println(checkForPassword(userInput, 6));
        System.out.println(extractEmails(userInput));
        System.out.println(checkForDoubles(userInput));
    }

    /**
     * Returns whether a given string is non-empty, contains one lower case letter,
     * at least one upper case letter, at least one digit, and meets the minimum length.
     * @param str the string to check for the properties in
     * @param minLength the minimum length required for the password
     * @return whether the string satisfies the password requirements
     */
    public static boolean checkForPassword(String str, int minLength) {
        // Handle null input
        if (str == null) {
            return false;
        }

        // Regex explanation:
        // ^(?=.*[a-z])    - positive lookahead for at least one lowercase letter
        // (?=.*[A-Z])     - positive lookahead for at least one uppercase letter
        // (?=.*\\d)       - positive lookahead for at least one digit
        // .{" + minLength + ",} - at least minLength characters total
        final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{" + minLength + ",}$";
        final boolean propertyOne = Pattern.matches(regex, str);
        return propertyOne;
    }

    /**
     * Returns a list of email addresses that occur in a given string.
     * @param str the string to look for email addresses in
     * @return a list containing the email addresses in the string.
     */
    public static List<String> extractEmails(String str) {
        // Handle null input - return empty list
        if (str == null) {
            return new ArrayList<>();
        }

        // Regex explanation:
        // [\\w.%+-]+      - at least one word character, dot, percent, plus, or hyphen
        // @               - literal @ symbol
        // (?:mail\\.)?    - optional "mail." (non-capturing group)
        // utoronto\\.ca   - literal "utoronto.ca"
        // The pattern will match emails ending with @utoronto.ca or @mail.utoronto.ca
        final Pattern pattern = Pattern.compile("[\\w.%+-]+@(?:mail\\.)?utoronto\\.ca");
        final Matcher matcher = pattern.matcher(str);
        final List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    /**
     * Checks whether a given string contains the same capital letter twice.
     * @param str the string to look for doubles in
     * @return whether str contains the same capital letter twice.
     */
    public static boolean checkForDoubles(String str) {
        // Handle null input - null doesn't contain any capital letters
        if (str == null) {
            return false;
        }

        // Regex explanation:
        // .*              - any characters (0 or more)
        // ([A-Z])         - capture group 1: any capital letter A-Z
        // .*              - any characters (0 or more)
        // \\1             - backreference to the captured capital letter
        // .*              - any characters (0 or more)
        // This checks if the same capital letter appears at least twice in the string
        return str.matches(".*([A-Z]).*\\1.*");
    }
}