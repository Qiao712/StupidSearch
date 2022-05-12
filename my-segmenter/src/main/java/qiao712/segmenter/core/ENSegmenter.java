package qiao712.segmenter.core;

import qiao712.segmenter.config.DictionaryConfig;
import qiao712.segmenter.dictionary.DefaultDictionary;
import qiao712.segmenter.dictionary.Dictionary;
import qiao712.segmenter.dictionary.Lexeme;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 英文分词
 */
public class ENSegmenter implements Segmenter {
    //省略词词典
    private final Dictionary stopwordDictionary = new DefaultDictionary();

    public ENSegmenter() throws IOException {
        File file = new File(DictionaryConfig.stopwordDictionary);
        stopwordDictionary.loadDictionary(new File[]{file});
    }

    @Override
    public List<Lexeme> match(String sentence) {
        List<Lexeme> lexemes = new ArrayList<>();

        boolean inWord = false;
        int wordBegin = 0;
        char[] charArray = sentence.toCharArray();

        for(int i = 0; i < charArray.length; i++){
            if(isLetter(charArray[i])){
                if(!inWord){
                    //单词开始
                    inWord = true;
                    wordBegin = i;
                }
            }else if(inWord && charArray[i] != '-' && charArray[i] != '\''){
                addLexeme(lexemes, charArray, wordBegin, i);
                //单词结束
                inWord = false;
            }
        }

        if(inWord){
            addLexeme(lexemes, charArray, wordBegin, charArray.length);
        }

        return lexemes;
    }

    private boolean isLetter(char character){
        return character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z';
    }

    private void addLexeme(List<Lexeme> lexemes, char[] charArray, int begin, int end){
        //添加单词
        Lexeme lexeme = new Lexeme();
        lexeme.setWord(String.valueOf(charArray, begin, end - begin));
        lexeme.setBegin(begin);

        //是否是省略词(stopword)
        if(stopwordDictionary.hasWord(lexeme.getWord())){
            lexeme.setWordType(Lexeme.WordType.STOPWORD);
        }else{
            lexeme.setWordType(Lexeme.WordType.ENGLISH);
        }

        lexemes.add(lexeme);
    }
}
