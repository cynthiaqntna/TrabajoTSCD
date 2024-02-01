import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PythonTokenizerTest {

    @Test
    public void testTokenize() {
        PythonTokenizer pythonTokenizer = new PythonTokenizer();

        String sourceCode = "variable = 42";

        List<String> expectedTokens = List.of("variable", "=", "42");

        List<String> actualTokens = pythonTokenizer.tokenize(sourceCode);

        assertEquals(expectedTokens, actualTokens);
    }
}
