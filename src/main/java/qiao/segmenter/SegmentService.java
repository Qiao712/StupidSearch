package qiao.segmenter;

import qiao.segmenter.config.DictionaryConfig;
import qiao.segmenter.dictionary.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SegmentService {
    private Segmenter segmenter;
    private Dictionary dictionary;

    public SegmentService() throws IOException {
        dictionary = new CNDictionary();
        segmenter = new CNSegmenter(dictionary);
        loadDictionary();
    }

    /**
     * 载入词典目录下的所有词典文件
     */
    private void loadDictionary() throws IOException {
        File direct = new File(DictionaryConfig.dictionaryDirect);
        File[] files = direct.listFiles();
        dictionary.loadDictionary(files);
    }

    public List<Word> segment(String sentence){
        return segmenter.match(sentence, CNSegmenter.SegmentMode.MAX_NUM);
    }
}
