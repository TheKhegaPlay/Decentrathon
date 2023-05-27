package main.main;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.urora.spectrum.pb.*;
import picocli.CommandLine;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@CommandLine.Command(name = "ls", description = "list storage providers info")
class ListSP implements Runnable {
    @Override
    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        StorageProviderServiceGrpc.StorageProviderServiceBlockingStub stub = StorageProviderServiceGrpc.newBlockingStub(channel);

        ListStorageProvidersRequest request = ListStorageProvidersRequest.newBuilder().setIncludeStatus(false).build();

        Iterator<StorageProvider> responseIterator = stub.listStorageProviders(request);

        System.out.println("SP list:");
        while (responseIterator.hasNext()) {
            StorageProvider storageProvider = responseIterator.next();
            System.out.println(String.format("sp[%d]: operator-address:%s, endpoint:%s, Status:%s",
                    storageProvider.getId(), storageProvider.getOperatorAddress(), storageProvider.getEndpoint(), storageProvider.getStatus()));
        }

        channel.shutdown();
        try {
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@CommandLine.Command(name = "head", description = "get storage provider details")
class GetSP implements Runnable {
    @CommandLine.Parameters(description = "<Storage Provider endpoint>")
    private String endpoint;

    @Override
    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        StorageProviderServiceGrpc.StorageProviderServiceBlockingStub stub = StorageProviderServiceGrpc.newBlockingStub(channel);

        ListStorageProvidersRequest request = ListStorageProvidersRequest.newBuilder().setIncludeStatus(false).build();

        Iterator<StorageProvider> responseIterator = stub.listStorageProviders(request);

        boolean foundSP = false;
        while (responseIterator.hasNext()) {
            StorageProvider storageProvider = responseIterator.next();
            if (storageProvider.getEndpoint().equals(endpoint)) {
                foundSP = true;
                StorageProviderInfo spInfo = stub.getStorageProviderInfo(storageProvider.getOperatorAddress());
                System.out.println("SP info:");
                System.out.println(spInfo.toString());
                System.out.println("Status: " + spInfo.getStatus());
                break;
            }
        }

        if (!foundSP) {
            System.out.println("Fail to get SP info");
        }

        channel.shutdown();
        try {
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@CommandLine.Command(name = "get-price", description = "get the quota price of the SP")
class GetQuotaPrice implements Runnable {
    @CommandLine.Parameters(description = "<Storage Provider endpoint>")
    private String endpoint;

    @Override
    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        StorageProviderServiceGrpc.StorageProviderServiceBlockingStub stub = StorageProviderServiceGrpc.newBlockingStub(channel);

        ListStorageProvidersRequest request = ListStorageProvidersRequest.newBuilder().setIncludeStatus(false).build();

        Iterator<StorageProvider> responseIterator = stub.listStorageProviders(request);

        boolean foundSP = false;
        while (responseIterator.hasNext()) {
            StorageProvider storageProvider = responseIterator.next();
            if (storageProvider.getEndpoint().equals(endpoint)) {
                foundSP = true;
                StoragePrice price = stub.getStoragePrice(storageProvider.getOperatorAddress());
                double quotaPrice = price.getReadPrice().getFloatValue();
                double storagePrice = price.getStorePrice().getFloatValue();
                System.out.println("get bucket read quota price: " + quotaPrice + " wei/byte");
                System.out.println("get bucket storage price: " + storagePrice + " wei/byte");
                break;
            }
        }

        if (!foundSP) {
            System.out.println("Fail to get SP info");
        }

        channel.shutdown();
        try {
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@CommandLine.Command(name = "gnfd-cmd", subcommands = { ListSP.class, GetSP.class, GetQuotaPrice.class })
public class Main implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new Main(), args);
    }

    @Override
    public void run() {
        System.out.println("Main command");
    }
}
