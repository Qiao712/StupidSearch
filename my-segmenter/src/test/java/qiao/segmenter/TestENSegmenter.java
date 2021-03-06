package qiao.segmenter;

import qiao712.segmenter.core.ENSegmenter;
import qiao712.segmenter.core.Segmenter;
import qiao712.segmenter.dictionary.Lexeme;

import java.io.IOException;
import java.util.List;

public class TestENSegmenter {
    public static void main(String[] args) throws IOException {
        String sentence = "asdf嗨嗨嗨123hello world, what's is a the fuck aaa-bbb-ccc-ddd/*/123**/*asdf/*123/adf*a/df";
        Segmenter segmenter = new ENSegmenter();
        List<Lexeme> words = segmenter.match(sentence);

        for (Lexeme word : words) {
            System.out.println(word);
        }
    }
}
