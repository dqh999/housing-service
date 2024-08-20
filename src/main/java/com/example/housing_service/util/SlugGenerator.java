package com.example.housing_service.util;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SlugGenerator {

    private static Set<String> slugSet = new HashSet<>();
    private static final Random RANDOM = new Random();

    public static String generateUniqueSlug(String title) {
        String baseSlug = titleToSlug(title);
        String uniqueSlug = baseSlug + "-" + generateRandomCode(5, 10);
        while (slugSet.contains(uniqueSlug)) {
            uniqueSlug = baseSlug + "-" + generateRandomCode(5, 10);
        }
        slugSet.add(uniqueSlug);
        return uniqueSlug;
    }

    private static String titleToSlug(String title) {
        // Convert title to lowercase and normalize
        String slug = Normalizer.normalize(title.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "") // Remove accents
                .replaceAll("[^a-z0-9]+", "-") // Replace non-alphanumeric with hyphens
                .replaceAll("^-|-$", ""); // Remove leading or trailing hyphens

        return slug;
    }

    private static String generateRandomCode(int minLength, int maxLength) {
        int length = minLength + RANDOM.nextInt(maxLength - minLength + 1);
        StringBuilder code = new StringBuilder(length);
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }
}
