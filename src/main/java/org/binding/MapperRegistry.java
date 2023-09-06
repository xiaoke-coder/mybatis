package org.binding;

import cn.hutool.core.lang.ClassScanner;
import org.session.Configuration;
import org.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 提供包路径扫描和映射器代理类注册机服务，完成接口对象的代理类注册
 * 接口名-代理类的映射
 */
public class MapperRegistry {
    private Configuration config;

    public MapperRegistry(Configuration config) {
        this.config = config;
    }

    private final Map<Class<?> , MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public void addMappers(String packageName) {
        // todo hutool工具包可学习其实现类
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for (Class<?> mapperClass : mapperSet) {
            addMapper(mapperClass);
        }
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMpper(type)) {
                throw new RuntimeException("Type" + type + "is already known to the MapperRegistry");
            }
        }
        knownMappers.put(type, new MapperProxyFactory<>(type));
    }

    private <T> boolean hasMpper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            // 代理的接口类字段在构造函数中已传递，就差sqlsession 为啥传SQLsession？生成的代理类通过sqlsession执行SQL语句
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }
}
