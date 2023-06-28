package com.tci.evaluacion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class TciConfig {

    final
    Environment env;

    public TciConfig(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));
    }

}
