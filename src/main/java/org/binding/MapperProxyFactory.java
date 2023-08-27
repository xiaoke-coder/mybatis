package org.binding;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 为接口创建代理类
 */
public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(Map<String, String> sqlSesssion) {
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSesssion, mapperInterface);
        // classloader interface invocationhandler
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}
        , mapperProxy);
    }
}
