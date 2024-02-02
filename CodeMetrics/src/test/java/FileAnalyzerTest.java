import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileAnalyzerTest {

    @Test
    public void testFileAnalyzer() throws IOException {
        String content = "line 1\n// Comment\n\nline 4";
        BufferedReader reader = new BufferedReader(new StringReader(content));

        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        Metrics metrics = fileAnalyzer.analyze(reader);

        assertEquals(4, metrics.getTotalLines());
        assertEquals(2, metrics.getCodeLines());
        assertEquals(1, metrics.getCommentLines());
        assertEquals(1, metrics.getBlankLines());
    }
}
