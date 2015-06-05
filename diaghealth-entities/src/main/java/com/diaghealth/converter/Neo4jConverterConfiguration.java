package com.diaghealth.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories("com.diaghealth.converter")
@EnableTransactionManagement
public class Neo4jConverterConfiguration extends Neo4jConfiguration {

	@Autowired
    private StringToUserTypeConverter stringToUserTypeConverter;

    @Autowired
    private UserTypeToStringConverter userTypeToStringConverter;


    @Override
    protected ConversionService neo4jConversionService() throws Exception {
        ConverterRegistry converterRegistry = (ConverterRegistry) super.neo4jConversionService();
        converterRegistry.addConverter(stringToUserTypeConverter);
        converterRegistry.addConverter(userTypeToStringConverter);
        return (ConversionService) converterRegistry;
    }

}
