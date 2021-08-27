package org.infobase.config;

import lombok.RequiredArgsConstructor;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/*
    Eliminates application failure to determine a suitable R2DBC Connection URL !!!
    https://stackoverflow.com/a/68297496
 */
@EnableAutoConfiguration(exclude = { R2dbcAutoConfiguration.class })

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class JooqConfig {

    private final DataSource dataSource;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(new ExceptionTransformer()));

        return jooqConfiguration;
    }

    public class ExceptionTransformer extends DefaultExecuteListener {

        @Override
        public void exception(ExecuteContext ctx) {
            SQLDialect dialect = ctx.configuration().dialect();
            SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dialect.name());

            ctx.exception(translator.translate("Access database using Jooq", ctx.sql(), ctx.sqlException()));
        }
    }
}
