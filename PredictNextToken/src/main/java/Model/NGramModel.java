package Model;

import java.util.List;

public interface NGramModel {
    List<String> suggestNextTokens(String context);
}
