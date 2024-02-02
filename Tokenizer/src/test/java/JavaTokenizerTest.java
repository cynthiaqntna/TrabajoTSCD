import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JavaTokenizerTest {

    @Test
    public void testJavaTokenizer() {
        Tokenizer tokenizer = new JavaTokenizer();
        List<String> tokens = tokenizer.tokenize("public class Example { }");
        assertEquals(Arrays.asList("public", "class", "Example", "{", "}"), tokens);
    }

    @Test
    public void testJavaTokenizerWithPunctuation() {
        Tokenizer tokenizer = new JavaTokenizer();
        List<String> tokens = tokenizer.tokenize("int x = 5;");
        assertEquals(Arrays.asList("int", "x", "=", "5", ";"), tokens);
    }

    @Test
    public void testJavaTokenizerWithMultipleSpaces() {
        Tokenizer tokenizer = new JavaTokenizer();
        List<String> tokens = tokenizer.tokenize("  if  (x  >  5)  {  }  ");
        assertEquals(Arrays.asList("if", "(", "x", ">", "5", ")", "{", "}"), tokens);
    }
}
