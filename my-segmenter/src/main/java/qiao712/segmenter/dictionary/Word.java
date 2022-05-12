package qiao712.segmenter.dictionary;

/**
 *
 */
public class Word {
    private String word;

    //在字符串中的位置
    private int begin;

    public enum WordType{
        IN_DICTIONARY,          //在字典文件中的词
        PREFIX_IN_DICTIONARY,   //字典中的词的前缀
        NUMBER,                 //数字
        ENGLISH,                //英文单词
        STOPWORD,               //省略词
        UNKNOWN,                //未知
    }

    private WordType wordType;

    public Word() {
    }

    public Word(String word, int begin, WordType wordType) {
        this.word = word;
        this.begin = begin;
        this.wordType = wordType;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public WordType getWordType() {
        return wordType;
    }

    public void setWordType(WordType wordType) {
        this.wordType = wordType;
    }

    public int getLength(){
        return word.length();
    }

    public int getEnd(){
        return begin + word.length();
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", begin=" + begin +
                ", wordType=" + wordType +
                '}';
    }
}
