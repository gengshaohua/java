package netty.rpc.api;

public interface IRpcCounterService {

    int add(int a, int b);
    int subtract(int a, int b);
    int multipart(int a, int b);
    int divide(int a, int b);
}
