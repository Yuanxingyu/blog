package com.stary.listener;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;


// tomcat容器关闭时的清理工作
@Slf4j
@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {}

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //tomcat容器关闭时清除注册的数据库驱动，断开数据库连接
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                log.error("contextDestroyed error", e);
            }
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
        //tomcat容器关闭时清除dubbo的注册，暂时没有用到
        //ProtocolConfig.destroyAll();
    }
}