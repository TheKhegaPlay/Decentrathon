package main.main;
import java.util.Arrays;

import com.github.urfave.cli.Cli;
import com.github.urfave.cli.Command;
import com.github.urfave.cli.Flag;
import com.github.urfave.cli.HelpPrinter;
import com.github.urfave.cli.Option;
import com.github.urfave.cli.SubCommand;

public class Main {
    public static void main(String[] args) {
        Option hostOption = Option.builder()
                .longName("host")
                .description("host name of request")
                .build();

        Option rpcAddrOption = Option.builder()
                .longName("rpcAddr")
                .description("greenfield chain client rpc address")
                .build();

        Option chainIdOption = Option.builder()
                .longName("chainId")
                .description("greenfield chainId")
                .build();

        Option passwordFileOption = Option.builder()
                .longName("passwordFile")
                .description("password file for encrypting and decoding the private key")
                .required(false)
                .build();

        Option configFileOption = Option.builder()
                .longName("config")
                .shortName("c")
                .description("Load configuration from FILE")
                .defaultValue("config.toml")
                .build();

        Option keystoreOption = Option.builder()
                .longName("keystore")
                .shortName("k")
                .description("key file path")
                .defaultValue("defaultKeyfile")
                .build();

        Command bucketCommand = Command.builder()
                .name("bucket")
                .description("support the bucket operation functions, including create/update/delete/head/list")
                .subCommands(
                        Command.builder().executor(Main::cmdCreateBucket).build(),
                        Command.builder().executor(Main::cmdUpdateBucket).build(),
                        Command.builder().executor(Main::cmdDelBucket).build(),
                        Command.builder().executor(Main::cmdHeadBucket).build(),
                        Command.builder().executor(Main::cmdListBuckets).build()
                )
                .build();

        Command objectCommand = Command.builder()
                .name("object")
                .description("support the object operation functions, including put/get/update/delete/head/list and so on")
                .subCommands(
                        Command.builder().executor(Main::cmdPutObj).build(),
                        Command.builder().executor(Main::cmdGetObj).build(),
                        Command.builder().executor(Main::cmdDelObject).build(),
                        Command.builder().executor(Main::cmdHeadObj).build(),
                        Command.builder().executor(Main::cmdCancelObjects).build(),
                        Command.builder().executor(Main::cmdListObjects).build(),
                        Command.builder().executor(Main::cmdCalHash).build(),
                        Command.builder().executor(Main::cmdCreateFolder).build(),
                        Command.builder().executor(Main::cmdUpdateObject).build(),
                        Command.builder().executor(Main::cmdGetUploadProgress).build()
                )
                .build();

        Command groupCommand = Command.builder()
                .name("group")
                .description("support the group operation functions, including create/update/delete/head/head-member")
                .subCommands(
                        Command.builder().executor(Main::cmdCreateGroup).build(),
                        Command.builder().executor(Main::cmdUpdateGroup).build(),
                        Command.builder().executor(Main::cmdHeadGroup).build(),
                        Command.builder().executor(Main::cmdHeadGroupMember).build(),
                        Command.builder().executor(Main::cmdDelGroup).build()
                )
                .build();

        Command crosschainCommand = Command.builder()
                .name("crosschain")
                .description("support the cross-chain functions, including transfer and mirror")
                .subCommands(
                        Command.builder().executor(Main::cmdMirrorResource).build(),
                        Command.builder().executor(Main::cmdTransferOut).build()
                )
                .build();

        Command bankCommand = Command.builder()
                .name("bank")
                .description("support the bank functions, including transfer in greenfield and query balance")
                .subCommands(
                        Command.builder().executor(Main::cmdTransfer).build(),
                        Command.builder().executor(Main::cmdGetAccountBalance).build()
                )
                .build();

        Command policyCommand = Command.builder()
                .name("policy")
                .description("support object policy and bucket policy operation functions")
                .subCommands(
                        Command.builder().executor(Main::cmdPutObjPolicy).build(),
                        Command.builder().executor(Main::cmdPutBucketPolicy).build()
                )
                .build();

        Command paymentCommand = Command.builder()
                .name("payment")
                .description("support the payment operation functions")
                .subCommands(
                        Command.builder().executor(Main::cmdCreatePaymentAccount).build(),
                        Command.builder().executor(Main::cmdPaymentDeposit).build(),
                        Command.builder().executor(Main::cmdPaymentWithdraw).build(),
                        Command.builder().executor(Main::cmdListPaymentAccounts).build(),
                        Command.builder().executor(Main::cmdBuyQuota).build(),
                        Command.builder().executor(Main::cmdGetQuotaInfo).build()
                )
                .build();

        Command spCommand = Command.builder()
                .name("sp")
                .description("support the storage provider operation functions")
                .subCommands(
                        Command.builder().executor(Main::cmdListSP).build(),
                        Command.builder().executor(Main::cmdGetSP).build(),
                        Command.builder().executor(Main::cmdGetQuotaPrice).build()
                )
                .build();

        Command generateKeyCommand = Command.builder()
                .executor(Main::cmdGenerateKey)
                .build();

        Cli app = Cli.builder()
                .name("gnfd-cmd")
                .description("cmd tool for supporting making request to greenfield")
                .options(Arrays.asList(hostOption, rpcAddrOption, chainIdOption, passwordFileOption, configFileOption, keystoreOption))
                .commands(Arrays.asList(bucketCommand, objectCommand, groupCommand, crosschainCommand, bankCommand, policyCommand, paymentCommand, spCommand, generateKeyCommand))
                .before(Main::beforeCommand)
                .build();

        app.run(args);
    }

