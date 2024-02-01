package Model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicNGramModel implements NGramModel {
    private NGramDataHandler dataHandler;

    public BasicNGramModel(NGramDataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    @Override
    public List<String> suggestNextTokens(String context) {
        Map<String, Map<String, Integer>> nGrams = dataHandler.getNGrams();
        Map<String, Integer> possibleNextTokens = nGrams.getOrDefault(context, Collections.emptyMap());
        return possibleNextTokens.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

