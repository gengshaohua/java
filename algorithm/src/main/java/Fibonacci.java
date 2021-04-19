public class Fibonacci {

    public static int fibSeq(int n){
        if(n<0){
            throw new IllegalArgumentException("the param is less than 0");
        }
        if(n==0)
            return 0;

        if(n==1)
            return 1;
        return fibSeq(n-1) + fibSeq(n-2);
    }

    public static void main(String[] args) {
        for(int i = 1; i < 10; i++){
            System.out.println(fibSeq(i) + "\t");
        }
    }

}
