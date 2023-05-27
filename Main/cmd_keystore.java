package main.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "create-keystore", description = "create a new keystore file", mixinStandardHelpOptions = true)
public class GenerateKey implements Runnable {

    @Option(names = { "--privKeyFile" }, description = "the private key file path which contains the original private hex string", required = true)
    private String privKeyFile;

    @Parameters(arity = "0..1", paramLabel = "<keyfile>", description = "the output keystore file path")
    private String keyFilePath;

    private static final String defaultKeyfile = "key.json";

    public static void main(String[] args) {
        CommandLine.run(new GenerateKey(), args);
    }

    @Override
    public void run() {
        if (keyFilePath == null || keyFilePath.isEmpty()) {
            keyFilePath = defaultKeyfile;
        }

        File keyFile = new File(keyFilePath);
        if (keyFile.exists()) {
            System.err.println("Key already exists at: " + keyFilePath);
            System.exit(1);
        }

        File keyFileDir = keyFile.getParentFile();
        if (!keyFileDir.exists() && !keyFileDir.mkdirs()) {
            System.err.println("Failed to create directory: " + keyFileDir.getPath());
            System.exit(1);
        }

        File privKeyFile = new File(this.privKeyFile);
        if (!privKeyFile.exists()) {
            System.err.println("Private key file not found: " + this.privKeyFile);
            System.exit(1);
        }

        try {
            List<String> lines = Files.readAllLines(privKeyFile.toPath());
            if (lines.isEmpty()) {
                System.err.println("Private key file is empty: " + this.privKeyFile);
                System.exit(1);
            }
            
            String privateKey = lines.get(0);
            
            Key key = new Key();
            key.setPrivateKey(privateKey);
            // Set address

            // Fetch password content

            // Encrypt the private key

            // Store the keystore file

            System.out.printf("Generate keystore %s successfully, key address: %s%n", keyFilePath, key.getAddress());
        } catch (IOException e) {
            System.err.println("Error reading private key file: " + e.getMessage());
            System.exit(1);
        }
    }
}

class Key {
    private String address;
    private String privateKey;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
