package qiao712.segmenter.core;

import qiao712.segmenter.dictionary.Word;

import java.util.List;

public interface Segmenter {
    List<Word> match(String sentence);
}
