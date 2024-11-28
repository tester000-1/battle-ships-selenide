package com.test.utils;

import java.io.InputStream;
import java.util.Properties;

public class AppConfiguration {

    private String baseUrl;
    private String browserName;
    private Long timeout;

    public AppConfiguration(){
        setUpProperties();
    }

    private void setUpProperties() {
        Properties properties = new Properties();
        try (InputStream input = AppConfiguration.class.getClassLoader().getResourceAsStream("game.properties")) {
            properties.load(input);
            baseUrl = properties.getProperty("BASE_URL");
            browserName = properties.getProperty("BROWSER_NAME");
            timeout = Long.valueOf(properties.getProperty("TIMEOUT"));
            assert input != null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getBaseUrl(){
        assert baseUrl != null;
        return baseUrl;
    }

    public String getBrowserName(){
        assert browserName != null;
        return browserName;
    }

    public Long getTimeout(){
        assert timeout != null;
        return timeout;
    }

}
