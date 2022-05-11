package qiao.segmenter.dictionary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultDictionary implements Dictionary{
    private final Node root = new Node();

    @Override
    public boolean hasWord(String word) {
        return root.find(word);
    }

    @Override
    public Match matchOne(char[] sentence, int begin, int end, boolean greedy){
        return matchOne(sentence, begin, end, greedy, root);
    }

    @Override
    public Match matchOne(char[] sentence, int begin, int end, boolean greedy, Node node){
        assert node != null;

        Match match = null;
        Node lastNode = node;
        int i;
        for(i = begin; i < end; i++){
            lastNode = node;
            node = node.nextNode(sentence[i]);

            if(node == null){
                break;
            }else if(node.isWord()){
                //完全匹配
                match = new Match(begin, i+1, node, Match.MatchType.ALL);
                if(! greedy){
                    break;
                }
            }
        }

        //匹配某个词的前缀
        if(match == null && begin < i){
            //node == null 区分 词典结束 还是 句子的长度不足
            match = new Match(begin, i, node == null ? lastNode : node, Match.MatchType.PREFIX);
        }

        return match != null ? match : new Match(begin, begin, node, Match.MatchType.NONE);
    }

    @Override
    public List<Match> multiMatch(char[] sentence, int begin, int end) {
        List<Match> matches = new ArrayList<>();
        //匹配sentence[begin]起始的所有可能的词
        Match match = matchOne(sentence, begin, end, false);

        while(match.getMatchType() == Match.MatchType.ALL){
            match.setBegin(begin);
            matches.add(match);

            //从上个词结尾处继续匹配
            match = matchOne(sentence, match.getEnd(), end, false, match.getNode());
        }

        return matches;
    }

    @Override
    public void addWord(String word) {
        root.add(word, 0);
    }

    @Override
    public void deleteWord(String word) {

    }

    @Override
    public void loadDictionary(File[] files) throws IOException {
        for (File file : files) {
            try(Reader fileReader = new FileReader(file)){
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                while((line = reader.readLine()) != null){
                    line = line.trim();
                    if(line.isEmpty()){
                        continue;
                    }

                    addWord(line);
                }
            }
        }
    }
}
