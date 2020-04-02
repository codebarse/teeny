package com.teeny.utils;

public class Utils {

    /**
     * Takes an input ID and produces a teeny URL.
     *
     * Base-62 encodes the identifier.
     */
    public static String idToTeenyUrl(long id) {
        // Mapping which defines the 62 possible output characters.
        String map = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String shortUrl = "";

        // Convert given ID to a base-62 number.
        while (id>0) {
            // Append each character mapped by the remainder.
            shortUrl += map.charAt((int)(id % 62));
            id /= 62;
        }

        // Reverse the string to complete the base conversion.
        shortUrl = new StringBuilder(shortUrl).reverse().toString();
        return shortUrl;
    }

    /**
     * Converts a teeny URL into the corresponding ID.
     *
     * Base-62 decodes the input string.
     */
    public static long teenyUrlToId(String teenyUrl) {
        long id = 0;

        // Base decode conversion logic.
        for (int i = 0; i < teenyUrl.length(); ++i) {
            if ('a' <= teenyUrl.charAt(i) && teenyUrl.charAt(i) <= 'z') {
                id = id * 62 + teenyUrl.charAt(i) - 'a';
            }
            if ('A' <= teenyUrl.charAt(i) && teenyUrl.charAt(i) <= 'Z') {
                id = id * 62 +teenyUrl.charAt(i) - 'A' + 26;
            }
            if ('0' <= teenyUrl.charAt(i) && teenyUrl.charAt(i) <= '9') {
                id = id * 62 + teenyUrl.charAt(i) - '0' + 52;
            }
        }
        return id;
    }

    public static void main(String[] args) {
        long input = 125;
        System.out.println(input);
        String a = idToTeenyUrl(input);
        System.out.println(a);
        System.out.println(teenyUrlToId(a));
    }
}
