package org.session;

import lombok.Getter;
import lombok.Setter;
import org.binding.MapperRegistry;
import org.datasource.druid.DruidDataSourceFactory;
import org.mapping.Environment;
import org.mapping.MappedStatement;
import org.session.defaults.DefaultSqlSession;
import org.transaction.jdbc.JdbcTransactionFactory;
import org.type.TypeAliasRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存从xml中解析的配置对象
 */
@Getter
@Setter
public class Configuration {


    //环境
    protected Environment environment;

    // 注册mapper
    protected MapperRegistry mapperRegistry = new MapperRegistry(this);


    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    /**
     * 解析SQL 映射的语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    /**
     * 配置类中初始化事务管理器和数据源
     */
    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
    }


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

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }
}
