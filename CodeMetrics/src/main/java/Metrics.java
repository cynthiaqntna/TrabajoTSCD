public class Metrics {
    private int totalLines;
    private int codeLines;
    private int commentLines;
    private int blankLines;

    public Metrics() {
        this.totalLines = 0;
        this.codeLines = 0;
        this.commentLines = 0;
        this.blankLines = 0;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getCodeLines() {
        return codeLines;
    }

    public void setCodeLines(int codeLines) {
        this.codeLines = codeLines;
    }

    public int getCommentLines() {
        return commentLines;
    }

    public void setCommentLines(int commentLines) {
        this.commentLines = commentLines;
    }

    public int getBlankLines() {
        return blankLines;
    }

    public void setBlankLines(int blankLines) {
        this.blankLines = blankLines;
    }

    public void incrementTotalLines() {
        this.totalLines++;
    }

    public void incrementCodeLines() {
        this.codeLines++;
    }

    public void incrementCommentLines() {
        this.commentLines++;
    }

    public void incrementBlankLines() {
        this.blankLines++;
    }
}