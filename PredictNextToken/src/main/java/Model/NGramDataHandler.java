package Model;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NGramDataHandler implements TokenProcessor {
    private final int n;
    private final AmazonDynamoDB dynamoDBClient;
    private final Map<String, Map<String, Integer>> nGrams;

    public NGramDataHandler(int n) {
        this.n = n;
        this.dynamoDBClient = AmazonDynamoDBClientBuilder.standard().build();
        this.nGrams = new HashMap<>();
        loadNGramsFromDynamoDB();
    }

    private void loadNGramsFromDynamoDB() {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("TokenDB");
        ScanResult scanResult = dynamoDBClient.scan(scanRequest);

        for (Map<String, AttributeValue> item : scanResult.getItems()) {
            String token = item.get("token").getS();
            List<String> tokenList = Arrays.asList(token.split("\\s+"));
            if (tokenList.size() >= n) {
                for (int i = 0; i <= tokenList.size() - n; i++) {
                    String context = String.join(" ", tokenList.subList(i, i + n - 1));
                    String nextToken = tokenList.get(i + n - 1);

                }
            }
        }
    }
    @Override
    public void processTokens(List<String> tokens) {
        for (int i = 0; i <= tokens.size() - n; i++) {
            String key = String.join(" ", tokens.subList(i, i + n - 1));
            String nextToken = tokens.get(i + n - 1);
            nGrams.putIfAbsent(key, new HashMap<>());
            Map<String, Integer> tokenMap = nGrams.get(key);
            tokenMap.put(nextToken, tokenMap.getOrDefault(nextToken, 0) + 1);
        }
    }

    public Map<String, Map<String, Integer>> getNGrams() {
        return nGrams;
    }
}