    private static void beforeCommand(Context context) {
        // Initialize input source here if needed
    }

    private static void cmdCreateBucket(Context context) {
        // Implementation for cmdCreateBucket
    }

    private static void cmdUpdateBucket(Context context) {
        // Implementation for cmdUpdateBucket
    }

    private static void cmdDelBucket(Context context) {
        // Implementation for cmdDelBucket
    }

    private static void cmdHeadBucket(Context context) {
        // Implementation for cmdHeadBucket
    }

    private static void cmdListBuckets(Context context) {
        // Implementation for cmdListBuckets
    }

    private static void cmdPutObj(Context context) {
        // Implementation for cmdPutObj
    }

    private static void cmdGetObj(Context context) {
        // Implementation for cmdGetObj
    }

    private static void cmdDelObject(Context context) {
        // Implementation for cmdDelObject
    }

    private static void cmdHeadObj(Context context) {
        // Implementation for cmdHeadObj
    }

    private static void cmdCancelObjects(Context context) {
        // Implementation for cmdCancelObjects
    }

    private static void cmdListObjects(Context context) {
        // Implementation for cmdListObjects
    }

    private static void cmdCalHash(Context context) {
        // Implementation for cmdCalHash
    }

    private static void cmdCreateFolder(Context context) {
        // Implementation for cmdCreateFolder
    }

    private static void cmdUpdateObject(Context context) {
        // Implementation for cmdUpdateObject
    }

    private static void cmdGetUploadProgress(Context context) {
        // Implementation for cmdGetUploadProgress
    }

    private static void cmdCreateGroup(Context context) {
        // Implementation for cmdCreateGroup
    }

    private static void cmdUpdateGroup(Context context) {
        // Implementation for cmdUpdateGroup
    }

    private static void cmdHeadGroup(Context context) {
        // Implementation for cmdHeadGroup
    }

    private static void cmdHeadGroupMember(Context context) {
        // Implementation for cmdHeadGroupMember
    }

    private static void cmdDelGroup(Context context) {
        // Implementation for cmdDelGroup
    }

    private static void cmdMirrorResource(Context context) {
        // Implementation for cmdMirrorResource
    }

    private static void cmdTransferOut(Context context) {
        // Implementation for cmdTransferOut
    }

    private static void cmdTransfer(Context context) {
        // Implementation for cmdTransfer
    }

    private static void cmdGetAccountBalance(Context context) {
        // Implementation for cmdGetAccountBalance
    }

    private static void cmdPutObjPolicy(Context context) {
        // Implementation for cmdPutObjPolicy
    }

    private static void cmdPutBucketPolicy(Context context) {
        // Implementation for cmdPutBucketPolicy
    }

    private static void cmdCreatePaymentAccount(Context context) {
        // Implementation for cmdCreatePaymentAccount
    }

    private static void cmdPaymentDeposit(Context context) {
        // Implementation for cmdPaymentDeposit
    }

    private static void cmdPaymentWithdraw(Context context) {
        // Implementation for cmdPaymentWithdraw
    }

    private static void cmdListPaymentAccounts(Context context) {
        // Implementation for cmdListPaymentAccounts
    }

    private static void cmdBuyQuota(Context context) {
        // Implementation for cmdBuyQuota
    }

    private static void cmdGetQuotaInfo(Context context) {
        // Implementation for cmdGetQuotaInfo
    }

    private static void cmdListSP(Context context) {
        // Implementation for cmdListSP
    }

    private static void cmdGetSP(Context context) {
        // Implementation for cmdGetSP
    }

    private static void cmdGetQuotaPrice(Context context) {
        // Implementation for cmdGetQuotaPrice
    }

    private static void cmdGenerateKey(Context context) {
        // Implementation for cmdGenerateKey
    }
}
