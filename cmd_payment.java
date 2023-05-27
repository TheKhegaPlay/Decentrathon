package main.main;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.urora.spectrum.pb.*;
import picocli.CommandLine;

import java.util.concurrent.TimeUnit;

@CommandLine.Command(name = "buy-quota", description = "update bucket quota info")
class BuyQuota implements Runnable {
    @CommandLine.Parameters(description = "BUCKET-URL")
    private String bucketUrl;

    @CommandLine.Option(names = "--chargedQuota", required = true, description = "indicate the target quota to be set for the bucket")
    private long chargedQuota;

    @Override
    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        BucketServiceGrpc.BucketServiceBlockingStub stub = BucketServiceGrpc.newBlockingStub(channel);

        String bucketName = getBucketNameFromUrl(bucketUrl);

        HeadBucketRequest headBucketRequest = HeadBucketRequest.newBuilder()
                .setBucketName(bucketName)
                .build();

        HeadBucketResponse headBucketResponse = stub.headBucket(headBucketRequest);
        if (!headBucketResponse.getExists()) {
            System.out.println("Bucket does not exist");
            return;
        }

        BuyQuotaRequest buyQuotaRequest = BuyQuotaRequest.newBuilder()
                .setBucketName(bucketName)
                .setTargetQuota(chargedQuota)
                .build();

        BuyQuotaResponse buyQuotaResponse = stub.buyQuota(buyQuotaRequest);

        System.out.println("Buy quota for bucket: " + bucketName + " successfully, txn hash: " + buyQuotaResponse.getTxnHash());

        channel.shutdown();
        try {
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@CommandLine.Command(name = "quota-info", description = "get quota info of the bucket")
class QuotaInfo implements Runnable {
    @CommandLine.Parameters(description = "BUCKET-URL")
    private String bucketUrl;

    @Override
    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        BucketServiceGrpc.BucketServiceBlockingStub stub = BucketServiceGrpc.newBlockingStub(channel);

        String bucketName = getBucketNameFromUrl(bucketUrl);

        HeadBucketRequest headBucketRequest = HeadBucketRequest.newBuilder()
                .setBucketName(bucketName)
                .build();

        HeadBucketResponse headBucketResponse = stub.headBucket(headBucketRequest);
        if (!headBucketResponse.getExists()) {
            System.out.println("Bucket does not exist");
            return;
        }

        GetBucketReadQuotaRequest getBucketReadQuotaRequest = GetBucketReadQuotaRequest.newBuilder()
                .setBucketName(bucketName)
                .build();

        GetBucketReadQuotaResponse getBucketReadQuotaResponse = stub.getBucketReadQuota(getBucketReadQuotaRequest);

        System.out.println("quota info:");
        System.out.println("charged quota: " + getBucketReadQuotaResponse.getReadQuotaSize());
        System.out.println("free quota: " + getBucketReadQuotaResponse.getSpFreeReadQuotaSize());
        System.out.println("consumed quota: " + getBucketReadQuotaResponse.getReadConsumedSize());

        channel.shutdown();
        try {
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@CommandLine.Command(name = "gnfd-cmd", subcommands = {BuyQuota.class, QuotaInfo.class})
public class Main implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new Main(), args);
    }

    @Override
    public void run() {
        System.out.println("Main command");
    }

    private static String getBucketNameFromUrl(String url) {
        // implement the logic to extract the bucket name from the URL
        return "";
    }
}
