package org.session;

import org.binding.MapperRegistry;
import org.mapping.MappedStatement;
import org.session.defaults.DefaultSqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存从xml中解析的配置对象
 */
public class Configuration {

    // 注册mapper
    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    /**
     * 解析SQL 映射的语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public <T> T getMapper(Class<T> type, DefaultSqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public void addMappedStatement(MappedStatement ms) {

        mappedStatements.put(ms.getId(), ms);
    }

    public void addMapper(Class<?> type) {
        mapperRegistry.addMapper(type);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }
}
