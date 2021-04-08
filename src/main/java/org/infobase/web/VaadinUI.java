package org.infobase.web;

import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.spring.annotation.EnableVaadin;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

// https://github.com/mstahv/vaadin-spring-noboot

public class VaadinUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    public static class Servlet extends VaadinServlet {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @ImportResource({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
    @EnableVaadin
    public static class MyConfiguration {
    }
}
