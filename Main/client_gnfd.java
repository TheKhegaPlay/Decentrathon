package main.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.domain.NodeInfo;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
import com.binance.dex.api.client.encoding.message.Token;
import com.binance.dex.api.client.encoding.message.TransactionRequestAssembler;
import com.binance.dex.api.client.encoding.message.input.Input;

public class Main {
    private static final String CONFIG_FILE = "config.json";
    private static final String DEFAULT_KEY_FILE = "keyfile.json";

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("c", "chain-id", true, "Chain ID");
        options.addOption("a", "address", true, "RPC address");
        options.addOption("k", "keystore", true, "Keystore file path");
        options.addOption("h", "host", true, "Host");
        options.addOption("u", "url", true, "URL");
        options.addOption("p", "password", true, "Password");

        DefaultParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Failed to parse command line arguments: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar <jar-file>", options);
            return;
        }

        String chainId = cmd.getOptionValue("chain-id");
        String address = cmd.getOptionValue("address");
        String keystore = cmd.getOptionValue("keystore");
        String host = cmd.getOptionValue("host");
        String url = cmd.getOptionValue("url");
        String password = cmd.getOptionValue("password");

        if (chainId == null || address == null || keystore == null || password == null) {
            System.out.println("Missing required arguments. Please provide chain ID, address, keystore, and password.");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar <jar-file>", options);
            return;
        }

        String rpcAddr = address.startsWith("https://") ? address : "https://" + address;

        String keyfilePath = keystore.isEmpty() ? DEFAULT_KEY_FILE : keystore;
        Path keyPath = Paths.get(keyfilePath);
        String keyjson;
        try {
            keyjson = new String(Files.readAllBytes(keyPath));
        } catch (IOException e) {
            System.out.println("Failed to read the keyfile at '" + keyfilePath + "': " + e.getMessage());
            return;
        }

        BinanceDexApiClientFactory factory = BinanceDexApiClientFactory.newInstance();
        factory.setNodeUrl(rpcAddr);
        factory.setApiKey(keyjson);

        BinanceDexEnvironment environment = BinanceDexEnvironment.PROD;

        BinanceDexApiClientFactory factory = BinanceDexApiClientFactory.newInstance();
        factory.setNodeUrl(rpcAddr);
        factory.setApiKey(keyjson);
        
        NodeInfo nodeInfo = factory.newNodeApiClient().getNodeInfo();
        System.out.println("Node Info: " + nodeInfo);

        Transfer transfer = new Transfer();
        transfer.setFromAddress(address);
        transfer.setToAddress(address);
        transfer.addToken(Token.BNB, "1");

        TransactionOption options = new TransactionOption(DEFAULT_ACCOUNT_NUMBER, sequenceNumber, DEFAULT_CHAIN_ID);
        options.setAccountNumber(accountNumber);
        options.setSequence(sequenceNumber);
        options.setMemo("Test Transfer");

        Input input = new Input();
        input.setAddress(address);
        input.setCoins(transfer.getCoins());
        input.setSequence(sequenceNumber);
        input.setAccountNumber(accountNumber);

        TransactionRequestAssembler assembler = new TransactionRequestAssembler();
        byte[] unsignedBytes = assembler.buildTransferRequest(input, options);
        System.out.println("Unsigned Tx Hex: " + Hex.encodeHexString(unsignedBytes));

        // Sign transaction
        byte[] signature = Signer.sign(unsignedBytes, privateKey);

        // Broadcast transaction
        String signedHex = Hex.encodeHexString(signature);
        String result = apiClient.broadcast(signedHex);
        System.out.println("Broadcast Result: " + result);
    }
}
