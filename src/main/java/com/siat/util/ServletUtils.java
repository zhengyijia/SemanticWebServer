package com.siat.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ServletUtils {

    public static Properties getProperties(String filePath) {
        Properties p = new Properties();
        try {
            InputStream is = ServletUtils.class.getResourceAsStream(filePath);
            InputStreamReader in = new InputStreamReader(
                    ServletUtils.class.getResourceAsStream(filePath), "UTF-8");
            p.load(in);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p;
    }

}
