package main.main;

import java.util.Arrays;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "head", description = "query object info", mixinStandardHelpOptions = true)
public class HeadObject implements Runnable {

    @Parameters(paramLabel = "OBJECT-URL", description = "the object URL")
    private String objectUrl;

    public static void main(String[] args) {
        CommandLine.run(new HeadObject(), args);
    }

    @Override
    public void run() {
        String[] urlParts = objectUrl.split("://");
        if (urlParts.length != 2) {
            System.err.println("Invalid object URL format: " + objectUrl);
            System.exit(1);
        }

        String bucketName = urlParts[1].split("/")[0];
        String objectName = urlParts[1].substring(bucketName.length() + 1);

        // TODO: Send headObject txn to chain and fetch object info on greenfield chain

        System.out.println("Querying object info for: " + objectUrl);
    }
}

@Command(name = "head", description = "query bucket info", mixinStandardHelpOptions = true)
public class HeadBucket implements Runnable {

    @Parameters(paramLabel = "BUCKET-URL", description = "the bucket URL")
    private String bucketUrl;

    public static void main(String[] args) {
        CommandLine.run(new HeadBucket(), args);
    }

    @Override
    public void run() {
        String bucketName = bucketUrl.split("://")[1];

        // TODO: Send headBucket txn to chain and fetch bucket info on greenfield chain

        System.out.println("Querying bucket info for: " + bucketUrl);
    }
}

@Command(name = "head", description = "query group info", mixinStandardHelpOptions = true)
public class HeadGroup implements Runnable {

    @Parameters(paramLabel = "GROUP-URL", description = "the group URL")
    private String groupUrl;

    @Option(names = { "--groupOwner" }, description = "the owner address of the group")
    private String groupOwner;

    public static void main(String[] args) {
        CommandLine.run(new HeadGroup(), args);
    }

    @Override
    public void run() {
        String groupName = groupUrl.split("://")[1];

        // TODO: Send headGroup txn to chain and fetch group info on greenfield chain

        System.out.println("Querying group info for: " + groupUrl);
        if (groupOwner != null) {
            System.out.println("Group owner: " + groupOwner);
        }
    }
}

@Command(name = "head-member", description = "check if a group member exists", mixinStandardHelpOptions = true)
public class HeadGroupMember implements Runnable {

    @Parameters(paramLabel = "GROUP-URL", description = "the group URL")
    private String groupUrl;

    @Option(names = { "--headMember" }, description = "the head member address")
    private String headMember;

    @Option(names = { "--groupOwner" }, description = "the owner address of the group")
    private String groupOwner;

    public static void main(String[] args) {
        CommandLine.run(new HeadGroupMember(), args);
    }

    @Override
    public void run() {
        String groupName = groupUrl.split("://")[1];

        // TODO: Send headGroupMember txn to chain and check if member is in the group

        System.out.println("Checking if member exists in group: " + groupUrl);
        if (headMember != null) {
            System.out.println("Head member address: " + headMember);
        }
        if (groupOwner != null) {
            System.out.println("Group owner: " + groupOwner);
        }
    }
}
