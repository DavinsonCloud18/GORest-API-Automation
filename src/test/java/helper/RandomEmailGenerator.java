package helper;

import java.util.Random;

public class RandomEmailGenerator {

    private static final String[] domains = { "naruto.co.id", "onepiece.com", "bokunohero.gov", "sololeveling.org"};

    public static String generateRandomEmail() {
        // Generate a random local part (username)
        String localPart = generateRandomString(10); // You can specify the desired length

        // Randomly select a domain from the list
        String domain = domains[new Random().nextInt(domains.length)];

        // Construct the email address
        return localPart + "@" + domain;
    }

    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return stringBuilder.toString();
    }
}

