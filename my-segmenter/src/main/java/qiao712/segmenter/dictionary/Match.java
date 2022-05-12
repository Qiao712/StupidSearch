package qiao712.segmenter.dictionary;

public class Match {
    //词在句中位置
    private int begin;
    private int end;

    //结尾字所在节点
    private Node node;

    public enum MatchType{
        ALL,
        PREFIX,
        NONE
    }

    private MatchType matchType;

    public Match(int begin, int end, Node node, MatchType matchType) {
        this.begin = begin;
        this.end = end;
        this.node = node;
        this.matchType = matchType;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public int getLength(){
        return end - begin;
    }

    @Override
    public String toString() {
        return "Match{" +
                "begin=" + begin +
                ", end=" + end +
                ", node=" + (node != null ? node.getCharacter() : "null") +
                ", matchType=" + matchType +
                '}';
    }
}
