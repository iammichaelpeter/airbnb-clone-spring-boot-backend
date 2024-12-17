package de.iammichaelpeter.airbnbclone.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({
    "de.iammichaelpeter.airbnbclone.user.repository",
    "de.iammichaelpeter.airbnbclone.listing.repository",
    "de.iammichaelpeter.airbnbclone.booking.repository"
})
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfiguration {
}
