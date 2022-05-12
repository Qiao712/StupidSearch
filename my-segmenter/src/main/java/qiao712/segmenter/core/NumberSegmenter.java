package qiao712.segmenter.core;

import qiao712.segmenter.dictionary.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * 数字分词
 */
public class NumberSegmenter implements Segmenter {
    @Override
    public List<Word> match(String sentence) {
        List<Word> words = new ArrayList<>();
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
                //添加单词
                Word word = new Word();
                word.setWord(String.valueOf(charArray, numberBegin, i - numberBegin));
                word.setBegin(numberBegin);
                word.setWordType(Word.WordType.NUMBER);
                words.add(word);

                //单词结束
                inNumber = false;
            }
        }

        return words;
    }
}
