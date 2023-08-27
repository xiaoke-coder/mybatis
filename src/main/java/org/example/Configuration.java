package org.example;

import lombok.Data;

import java.sql.Connection;
import java.util.Map;

@Data
public class Configuration {
    protected Connection connection;

    protected Map<String, String> dataSource;

    protected Map<String, XNode> mapperElement;
}
