package ru.netflix.service;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class SaveImages {
    public static void saveImage(String image_path, String name, String resources) {
        try {
            String basePath = loadFilePath(resources);
            String filename = basePath + name + ".jpg";
            FileUtils.copyURLToFile(new URL(image_path), new File(filename), 10000, 10000);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static String loadFilePath(String resources) {
        Properties properties=new Properties();
        Resource resource=new ClassPathResource("config.properties");

        try(InputStream inputStream=resource.getInputStream()){
            properties.load(inputStream);
            return properties.getProperty(resources);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
}
