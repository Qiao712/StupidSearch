package qiao712.segmenter.core;

import qiao712.segmenter.dictionary.Lexeme;

import java.util.ArrayList;
import java.util.List;

/**
 * 数字分词
 */
public class NumberSegmenter implements Segmenter {
    @Override
    public List<Lexeme> match(String sentence) {
        List<Lexeme> lexemes = new ArrayList<>();
        char[] charArray = sentence.toCharArray();

        boolean inNumber = false;
        int numberBegin = 0;

        for(int i = 0; i < charArray.length; i++){
            if(Character.isDigit(charArray[i])){
                if(!inNumber){
                    //单词开始
                    inNumber = true;
                    numberBegin = i;
                }
            }else if(inNumber && charArray[i] != ','){
                addLexeme(lexemes, charArray, numberBegin, i);
                //单词结束
                inNumber = false;
            }
        }

        if(inNumber){
            addLexeme(lexemes, charArray, numberBegin, charArray.length);
        }

        return lexemes;
    }

    private void addLexeme(List<Lexeme> lexemes, char[] charArray, int begin, int end){
        Lexeme lexeme = new Lexeme();
        lexeme.setWord(String.valueOf(charArray, begin, end - begin));
        lexeme.setBegin(begin);
        lexeme.setWordType(Lexeme.WordType.NUMBER);
        lexemes.add(lexeme);
    }
}
