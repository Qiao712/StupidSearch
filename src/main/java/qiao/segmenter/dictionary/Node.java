package qiao.segmenter.dictionary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Node {
    private char character;
    private boolean isWord;
    private final Map<Character, Node> children = new ConcurrentHashMap<>();

    Node(){ }
    Node(char character){
        this.character = character;
    }



    /**
     * 将word[begin, )部分加，以word[begin]建立子节点
     * @param word
     * @param begin
     */
    void add(String word, int begin){
        char beginChar = word.charAt(begin);

        //获取子节点，若不存则加入
        //同步: 防止产生两个不同Node
        Node child;
        synchronized (this){
            child = children.get(beginChar);
            if(child == null){
                child = new Node(beginChar);
                children.put(beginChar, child);
            }
        }

        if(begin < word.length() - 1){
            child.add(word, begin + 1);
        }else{
            child.isWord = true;
        }
    }

    /**
     * 查找词
     */
    boolean find(String word){
        Node current = this;
        for(int i = 0; i < word.length(); i++){
            current = current.children.get(word.charAt(i));
            if(current == null){
                return false;
            }
        }
        return current.isWord;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    public Node nextNode(char character){
        return children.get(character);
    }
}
