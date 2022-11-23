package br.dh.meli.integratorprojectfresh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Integrator project fresh application.
 */
@SpringBootApplication
public class IntegratorProjectFreshApplication implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(IntegratorProjectFreshApplication.class);

    @Value("${env.APP_MELI_FRESH}") String profile;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(IntegratorProjectFreshApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started with command-line arguments: {}", args.getOptionNames());
        logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
        logger.info("OptionNames: {}", args.getOptionNames());
        logger.info("Active profile: {}", profile);
    }
}
