import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaTokenizer implements Tokenizer {
    @Override
    public List<String> tokenize(String sourceCode) {
        return Arrays.stream(sourceCode.split("\\s+|(;|\\{|\\})"))
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
    }
}
