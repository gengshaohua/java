package linkedList;

import java.util.Random;

public class SkipList {

    public class Node{
        public int data = -1;
        public Node[] forward;
        public Node(int level){
            forward = new Node[level];
        }
    }

    Random r = new Random();
    final int MAX_LENGTH = 16;    //最高层索引数
    int levelCount = 1;    //当前最高索引层
    Node head = new Node(MAX_LENGTH);

    public int randomLevel(){
        int level = 1;
        for(int i = 0; i < MAX_LENGTH; i++){
            if(r.nextInt() % 2 == 1)
                level++;
        }
        return level;
    }

    public void insert(int value){
        int level = head.forward[0] == null ? 1 : randomLevel();
        if(level > levelCount){
            level = ++levelCount;
        }
        Node newNode = new Node(level);
        newNode.data = value;
        Node p = head;
        for(int i = levelCount - 1; i >=0 ; --i){
            while(p.forward[i] != null && p.forward[i].data < value){
                p = p.forward[i];
            }
            if(level > i){
                if(p.forward[i] == null){
                    p.forward[i] = newNode;
                }else{
                    Node next = p.forward[i];
                    p.forward[i] = newNode;
                    newNode.forward[i] = next;
                }
            }
        }
    }

    public Node find(int value){
        Node p = head;
        for(int i = levelCount - 1; i >= 0; --i){
            while(p.forward[i] != null && p.forward[i].data < value){
                p = p.forward[i];
            }
        }
        if(p.forward[0] != null && p.forward[0].data == value)
            return p.forward[0];
        else
            return null;
    }

    public void delete(int value){
        Node[] update = new Node[levelCount];
        Node p = head;
        for(int i = levelCount - 1; i >= 0; --i){
            while(p.forward[i] != null && p.forward[i].data < value)
                p = p.forward[i];
            update[i] = p;
        }
        if(p.forward[0] != null && p.forward[0].data == value){
            for(int i = levelCount - 1; i >= 0; --i){
                if(update[i].forward[i]!= null && update[i].forward[i].data == value)
                    update[i].forward[i] = update[i].forward[i].forward[i];
            }
        }
    }
}
