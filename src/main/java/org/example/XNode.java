package org.example;

import lombok.Data;
import java.util.Map;

/**
 * 对 mapper.xml 的建模
 * 解析节点
 */
@Data
public class XNode {
    private String namespace;

    private String id;

    private String parameterType;

    private String resultType;

    private String sql;

    /**
     * 参数索引到参数名的映射
     */
    private Map<Integer, String> parameter;

}
