import java.io.BufferedReader;
import java.io.IOException;

public class FileAnalyzer {
    public Metrics analyze(BufferedReader reader) throws IOException {
        Metrics metrics = new Metrics();
        String line;

        while ((line = reader.readLine()) != null) {
            metrics.incrementTotalLines();
            if (line.trim().isEmpty()) {
                metrics.incrementBlankLines();
            } else if (line.trim().startsWith("//") || line.trim().startsWith("/*") || line.trim().startsWith("*/")) {
                metrics.incrementCommentLines();
            } else {
                metrics.incrementCodeLines();
            }
        }

        return metrics;
    }
}