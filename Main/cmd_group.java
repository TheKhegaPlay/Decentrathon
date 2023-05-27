package main.main;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "create", description = "create a new group", mixinStandardHelpOptions = true)
public class CreateGroup implements Callable<Void> {

    @Parameters(paramLabel = "GROUP-URL", description = "the group URL")
    private String groupUrl;

    @Option(names = {"--initMembers"}, description = "indicate the initial member address string list")
    private String initMembers;

    public static void main(String[] args) {
        CommandLine.call(new CreateGroup(), args);
    }

    @Override
    public Void call() {
        String groupName = getGroupNameByUrl(groupUrl);

        // Create the group
        GroupClient client = new GroupClient();
        CreateGroupOptions options = new CreateGroupOptions();

        if (initMembers != null && !initMembers.isEmpty()) {
            List<String> memberList = parseAddrList(initMembers);
            options.setInitGroupMember(memberList);
        }

        // Send the create group request
        client.createGroup(groupName, options);

        return null;
    }

    private String getGroupNameByUrl(String groupUrl) {
        // Extract the group name from the URL
        return groupUrl.substring(groupUrl.lastIndexOf("/") + 1);
    }

    private List<String> parseAddrList(String initMembersInfo) {
        // Parse the member address string list
        List<String> memberList = new ArrayList<>();
        String[] members = initMembersInfo.split(",");
        for (String member : members) {
            memberList.add(member.trim());
        }
        return memberList;
    }
}
