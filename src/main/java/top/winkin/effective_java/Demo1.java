package top.winkin.effective_java;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 注册服务 获取服务 调用具体的服务实现
 * @Author: wenjiajia
 * @Data: 2018/10/24 11:42 AM
 */
public class Demo1 {
    public static void main(String[] args){
        // registers the provider
        Provider p = new ConnectionProvider();
        Services.registerDefaultProvider(p);
        //get the default provider's service
        Service service = Services.newInstance();
    }

}

interface Service{ }

interface Provider{
    Service newServie();
}

class ConnectionService implements Service{

}

class ConnectionProvider implements Provider{

    @Override
    public Service newServie() {
        return new ConnectionService();
    }
}

class Services{
    // prevents instantiation

    private Services(){}

    //Maps service names to services

    private static final Map<String,Provider> providers = new ConcurrentHashMap<String, Provider>();
    private static final String DEFAULT_PROVIDER_NAME ="<default>";

    //provider registration API

    public static void registerDefaultProvider(Provider p){
        registerProvider(DEFAULT_PROVIDER_NAME,p);
    }
    public static void registerProvider(String name,Provider p){
        providers.put(name,p);
    }

    //service access API

    public static Service newInstance(){
        return newInstance(DEFAULT_PROVIDER_NAME);
    }
    public static Service newInstance(String instanceName){
        Provider p = providers.get(instanceName);
        if(p == null){
            throw new IllegalArgumentException("No provider registered with name:"+instanceName);
        }
        return p.newServie();
    }

}