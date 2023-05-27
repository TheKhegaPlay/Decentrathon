package main.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class utils {

    public static void main(String[] args) {
        final String defaultPasswordFile = "password";
        final String defaultKeyFile = "key.json";

        String password = getPassword(defaultPasswordFile);
        if (password == null) {
            System.out.println("Failed to read password file");
            return;
        }

        String keyFilePath = args.length > 0 ? args[0] : defaultKeyFile;
        String keyContent = getKeyFileContent(keyFilePath);
        if (keyContent == null) {
            System.out.println("Failed to read key file");
            return;
        }

        String address;
        try {
            address = getAddressFromKeyFile(keyContent);
        } catch (Exception e) {
            System.out.println("Failed to get address from key file");
            return;
        }

        System.out.println("Password: " + password);
        System.out.println("Key File: " + keyFilePath);
        System.out.println("Address: " + address);
    }

    private static String getPassword(String passwordFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(passwordFile));
            return lines.get(0).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getKeyFileContent(String keyFilePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(keyFilePath));
            return lines.stream().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getAddressFromKeyFile(String keyContent) throws Exception {
        // Implement the logic to extract the address from the key file content
        // You may need to use JSON parsing libraries or perform string manipulation
        // based on the format of the key file
        throw new Exception("Not implemented");
    }
}
