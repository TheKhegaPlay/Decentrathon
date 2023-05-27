package main.main;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiException;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.encoding.message.common.Dec;
import com.binance.dex.api.client.encoding.message.common.Decoder;
import com.binance.dex.api.client.encoding.message.common.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws BinanceDexApiException {
        String filePath = args[0];
        String objectUrl = args[1];

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Upload file does not exist");
        }

        String bucketName;
        String objectName;

        try {
            bucketName = parseBucketName(objectUrl);
            objectName = parseObjectName(objectUrl);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse bucket name and object name");
        }

        BinanceDexApiClientFactory factory = BinanceDexApiClientFactory.newInstance();
        BinanceDexApiRestClient client = factory.newRestClient();

        CreateObjectOptions options = new CreateObjectOptions();
        options.setContentType("");
        options.setVisibility(Visibility.PRIVATE);

        try (FileInputStream inputStream = new FileInputStream(file)) {
            client.createObject(bucketName, objectName, inputStream, options);

            PutObjectOptions putObjectOptions = new PutObjectOptions();
            putObjectOptions.setContentType("");

            client.putObject(bucketName, objectName, file.length(), new FileInputStream(file), putObjectOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if object is sealed
        for (int i = 0; i < 15; i++) {
            ObjectMetadata metadata = client.getObjectMetadata(bucketName, objectName);
            if (metadata.getObjectStatus() == ObjectStatus.OBJECT_STATUS_SEALED) {
                System.out.println("Put object successfully");
                return;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        throw new IllegalStateException("Object not sealed after 15 seconds");
    }

    private static String parseBucketName(String objectUrl) {
        String[] parts = objectUrl.split("/");
        if (parts.length >= 2) {
            return parts[1];
        }
        throw new IllegalArgumentException("Failed to parse bucket name");
    }

    private static String parseObjectName(String objectUrl) {
        String[] parts = objectUrl.split("/");
        if (parts.length >= 3) {
            return String.join("/", Arrays.copyOfRange(parts, 2, parts.length));
        }
        throw new IllegalArgumentException("Failed to parse object name");
    }
}
