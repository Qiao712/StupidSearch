package qiao712.segmenter.core;

import qiao712.segmenter.util.Range;
import qiao712.segmenter.dictionary.Lexeme;

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
    public List<Lexeme> match(String sentence) {
        List<Lexeme> lexemes = new ArrayList<>();

        for (Segmenter segmenter : segmenters) {
            List<Lexeme> someLexemes = segmenter.match(sentence);
            lexemes.addAll(someLexemes);
        }

        //未覆盖区域
        List<Range> notCover = new ArrayList<>();

        Comparator<Lexeme> positionCmp = new Comparator<Lexeme>() {
            @Override
            public int compare(Lexeme o1, Lexeme o2) {
                return Integer.compare(o1.getBegin(), o2.getBegin());
            }
        };
        lexemes.sort(positionCmp);

        //匹配到的词覆盖范围的最后位置的 后驱
        int lastPos = 0;
        for (Lexeme lexeme : lexemes) {
            if(lexeme.getBegin() > lastPos){
                //找到了未覆盖的区域
                notCover.add(new Range(lastPos, lexeme.getBegin()));
            }

            lastPos = Math.max(lastPos, lexeme.getEnd());
        }

        //处理未被匹配的区域
        for (Range range : notCover) {
            System.out.println(range.getBegin() + "," + range.getEnd());
            for(int i = range.getBegin(); i < range.getEnd(); i++){
                Lexeme lexeme = new Lexeme();
                lexeme.setBegin(i);
                lexeme.setWord(String.valueOf(sentence.charAt(i)));
                lexeme.setWordType(Lexeme.WordType.UNKNOWN);
                lexemes.add(lexeme);
            }
        }

        return lexemes;
    }
}
