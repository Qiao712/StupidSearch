package qiao.segmenter;

import qiao712.segmenter.core.NumberSegmenter;
import qiao712.segmenter.core.Segmenter;
import qiao712.segmenter.dictionary.Word;

import java.util.List;

public class TestNumberSegmenter {
    public static void main(String[] args) {
        Segmenter segmenter = new NumberSegmenter();

        String sentence = "123xxx123一二三三二一";
        List<Word> words = segmenter.match(sentence);

        for (Word word : words) {
            System.out.println(word);
        }
    }
}
