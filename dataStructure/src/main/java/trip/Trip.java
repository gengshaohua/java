package trip;

public class Trip {

    TrieNode trieNode = new TrieNode('/');

    //插入一个字符串
    public void insert(char[] text){
        TrieNode p = trieNode;
        for(int i = 0; i<text.length; i++){
            int index = text[i] - 'a';
            if(p.children[index] == null){
                TrieNode newNode = new TrieNode(text[i]);
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
    }

    public int find(char[] text){
        TrieNode p = trieNode;
        for(int i = 0; i<text.length; i++){
            int index = text[i] - 'a';
            if(p.children[index] == null) return -1;
            p = p.children[index];
        }
        if(!p.isEndingChar) return -1;
        return 1;
    }

    public class TrieNode {
        public char data;
        public TrieNode[] children = new TrieNode[26];
        public boolean isEndingChar = false;
        public TrieNode(char data) {
            this.data = data;
        }
    }

}
