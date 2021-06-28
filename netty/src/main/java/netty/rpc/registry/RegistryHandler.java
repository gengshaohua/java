package netty.rpc.registry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.rpc.protocol.InvokerProtocol;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryHandler extends ChannelInboundHandlerAdapter {

    //保存可用的服务
    private static ConcurrentHashMap<String, Object> registryMap
            = new ConcurrentHashMap<>();

    //保存所有相关的服务类
    private List<String> classNames = new ArrayList<>();

    public RegistryHandler(){
        //完成扫描
        scannerClass("netty.rpc.provider");
        doRegister();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        InvokerProtocol request = (InvokerProtocol) msg;
        if(registryMap.containsKey(request.getClassName())){
            Object clazz = registryMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName(), request.getParams());
            result = method.invoke(clazz, request.getValues());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 递归扫描类
     * @param packName
     */
    private void scannerClass(String packName) {
        URL url = this.getClass().getClassLoader()
                .getResource(packName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for(File file : dir.listFiles()){
            //如果是一个文件夹，继续递归
            if(file.isDirectory()){
                scannerClass(packName + "." + file.getName());
            }else{
                classNames.add(packName + "." + file.getName().replace(".class", "").trim());
            }
        }
    }

    /**
     * 注册服务
     */
    private void doRegister() {
        if(classNames.isEmpty()) {return;}
        for(String className : classNames){
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> implementInterFace = clazz.getInterfaces()[0];
                registryMap.put(implementInterFace.getName(), clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
