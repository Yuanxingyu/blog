package com.stary.assist;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public final class SpringAssist implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(@NonNull Class<T> clazzName) {
        if (context != null) {
            return context.getBean(clazzName);
        }
        return null;
    }

    public static <T> T getBean(String name, @NonNull Class<T> clazzName) {
        if (context != null && StringUtils.isNotBlank(name)) {
            return context.getBean(name, clazzName);
        }
        return null;
    }
}
