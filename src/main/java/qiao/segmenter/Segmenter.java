package qiao.segmenter;

import qiao.segmenter.dictionary.ChSegmenter;
import qiao.segmenter.dictionary.Word;

import java.util.List;

public interface Segmenter {
    List<Word> match(String sentence, ChSegmenter.SegmentMode mode);
}
