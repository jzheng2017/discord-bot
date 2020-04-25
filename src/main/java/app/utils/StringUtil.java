package app.utils;

import app.constants.Message;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

    public static String truncate(String text) {
        if (text.length() > Message.MAX_MESSAGE_CHARACTERS_LENGTH) {
            return text.substring(0, Message.MAX_MESSAGE_CHARACTERS_LENGTH);
        } else {
            return text;
        }
    }

    public static int occurrences(String haystack, String needle) {
        int occurrences = 0;

        Pattern pattern = Pattern.compile(needle, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(haystack);

        while (matcher.find()) {
            occurrences++;
        }

        return occurrences;
    }

    public static String topNOccurrences(String text, int limit, String delimiter) {
        StringBuilder topTen = new StringBuilder();
        List<String> words = Arrays.asList(text.split(delimiter));
        Map<String, Long> map = words.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        List<Map.Entry<String, Long>> results = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toList());

        Iterator it = results.iterator();
        int counter = 1;
        int total = words.size();

        while (it.hasNext()) {
            Map.Entry result = (Map.Entry) it.next();
            int occurrence = Integer.parseInt(result.getValue().toString());
            topTen
                    .append(counter++)
                    .append(". ")
                    .append(result.getKey())
                    .append(": ")
                    .append(result.getValue())
                    .append("**")
                    .append(String.format("  %.2f", ((float) occurrence / total * 100)))
                    .append("%** \n");
        }

        return topTen.toString();
    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
