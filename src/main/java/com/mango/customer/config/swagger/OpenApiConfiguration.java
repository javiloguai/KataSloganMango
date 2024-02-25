package com.mango.customer.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;


/**
 * @author javiruizidneo
 */
@OpenAPIDefinition(info = @Info(title = "Springboot Mango customerKata", version = "1.0", description = "Mango test", termsOfService = "http://swagger.io/terms/", license = @License(name = "Apache 2.0 Licence for mango test api", url = "http://springdoc.org/")))
public class OpenApiConfiguration {
}
