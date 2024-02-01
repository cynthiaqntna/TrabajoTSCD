import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GetObjectRequest;
import database.DynamoDBClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

public class TokenizerLambdaHandler implements RequestHandler<S3Event, LambdaResponse> {
    private final DynamoDBClient dynamoDBClient = new DynamoDBClient();
    private final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();

    @Override
    public LambdaResponse handleRequest(S3Event s3Event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Inicio de la ejecución de la función Lambda");

        String bucketName = s3Event.getRecords().get(0).getS3().getBucket().getName();
        String objectKey = s3Event.getRecords().get(0).getS3().getObject().getKey();
        logger.log("Descargando código fuente de S3: bucket=" + bucketName + ", key=" + objectKey);

        String sourceCode = downloadSourceCodeFromS3(bucketName, objectKey, logger);

        File file = new File(objectKey);
        TokenizerFactory tokenizerFactory = new TokenizerFactory();
        Tokenizer tokenizer = tokenizerFactory.getTokenizer(file);
        logger.log("Procesando archivo con tokenizer: " + file.getName());

        List<String> tokens = tokenizer.tokenize(sourceCode);
        logger.log("Número de tokens generados: " + tokens.size());

        dynamoDBClient.saveTokensToDynamoDB(tokens, logger);

        logger.log("Tokens guardados en DynamoDB exitosamente");

        return new LambdaResponse(tokens);
    }

    private String downloadSourceCodeFromS3(String bucketName, String objectKey, LambdaLogger logger) {
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, objectKey));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()))) {
            StringBuilder sourceCode = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sourceCode.append(line).append("\n");
            }
            return sourceCode.toString();
        } catch (Exception e) {
            logger.log("Error al descargar el código fuente desde S3: " + e.getMessage());
            throw new RuntimeException("Error al descargar el código fuente desde S3: " + e.getMessage(), e);
        }
    }
}
