package com.example.multidatasoure.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(type = SecuritySchemeType.HTTP, name = "bearer", scheme = "bearer", bearerFormat = "JWT")
public class BearerSecurityScheme {
}
