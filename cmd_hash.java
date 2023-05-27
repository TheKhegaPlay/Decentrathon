package main.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "get-hash", description = "compute the integrity hash of file", mixinStandardHelpOptions = true)
public class ComputeHashRoot implements Runnable {

    @Parameters(paramLabel = "filePath", description = "the path to the file")
    private String filePath;

    public static void main(String[] args) {
        CommandLine.run(new ComputeHashRoot(), args);
    }

    @Override
    public void run() {
        try {
            // Read the file content and compute the hash
            byte[] fileContent = readFileContent(filePath);
            String hash = computeHash(fileContent);
            System.out.println("Hash: " + hash);
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Failed to compute hash: " + e.getMessage());
        }
    }

    private byte[] readFileContent(String filePath) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            byte[] fileContent = new byte[fis.available()];
            fis.read(fileContent);
            return fileContent;
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    private String computeHash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(data);
        return bytesToHex(hashBytes);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
