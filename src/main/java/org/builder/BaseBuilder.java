package org.builder;

import org.session.Configuration;
import org.type.TypeAliasRegistry;

/**
 * 解析xml配置
 */
public abstract class BaseBuilder {
    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
    }
    public Configuration getConfiguration() {
        return configuration;
    }


}
