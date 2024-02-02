import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class TokenizerFactoryTest {

    @Test
    public void testTokenizerFactoryForJavaFile() {
        TokenizerFactory tokenizerFactory = new TokenizerFactory();
        File javaFile = new File("C:\\Users\\andre\\Desktop\\Andre Uni\\Cuarto\\TSCD\\Ficheros\\Agenda.java");
        Tokenizer javaTokenizer = tokenizerFactory.getTokenizer(javaFile);
        assertTrue(javaTokenizer instanceof JavaTokenizer);
    }

    @Test
    public void testTokenizerFactoryForPythonFile() {
        TokenizerFactory tokenizerFactory = new TokenizerFactory();
        File pythonFile = new File("C:\\Users\\andre\\Desktop\\Andre Uni\\Cuarto\\TSCD\\Ficheros\\Alumno.py");
        Tokenizer pythonTokenizer = tokenizerFactory.getTokenizer(pythonFile);
        assertTrue(pythonTokenizer instanceof PythonTokenizer);
    }

    @Test
    public void testTokenizerFactoryForUnknownFile() {
        TokenizerFactory tokenizerFactory = new TokenizerFactory();
        File unknownFile = new File("C:\\Users\\andre\\Desktop\\Andre Uni\\Cuarto\\TSCD\\Ficheros\\example.txt");
        Tokenizer nullTokenizer = tokenizerFactory.getTokenizer(unknownFile);
        assertTrue(nullTokenizer instanceof NullTokenizer);
    }
}
