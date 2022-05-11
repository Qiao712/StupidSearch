package qiao.segmenter;

import qiao.segmenter.config.DictionaryConfig;
import qiao.segmenter.dictionary.DefaultDictionary;
import qiao.segmenter.dictionary.Dictionary;
import qiao.segmenter.dictionary.Match;
import qiao.segmenter.dictionary.Word;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 英文分词
 */
public class ENSegmenter implements Segmenter{
    //省略词词典
    private final Dictionary stopwordDictionary = new DefaultDictionary();

    ENSegmenter() throws IOException {
        File file = new File(DictionaryConfig.stopwordDictionary);
        stopwordDictionary.loadDictionary(new File[]{file});
    }

    @Override
    public List<Word> match(String sentence) {
        List<Word> words = new ArrayList<>();

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
                //添加单词
                Word word = new Word();
                word.setWord(String.valueOf(charArray, wordBegin, i - wordBegin));
                word.setBegin(wordBegin);

                //是否是省略词(stopword)
                if(stopwordDictionary.hasWord(word.getWord())){
                    word.setWordType(Word.WordType.STOPWORD);
                }else{
                    word.setWordType(Word.WordType.ENGLISH);
                }

                words.add(word);

                //单词结束
                inWord = false;
            }
        }

        return words;
    }

    private boolean isLetter(char character){
        return character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z';
    }
}
