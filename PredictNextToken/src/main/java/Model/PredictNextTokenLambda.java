package Model;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class PredictNextTokenLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        NGramDataHandler dataHandler = new NGramDataHandler(3);
        BasicNGramModel model = new BasicNGramModel(dataHandler);

        String contextToken = request.getBody();
        var nextTokens = model.suggestNextTokens(contextToken);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setBody(nextTokens.toString());
        return response;
    }
}
