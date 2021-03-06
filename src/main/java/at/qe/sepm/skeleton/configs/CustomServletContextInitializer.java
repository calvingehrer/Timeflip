package at.qe.sepm.skeleton.configs;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;

/**
 * Spring configuration for servlet context.
 * <p>
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Configuration
public class CustomServletContextInitializer implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext sc) {
        sc.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
        sc.setInitParameter("primefaces.THEME", "ecuador-cyan");
    }

}
