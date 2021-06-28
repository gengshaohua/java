package netty.rpc.provider;

import netty.rpc.api.IRpcCounterService;

public class IRpcCounterServiceImpl implements IRpcCounterService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        return a - b;
    }

    @Override
    public int multipart(int a, int b) {
        return a * b;
    }

    @Override
    public int divide(int a, int b) {
        return a / b;
    }
}
