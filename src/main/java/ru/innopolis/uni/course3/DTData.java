package ru.innopolis.uni.course3;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class DTData {

    public final static String CYRILLIC_LETTERS = "\\p{InCYRILLIC}";                    //"А-Яа-яЁё"
    public final static String DIGITS = "\\p{IsDigit}";                                 //"0-9"
    public final static String PUNCTUATION_MARKS = "\\p{Punct}" + "„“«»“”‘’‹›‒–—―‐-";  //"!\"#$%&'()\\*\\+,-\\./:;<=>\\?@\\[\\]\\^_`{|}~„“«»“”‘’‹›‒–—―‐-";
    public final static String WHITESPACES = "\\p{Space}";                              //"\\t\\n\\x0B\\f\\r"

    // RegExp is "[\\p{Space}\\p{InCYRILLIC}\\p{IsDigit}\\p{Punct}]*"
    public final static String VALID_CHARACTERS = WHITESPACES + CYRILLIC_LETTERS + DIGITS  + PUNCTUATION_MARKS;
    public final static String EXCLUDED_CHARACTERS = DIGITS + PUNCTUATION_MARKS;

    public final static Map<String, Integer> MAP = new TreeMap<>(); //new ConcurrentSkipListMap<>();
    public static volatile Boolean isShutDown = false;

}
