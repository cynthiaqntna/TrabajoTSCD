import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.mockito.Mockito.*;

public class CodeMetricsLambdaHandlerTest {

    private CodeMetricsLambdaHandler lambdaHandler;
    private AmazonS3 s3Client;
    private AmazonDynamoDB dynamoDBClient;

    @Before
    public void setUp() {
        s3Client = mock(AmazonS3.class);
        dynamoDBClient = mock(AmazonDynamoDB.class);
        lambdaHandler = new CodeMetricsLambdaHandler(s3Client, dynamoDBClient);
    }

    @Test
    public void handleRequest_ValidS3Event_CallsAnalyzeAndSaveMetricsToDynamoDB() throws IOException {
        S3Event s3Event = createValidS3Event();
        Context context = Mockito.mock(Context.class);

        // Simulamos el comportamiento de S3
        S3Object s3Object = mock(S3Object.class);
        when(s3Client.getObject(anyString(), anyString())).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(new TestS3ObjectInputStream());

        // Simulamos el comportamiento de DynamoDB
        doNothing().when(dynamoDBClient).putItem(any());

        // Llamamos al método handleRequest
        lambdaHandler.handleRequest(s3Event, context);

        // Verificamos que analyze y saveMetricsToDynamoDB fueron llamados
        verify(s3Object, times(1)).getObjectContent();
        verify(dynamoDBClient, times(1)).putItem(any());
    }

    private S3Event createValidS3Event() {
        // Puedes personalizar este método según tus necesidades de prueba.
        // Aquí se crea un evento S3 de ejemplo.
        // Nota: Esta implementación es básica y puede necesitar ajustes según la estructura de tus eventos S3.

        S3Event.S3Entity s3Entity = new S3Event.S3Entity();
        s3Entity.setBucket(new S3Event.S3BucketEntity());
        s3Entity.setObject(new S3Event.S3ObjectEntity());

        S3Event.S3BucketEntity s3BucketEntity = s3Entity.getBucket();
        s3BucketEntity.setName("test-bucket");

        S3Event.S3ObjectEntity s3ObjectEntity = s3Entity.getObject();
        s3ObjectEntity.setKey("test-key");

        S3Event s3Event = new S3Event();
        s3Event.setRecords(List.of(new S3Event.S3EventNotificationRecord()));
        s3Event.getRecords().get(0).setS3(s3Entity);

        return s3Event;
    }

    private static class TestS3ObjectInputStream extends StringReader {
        TestS3ObjectInputStream() {
            super("Example content for testing.");
        }
    }
}
