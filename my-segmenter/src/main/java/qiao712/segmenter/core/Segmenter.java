package qiao712.segmenter.core;

import qiao712.segmenter.dictionary.Lexeme;

import java.util.List;

public interface Segmenter {
    List<Lexeme> match(String sentence);
}
