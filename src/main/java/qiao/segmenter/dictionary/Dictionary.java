package qiao.segmenter.dictionary;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Dictionary {
    /**
     * 查询一个词是否在词典中
     */
    boolean hasWord(String word);

    Match matchOne(char[] sentence, int begin, int end, boolean greedy);

    Match matchOne(char[] sentence, int begin, int end, boolean greedy, Node node);

    List<Match> multiMatch(char[] sentence, int begin, int end);

    /**
     * 添加词
     */
    void addWord(String word);

    /**
     * 删除词
     */
    void deleteWord(String word);

    /**
     * 加载词典
     */
    void loadDictionary(File[] files) throws IOException;
}
