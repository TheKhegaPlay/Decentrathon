package main.main;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.math.BigInteger;
import java.util.concurrent.Callable;

@Command(name = "transfer-out", description = "transfer from greenfield to a BSC account", mixinStandardHelpOptions = true)
public class TransferOut implements Callable<Void> {

    @Parameters(paramLabel = "--toAddress", description = "the receiver address in BSC")
    private String toAddress;

    @Parameters(paramLabel = "--amount", description = "the amount of BNB to be sent")
    private String amount;

    public static void main(String[] args) {
        CommandLine.call(new TransferOut(), args);
    }

    @Override
    public Void call() {
        // Create a cross-chain transfer from Greenfield to a BSC account
        GreenfieldClient client = new GreenfieldClient();

        // Convert the amount string to a BigInteger
        BigInteger transferAmount = new BigInteger(amount);

        // Perform the transfer
        client.transferOut(toAddress, transferAmount);

        return null;
    }
}
