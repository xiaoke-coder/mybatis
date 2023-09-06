package org.mapping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.session.Configuration;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class MappedStatement {
    private Configuration configuration;
    private String id;

    private SqlCommandType sqlCommandType;

    private String parameterType;

    private String resultType;

    private String sql;

    private Map<Integer, String> parameter;

    // 构造器模式 == @Builder
    public static class Builder {
        private MappedStatement mappedStatement =new MappedStatement();

        public Builder(Configuration configuration, String id, SqlCommandType sqlCommandType, String parameterType, String resultType, String sql, Map<Integer, String> parameter) {
            mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.sqlCommandType = sqlCommandType;
            mappedStatement.parameterType = parameterType;
            mappedStatement.resultType = resultType;
            mappedStatement.sql = sql;
            mappedStatement.parameter = parameter;
        }

        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            return mappedStatement;
        }

    }

}
