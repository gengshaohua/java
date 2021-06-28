package netty.rpc.provider;

import netty.rpc.api.IRpcHelloService;

public class IRpcHelloServiceImpl implements IRpcHelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

}
