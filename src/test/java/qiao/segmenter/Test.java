package qiao.segmenter;


import qiao.segmenter.dictionary.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        Dictionary dictionary = new CNDictionary();
        dictionary.loadDictionary(new File[]{new File("dictionary\\main.dic"), new File("dictionary\\preposition.dic"), new File("dictionary\\test.dic")});
        dictionary.addWord("嗨害害");
        dictionary.addWord("秘制小汉堡");
        dictionary.addWord("What");

        System.out.println(dictionary.hasWord("嗨嗨害"));
        System.out.println(dictionary.hasWord("嗨害害"));
        System.out.println(dictionary.hasWord("秘制老汉堡"));
        System.out.println(dictionary.hasWord("秘制小汉堡"));
        System.out.println(dictionary.hasWord("编程"));
        System.out.println(dictionary.hasWord("处理器"));
        System.out.println(dictionary.hasWord("尼玛这词也有"));
        System.out.println(dictionary.hasWord("你🐎死了"));

//        String sentence = "全体目光向我看起，我是个傻逼。你狗屁才艺，谁不会啊";
//        String sentence = "在这个xxxfff反智横行的年代，只有金轮的直播给了我感性和理性的认知。[傲娇]\n" +
//                "他的直播展现出的，是一个人纯粹所具有的良好品质，说不上什么几分特别突兀的地方，整场直播十分的融洽。\n" +
//                "他直播发病的机智是精妙的，真诚和正直的朴素给人一种无法接触的高贵感，就好像永远抓不住的闪亮的星星，一个彬彬有礼的人用完美的操作给观众带来最为精致的美的感受，我不禁怀疑上帝的天意是否对我太过宠溺，让我人生中认识到如此不朽和无价的灵魂，充满着可爱与美好。\n" +
//                "我不得不再次审视男童们，原来他们早已追随如此伟大的事物，想必他们的品行、修养也一定很好。\n" +
//                "呐呐呐，你看我现在，再超神给你看啊[傲娇]";
//        List<Word> words = dictionary.greedyMatch(sentence.toCharArray(), 0, sentence.toCharArray().length);
//        for (Word word : words) {
//            System.out.println(word);
//        }

        String sentence = "艾伦耶格尔君,三笠，阿克曼 埃尔文";
        char[] a = sentence.toCharArray();
        Match match = dictionary.matchOne(a, 0, a.length, false);
        System.out.println(match);

        Segmenter segment = new CNSegmenter(dictionary);
        System.out.println("尽量细分:");
        List<Word> words = segment.match(sentence, CNSegmenter.SegmentMode.MAX_NUM);
        for (Word word : words) {
            System.out.println(word);
        }

        System.out.println("尽量粗分:");
        words = segment.match(sentence, CNSegmenter.SegmentMode.MAX_WORD_LENGTH);
        for (Word word : words) {
            System.out.println(word);
        }
    }
}
