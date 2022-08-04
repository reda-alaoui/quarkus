package io.quarkus.context.test;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class RequestBean {

    public static volatile int DESTROY_INVOKED = 0;

    public String callMe() {
        return "Hello " + System.identityHashCode(this);
    }

    @PreDestroy
    public void destroy() {
        DESTROY_INVOKED++;
    }
}
