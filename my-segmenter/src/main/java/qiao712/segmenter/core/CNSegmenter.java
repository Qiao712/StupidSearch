package qiao712.segmenter.core;

import qiao712.segmenter.config.DictionaryConfig;
import qiao712.segmenter.dictionary.DefaultDictionary;
import qiao712.segmenter.dictionary.Dictionary;
import qiao712.segmenter.dictionary.Match;
import qiao712.segmenter.dictionary.Lexeme;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CNSegmenter implements Segmenter {
    private final Dictionary dictionary = new DefaultDictionary();

    public CNSegmenter() throws IOException {
        File mainDictFile = new File(DictionaryConfig.stopwordDictionary);
        File prepositionDictFile = new File(DictionaryConfig.mainDictionary);
        dictionary.loadDictionary(new File[]{mainDictFile, prepositionDictFile});
    }

    @Override
    public List<Lexeme> match(String sentence) {
        return matchAllWord(sentence);
    }

    private List<Lexeme> matchAllWord(String sentence){
        List<Lexeme> lexemes = new ArrayList<>();
        char[] charArray = sentence.toCharArray();

        for(int i = 0; i < charArray.length; i++){
            //匹配sentence[i]起始的所有可能的词
            List<Match> matches = dictionary.multiMatch(charArray, i, charArray.length);

            for (Match match : matches) {
                Lexeme lexeme = new Lexeme();
                lexeme.setWord(String.valueOf(charArray, i, match.getEnd() - i));
                lexeme.setWordType(Lexeme.WordType.IN_DICTIONARY);
                lexeme.setBegin(i);
                lexemes.add(lexeme);
            }
        }

        return lexemes;
    }

    private List<Lexeme> matchLongestWord(String sentence){
        List<Lexeme> lexemes = new ArrayList<>();
        char[] charArray = sentence.toCharArray();

        int begin = 0;
        while(begin < charArray.length){
            Match match = dictionary.matchOne(charArray, begin, charArray.length, true);

            if(match.getMatchType() == Match.MatchType.ALL || match.getMatchType() == Match.MatchType.PREFIX){
                Lexeme lexeme = new Lexeme();
                lexeme.setWord(String.valueOf(charArray, begin, match.getEnd() - begin));
                lexeme.setWordType(match.getMatchType() == Match.MatchType.ALL ? Lexeme.WordType.IN_DICTIONARY : Lexeme.WordType.PREFIX_IN_DICTIONARY);
                lexeme.setBegin(begin);
                lexemes.add(lexeme);

                begin = match.getEnd();
            }else{
                //未匹配的单字
                Lexeme lexeme = new Lexeme();
                lexeme.setWord(String.valueOf(charArray, begin, 1));
                lexeme.setWordType(Lexeme.WordType.UNKNOWN);
                lexeme.setBegin(begin);
                lexemes.add(lexeme);

                begin++;
            }
        }

        return lexemes;
    }
}
