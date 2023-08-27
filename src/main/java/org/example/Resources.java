package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Resources {
    public static Reader getResourceAsReader(String resource) throws IOException{
        return new InputStreamReader(getResourceAsStream(resource));
    }

    private static InputStream getResourceAsStream(String resource) throws IOException{
        ClassLoader[] classLoaders = getClassLoader();
        for (ClassLoader classLoader : classLoaders) {
            InputStream inputStream = classLoader.getResourceAsStream(resource);
            if (inputStream != null) {
                return inputStream;
            }
        }
        throw new IOException("Could not find resource" + resource);
    }

    private static ClassLoader[] getClassLoader() {
        return new ClassLoader[] {
                // 类加载器
                ClassLoader.getSystemClassLoader(),
                // 基于线程传递的类加载器
                Thread.currentThread().getContextClassLoader()
        };
    }

}
