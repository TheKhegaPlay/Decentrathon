package main.main;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "delete", description = "delete an existed bucket", mixinStandardHelpOptions = true)
public class DeleteBucket implements Callable<Void> {

    @Parameters(paramLabel = "BUCKET-URL", description = "the bucket URL")
    private String bucketUrl;

    public static void main(String[] args) {
        CommandLine.call(new DeleteBucket(), args);
    }

    @Override
    public Void call() {
        String bucketName = getBucketNameByUrl(bucketUrl);

        // Delete the bucket
        GroupClient client = new GroupClient();

        if (!client.headBucket(bucketName)) {
            System.out.println("Bucket does not exist.");
            return null;
        }

        // Send the delete bucket request
        client.deleteBucket(bucketName);

        return null;
    }

    private String getBucketNameByUrl(String bucketUrl) {
        // Extract the bucket name from the URL
        String[] parts = bucketUrl.split("/");
        return parts[parts.length - 1];
    }
}
