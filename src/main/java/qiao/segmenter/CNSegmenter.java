package qiao.segmenter;

import qiao.segmenter.dictionary.Dictionary;
import qiao.segmenter.dictionary.Match;
import qiao.segmenter.dictionary.Word;
import qiao.segmenter.util.Range;

import java.util.*;

public class CNSegmenter implements Segmenter {
    private final Dictionary dictionary;

    public CNSegmenter(Dictionary dictionary){
        this.dictionary = dictionary;
    }

    @Override
    public List<Word> match(String sentence) {
        return matchAllWord(sentence);
    }

    private List<Word> matchAllWord(String sentence){
        List<Word> words = new ArrayList<>();
        char[] charArray = sentence.toCharArray();

        //匹配到的词覆盖范围的最后位置的 后驱
        int lastPos = 0;
        //未覆盖区域
        List<Range> notCover = new ArrayList<>();

        for(int i = 0; i < charArray.length; i++){
            //匹配sentence[i]起始的所有可能的词
            List<Match> matches = dictionary.multiMatch(charArray, i, charArray.length);

            //检查未覆盖区域
            if(! matches.isEmpty()){
                if(i > lastPos){
                    //记录未覆盖的区域
                    notCover.add(new Range(lastPos, i));
                }
            }

            for (Match match : matches) {
                lastPos = Math.max(lastPos, match.getEnd());

                Word word = new Word();
                word.setWord(String.valueOf(charArray, i, match.getEnd() - i));
                word.setWordType(Word.WordType.IN_DICTIONARY);
                word.setBegin(i);
                words.add(word);
            }
        }

        //处理未被匹配的区域
        for (Range range : notCover) {
            for(int i = range.getBegin(); i < range.getEnd(); i++){
                //尝试匹配前缀
                Match match = dictionary.matchOne(charArray, i, charArray.length, false);

                Word word = new Word();
                word.setBegin(i);
                if(match.getMatchType() == Match.MatchType.PREFIX){
                    word.setWord(String.valueOf(charArray, i, match.getEnd() - i));
                    word.setWordType(Word.WordType.PREFIX_IN_DICTIONARY);
                }else if(match.getMatchType() == Match.MatchType.NONE){
                    word.setWord(String.valueOf(charArray[i]));
                    word.setWordType(Word.WordType.UNKNOWN);
                }
                words.add(word);
            }
        }

        return words;
    }

    private List<Word> matchLongestWord(String sentence){
        List<Word> words = new ArrayList<>();
        char[] charArray = sentence.toCharArray();

        int begin = 0;
        while(begin < charArray.length){
            Match match = dictionary.matchOne(charArray, begin, charArray.length, true);

            if(match.getMatchType() == Match.MatchType.ALL || match.getMatchType() == Match.MatchType.PREFIX){
                Word word = new Word();
                word.setWord(String.valueOf(charArray, begin, match.getEnd() - begin));
                word.setWordType(match.getMatchType() == Match.MatchType.ALL ? Word.WordType.IN_DICTIONARY : Word.WordType.PREFIX_IN_DICTIONARY);
                word.setBegin(begin);
                words.add(word);

                begin = match.getEnd();
            }else{
                //未匹配的单字
                Word word = new Word();
                word.setWord(String.valueOf(charArray, begin, 1));
                word.setWordType(Word.WordType.UNKNOWN);
                word.setBegin(begin);
                words.add(word);

                begin++;
            }
        }

        return words;
    }
}
