package qiao.segmenter;

import qiao.segmenter.dictionary.Word;

import java.io.IOException;
import java.util.List;

public class TestENSegmenter {
    public static void main(String[] args) throws IOException {
        String sentence = "asdf嗨嗨嗨123hello world, what's is a the fuck aaa-bbb-ccc-ddd/*/123**/*asdf/*123/adf*a/df";
        Segmenter segmenter = new ENSegmenter();
        List<Word> words = segmenter.match(sentence);

        for (Word word : words) {
            System.out.println(word);
        }
    }
}
