/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author CND
 */
public class AppServletContextListener implements ServletContextListener {

    private final String BASE_MAP_FILE = "/WEB-INF/map.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext contex = sce.getServletContext();

        String path = contex.getRealPath("/");
        Properties properties = new Properties();
        InputStream ips = null;

        try {
            ips = new FileInputStream(path + BASE_MAP_FILE);
            properties.load(ips);
            contex.setAttribute("map", properties);
        } catch (FileNotFoundException e) {
            Logger.getLogger(AppServletContextListener.class.getName()).log(Level.SEVERE, null, e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(AppServletContextListener.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
