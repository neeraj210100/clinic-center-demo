# Swagger/OpenAPI Integration Guide

## Overview

The Clinic Center backend includes Swagger/OpenAPI documentation using SpringDoc OpenAPI, which is the recommended library for Spring Boot 3.x applications.

## Accessing Swagger UI

Once the backend is running:

1. **Swagger UI**: `http://localhost:8080/swagger-ui.html`
   - Interactive API documentation
   - Test all endpoints directly from the browser
   - View request/response schemas
   - See example requests and responses

2. **OpenAPI JSON**: `http://localhost:8080/api-docs`
   - Raw OpenAPI 3.0 specification
   - Can be imported into:
     - Postman
     - Insomnia
     - Swagger Editor
     - Other API testing tools

## Configuration

### Application Properties

Swagger is configured in `application.properties`:

```properties
# Swagger/OpenAPI Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.tryItOutEnabled=true
```

### OpenAPI Configuration

The main configuration is in `OpenApiConfig.java`:
- API title and description
- Contact information
- Server URLs (development and production)
- License information

## Customization

### Update API Information

Edit `src/main/java/com/clinic/config/OpenApiConfig.java`:

```java
Info info = new Info()
    .title("Your Clinic Name API")  // Change title
    .version("1.0.0")
    .contact(contact)
    .description("Your custom description")  // Update description
    .termsOfService("https://yourclinic.com/terms")
    .license(license);
```

### Add More Documentation

Controllers already include Swagger annotations:
- `@Tag` - Groups endpoints by category
- `@Operation` - Describes each endpoint
- `@ApiResponse` - Documents response codes
- `@Parameter` - Describes parameters

Example:
```java
@Operation(
    summary = "Create a new appointment",
    description = "Creates a new appointment and sends WhatsApp confirmation"
)
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Appointment created"),
    @ApiResponse(responseCode = "400", description = "Invalid input")
})
@PostMapping
public ResponseEntity<Appointment> createAppointment(...) {
    // ...
}
```

### Customize DTOs

Add schema annotations to DTOs for better documentation:

```java
@Schema(description = "Request to create an appointment")
public class AppointmentRequest {
    @Schema(description = "Patient's full name", example = "John Doe", required = true)
    private String patientName;
    // ...
}
```

## Features

✅ **Interactive Testing**: Test endpoints directly from Swagger UI
✅ **Schema Documentation**: Automatic schema generation from Java models
✅ **Request/Response Examples**: See example payloads
✅ **Authentication Support**: Ready for adding security schemes
✅ **Multiple Environments**: Configure different servers (dev, prod)

## Security (Future Enhancement)

To add authentication to Swagger:

1. Add security scheme to `OpenApiConfig.java`:
```java
@Bean
public OpenAPI clinicCenterOpenAPI() {
    // ... existing code ...
    
    SecurityScheme securityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");
    
    return new OpenAPI()
        .info(info)
        .servers(List.of(devServer, prodServer))
        .components(new Components().addSecuritySchemes("bearer-jwt", securityScheme))
        .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
}
```

2. Add security annotation to controllers:
```java
@SecurityRequirement(name = "bearer-jwt")
@RestController
public class AppointmentController {
    // ...
}
```

## Troubleshooting

### Swagger UI not loading
- Ensure backend is running on port 8080
- Check that SpringDoc dependency is in `pom.xml`
- Verify no CORS issues

### Endpoints not showing
- Ensure controllers have `@RestController` annotation
- Check that request mappings are correct
- Verify no compilation errors

### Custom paths not working
- Update `springdoc.swagger-ui.path` in `application.properties`
- Restart the application

## Resources

- [SpringDoc OpenAPI Documentation](https://springdoc.org/)
- [OpenAPI Specification](https://swagger.io/specification/)
- [Swagger UI](https://swagger.io/tools/swagger-ui/)
