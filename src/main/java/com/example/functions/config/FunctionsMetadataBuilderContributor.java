package com.example.functions.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

@Slf4j
public class FunctionsMetadataBuilderContributor implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        log.info("Contribute");
        metadataBuilder.applySqlFunction(
                "any_int",
                new SQLFunctionTemplate(
                        StandardBasicTypes.BOOLEAN, "ANY(string_to_array(?1, ',')::int[])"
                )
        );
    }
}
