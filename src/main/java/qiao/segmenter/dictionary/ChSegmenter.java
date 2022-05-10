package qiao.segmenter.dictionary;

import qiao.segmenter.Segmenter;

import java.util.ArrayList;
import java.util.List;

public class ChSegmenter implements Segmenter {
    private final Dictionary dictionary;

    public ChSegmenter(Dictionary dictionary){
        this.dictionary = dictionary;
    }

    /**
     * 分词模式
     */
    public enum SegmentMode{
        MAX_NUM,            //细分，所有出现的词
        MAX_WORD_LENGTH     //是词尽量的长
    }

    @Override
    public List<Word> match(String sentence, SegmentMode mode) {
        switch (mode){
            case MAX_NUM: return matchAllWord(sentence);
            case MAX_WORD_LENGTH: return matchLongestWord(sentence);
        }

        throw new UnsupportedOperationException();
    }

    private List<Word> matchAllWord(String sentence){
        List<Word> words = new ArrayList<>();
        char[] charArray = sentence.toCharArray();

        for(int i = 0; i < charArray.length; i++){
            //匹配sentence[i]起始的所有可能的词
            List<Match> matches = dictionary.multiMatch(charArray, i, charArray.length);

            for (Match match : matches) {
                Word word = new Word();
                word.setWord(String.valueOf(charArray, i, match.getEnd() - i));
                word.setWordType(Word.WordType.IN_DICTIONARY);
                word.setBegin(i);
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
