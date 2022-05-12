package qiao712.segmenter.core;

import qiao712.segmenter.util.Range;
import qiao712.segmenter.dictionary.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 总分词器
 */
public class TopSegmenter implements Segmenter {
    private Segmenter[] segmenters;

    public TopSegmenter() throws IOException {
        initSegmenter();
    }

    /**
     * 初始化子分词器
     * @throws IOException
     */
    private void initSegmenter() throws IOException {
        segmenters = new Segmenter[]{
            new CNSegmenter(), new ENSegmenter(), new NumberSegmenter()
        };
    }

    @Override
    public List<Word> match(String sentence) {
        List<Word> words = new ArrayList<>();

        for (Segmenter segmenter : segmenters) {
            List<Word> partWords = segmenter.match(sentence);
            words.addAll(partWords);
        }

        //未覆盖区域
        List<Range> notCover = new ArrayList<>();

        Comparator<Word> positionCmp = new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return Integer.compare(o1.getBegin(), o2.getBegin());
            }
        };
        words.sort(positionCmp);

        //匹配到的词覆盖范围的最后位置的 后驱
        int lastPos = 0;
        for (Word word : words) {
            if(word.getBegin() > lastPos){
                //找到了未覆盖的区域
                notCover.add(new Range(lastPos, word.getBegin()));
            }

            lastPos = Math.max(lastPos, word.getEnd());
        }

        //处理未被匹配的区域
        for (Range range : notCover) {
            System.out.println(range.getBegin() + "," + range.getEnd());
            for(int i = range.getBegin(); i < range.getEnd(); i++){
                Word word = new Word();
                word.setBegin(i);
                word.setWord(String.valueOf(sentence.charAt(i)));
                word.setWordType(Word.WordType.UNKNOWN);
                words.add(word);
            }
        }

        return words;
    }
}
