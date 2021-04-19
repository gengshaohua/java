package search;

public class BinarySearchTree {

    private Node tree;

    public Node find(int data){
        Node p = tree;
        while(p != null){
            if(data < p.value){
                p = p.left;
            }else if(data > p.value){
                p = p.right;
            }else{
                return p;
            }
        }
        return null;
    }

    public void insert(int data){
        if(tree == null) {
            tree = new Node(data);
            return;
        }
        Node p = tree;
        while(p != null){
            if(data > p.value){
                if(p.right == null) {
                    p.right = new Node(data);
                    return;
                }
                p = p.right;
            }else{
                if(p.left == null) {
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            }
        }
    }

    public void delete(int data){
        Node p = tree;
        Node pp = null;
        while(p != null && p.value != data){
            pp = p;
            if(data > p.value){
                p = p.right;
            }else if(data < p.value){
                p = p.left;
            }
        }
        if(p == null) return;
        if(p.left != null && p.right != null){
            Node minP = p.right;
            Node minPP = p;
            while(minP.left != null){
                minPP = minP;
                minP = minP.left;
            }
            p.value = minP.value;
            p = minP;
            pp = minPP;
        }

        //删除的节点下只有一个节点
        Node child;
        if(p.left != null) child = p.left;
        else if(p.right != null) child = p.right;
        else child = null;

        if(pp == null) tree = child;
        else if(pp.left == p) pp.left = child;
        else pp.right = child;

    }
    public static class Node{
        public int value;

        public Node left;
        public Node right;

        public Node(int value){
            this.value = value;
        }
    }
}
