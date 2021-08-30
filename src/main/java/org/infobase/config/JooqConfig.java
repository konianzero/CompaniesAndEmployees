package org.infobase.config;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
    Eliminates application failure to determine a suitable R2DBC Connection URL !!!
    https://stackoverflow.com/a/68297496
 */
@EnableAutoConfiguration(exclude = { R2dbcAutoConfiguration.class })

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class JooqConfig {
}
