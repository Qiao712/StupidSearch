package qiao712.segmenter;

import qiao712.segmenter.dictionary.Lexeme;
import qiao712.segmenter.core.Segmenter;
import qiao712.segmenter.core.TopSegmenter;

import java.io.IOException;
import java.util.List;

public class SegmentService {
    private final Segmenter segmenter;

    public SegmentService() throws IOException {
        segmenter = new TopSegmenter();
    }

    public List<Lexeme> segment(String sentence){
        return segmenter.match(sentence);
    }
}
