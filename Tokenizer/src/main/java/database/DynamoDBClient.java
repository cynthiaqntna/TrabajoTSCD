package database;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamoDBClient {
    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    public void saveTokensToDynamoDB(List<String> tokens, LambdaLogger logger) {
        for (String token : tokens) {
            Map<String, AttributeValue> item = new HashMap<>();
            item.put("token", new AttributeValue(token));

            PutItemRequest putItemRequest = new PutItemRequest()
                    .withTableName("TokenDB")
                    .withItem(item);

            client.putItem(putItemRequest);
            logger.log("Token guardado: " + token);
        }
    }
}
