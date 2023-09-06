package org.binding;

import org.mapping.MappedStatement;
import org.mapping.SqlCommandType;
import org.session.Configuration;
import org.session.SqlSession;

import java.lang.reflect.Method;

/**
 * 映射器方法    mybatis代理最终调用的类  关键字段  方法类型 sqlsession
 */
public class MapperMethod {

    private final SqlCommand command;
    public <T> MapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) {
        this.command = new SqlCommand(configuration, mapperInterface, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result = null;
        switch (command.getType()) {
            case INSERT:
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case SELECT:
                result = sqlSession.selectOne(command.getName(), args);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        return result;
    }

    /**
     * SQL 指令 当一个类和另一个类紧密相关时 可以用内部类
     */
    public static class SqlCommand {

        private final String name;
        private final SqlCommandType type;

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            String statementName = mapperInterface.getName() + "." + method.getName();
            MappedStatement ms = configuration.getMappedStatement(statementName);
            name = ms.getId();
            type = ms.getSqlCommandType();
        }
    }
}
