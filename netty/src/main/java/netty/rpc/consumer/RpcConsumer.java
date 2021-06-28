package netty.rpc.consumer;

import netty.rpc.api.IRpcCounterService;
import netty.rpc.api.IRpcHelloService;

public class RpcConsumer {

    public static void main(String[] args) {

        IRpcCounterService counterService = RpcProxy.create(IRpcCounterService.class);
        System.out.println(counterService.add(1, 2));
        System.out.println(counterService.divide(1, 2));
        System.out.println(counterService.multipart(1, 2));
        System.out.println(counterService.subtract(1, 2));

        IRpcHelloService helloService = RpcProxy.create(IRpcHelloService.class);
        System.out.println(helloService.hello("haha"));


    }

}
