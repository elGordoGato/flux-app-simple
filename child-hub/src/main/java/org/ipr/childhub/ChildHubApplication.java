package org.ipr.childhub;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@SpringBootApplication
@EnableR2dbcAuditing
public class ChildHubApplication {
    private final Environment env;

    public ChildHubApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(ChildHubApplication.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        boolean enableTomcat = Boolean.parseBoolean(env.getProperty("server.tomcat.enabled", "false"));
        if (!enableTomcat) {
            // If Tomcat is disabled, return null to skip initializing Tomcat
            return null;
        }
        return new TomcatServletWebServerFactory();
    }
}
