# Spring Boot Reviewer (2025 Edition)

---

## Table of Contents

1. [Introduction to Spring Boot](#introduction-to-spring-boot)
2. [Setting Up a Spring Boot Application](#setting-up-a-spring-boot-application)
3. [Spring Boot Core Concepts](#spring-boot-core-concepts)
4. [Building RESTful Web Applications](#building-restful-web-applications)
5. [Data Access with JPA & Hibernate](#data-access-with-jpa-and-hibernate)
6. [Spring Boot Profiles & Configuration Management](#spring-boot-profiles-and-configuration-management)
7. [Transaction Management](#transaction-management)
8. [Localization & i18n](#localization-and-i18n)
9. [Connection Pooling](#connection-pooling)
10. [Security & Best Practices: REST & Microservices](#security-and-best-practices-rest-and-microservices)
11. [Microservices With Spring Boot](#microservices-with-spring-boot)
12. [Project Ideas & Hands-On Practices](#project-ideas-and-hands-on-practices)
13. [Recommended Tools & Resources (2025)](#recommended-tools-and-resources)

---

### 1. Introduction to Spring Boot

#### What is Spring Boot?

Spring Boot is an open-source framework by Pivotal (now VMware Tanzu) that enables rapid, convention-over-configuration
development for stand-alone, production-grade applications based on the Spring ecosystem. Spring Boot streamlines
working with Spring, providing sensible defaults, embedded servers, and dramatically reducing boilerplate code.

- **Primary Goals:**
    - Eliminate boilerplate code and configuration
    - Provide production-ready features (metrics, health checks, externalized config)
    - Reduce time to deploy and iterate for both monolith and microservice architectures

#### Why Spring Boot over Spring Framework?

Spring Framework was historically powerful yet configuration-heavy. Spring Boot abstracts the best practices and
configuration, auto-wiring the most likely setups and allowing deep customization only when necessary.

- **Spring (core) is** … a toolbox for dependency injection and infrastructure wiring.
- **Spring Boot is** … a pre-configured, ready-to-go package that lets you get productive fast, while still letting you
  drop into the toolbox when you need more control.

**Spring Boot advantages:**

- Reduced configuration. Most dependencies work “out of the box.”
- Embedded servers (Tomcat, Jetty, Undertow): no need to deploy WARs separately.
- Wide community and rich third-party starter support.
- Vast ecosystem: cloud, data, reactive, integration, messaging, and observability.
- Best for both learning and building quick MVPs and enterprise-scale microservices.
- Automatic health checks, metrics, and actuator endpoints for monitoring.

**Drawbacks:**

- Heavier abstraction: sometimes difficult to understand what defaults are being applied.
- Upgrades require keeping up with fast Spring release cycles (especially with 3.x+).
- Not always suited for super-lightweight REST-only APIs (can consider Quarkus, Micronaut, etc. if that is a concern).

#### Usage Scenarios

- Microservices backends
- REST APIs
- Event-driven and messaging systems
- Monolith to microservice decompositions
- Cloud-native apps (Spring Cloud, Kubernetes)
- Batch processing, data pipelines

#### Spring Boot Evolution & Modern Relevance (2025)

- **Initial Release:** 2014 (Spring Boot 1.0)
- **Spring Boot 2.x:** Focused on Java 8/11, major auto-configuration and actuator boosts.
- **Spring Boot 3.x+ (2022–2025):**
    - Requires Java 17+ (Java 21 highly recommended)
    - Migrated to Jakarta EE 9+ (package change from `javax.` to `jakarta.`)
    - Supports cloud-native, reactive, and container-first architectures
    - Key features: improved native compilation support, virtual threads, better security integration (Spring Security
      6+), new RestClient, HTTP interface clients, enhanced Actuator endpoints, support for Project Loom, records for
      DTOs, and more
    - Growing support for Kubernetes, distributed tracing (Micrometer, OpenTelemetry)
- **Release cadence:** Major release every 12–18 months, regular minor/bugfix updates

#### Compatibility & The Java Ecosystem

- **Required:** Java 17+ (Java 21 recommended for full modern feature set)
- JVMs: Compatible with OpenJDK, Oracle JDK, GraalVM (native executables)
- IDEs: IntelliJ IDEA, Eclipse, VS Code (Spring Tools), NetBeans

#### Interview & Mastery Notes

- Be able to articulate the difference between Spring, Spring Boot, and other Java frameworks
- Know why we use embedded servers (vs. deploying WARs)
- Understand the implications of auto-configuration and when to override it
- Be able to describe the migration path to Spring Boot 3.x from 2.x (Jakarta EE changes, minimum Java version)
- Be familiar with the new features in 3.x/3.2/3.5 series (e.g. virtual threads, RestClient, records, security)
- Common interview question: "How does Spring Boot simplify the configuration of a Spring application?"
- Know pitfalls around version mismatches and updates, especially with breaking changes in the dependency tree

#### Further Reading / Practice:

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Evolution of Spring Boot](https://www.baeldung.com/spring-boot)
- [Spring Initializr](https://start.spring.io/)
- [roadmap.sh/spring-boot](https://roadmap.sh/spring-boot)
- [Spring Boot 3 Migration Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#migration)

### 2. Setting Up a Spring Boot Application

#### Project Bootstrapping (start.spring.io, Spring Initializr)

The fastest and most reliable way to start a new project is [Spring Initializr](https://start.spring.io/). This tool
scaffolds a ready-to-run project structure for you.

**Step-by-step:**

1. Visit [start.spring.io](https://start.spring.io/)
2. Choose Project: Maven or Gradle
3. Language: Java (Java 17 or 21 recommended)
4. Spring Boot Version: Prefer latest stable (e.g. 3.5.x)
5. Group/Artifact/Name: (e.g. com.acme, demo)
6. Packaging: JAR (unless deploying to legacy servlet container)
7. Java Version: 17 or 21
8. Dependencies: Select at least "Spring Web". Add Data JPA, Spring Security, Actuator, Lombok, H2/Database as needed
9. Click "Generate"
10. Extract and open the zipped project in your IDE

#### Example Directory Structure (2025 default)

```
my-springboot-app/
├── src/
│   ├── main/
│   │   ├── java/com/example/app/   # Your code (controllers, services, etc.)
│   │   ├── resources/
│   │   │   ├── application.properties (or application.yml)
│   │   │   └── static/, templates/
│   ├── test/
│   │   └── java/com/example/app/   # Unit & integration tests
├── pom.xml   # (for Maven) -or- build.gradle  # (for Gradle)
└── ...
```

**Best Practices (2025):**

- Use lower-case, hyphenated project and package names for consistency
- Place `Application.java` (with the @SpringBootApplication annotation) in the base package (e.g. `com.example.app`)
- Avoid placing code in the default package (no package statement)
- Group by "feature" (domain-driven) rather than by layer, for larger projects
- Keep resources organized: static for JS/CSS/assets, templates for Thymeleaf/Freemarker

#### Maven vs Gradle Setup

- **Maven** is the most common, more stable for beginners. Example `pom.xml`:

```xml
<project ...>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>demoapp</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.2</version>
    <relativePath/>
  </parent>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>
  </dependencies>
    <!-- Plugin section optional, for custom builds -->
  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
</project>
```

- **Gradle** (Groovy or Kotlin DSL) is more concise for advanced users. Example `build.gradle` (Groovy):

```groovy
plugins {
	id 'org.springframework.boot' version '3.5.2'
	id 'io.spring.dependency-management' version '1.1.5'
	id 'java'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourcesCompatibility = '21'
repositories { mavenCentral() }

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	runtimeOnly 'com.h2database:h2'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

**Key Maven/Gradle Best Practices:**

- Inherit Spring Boot BOM for dependency version management.
- Never override managed versions unless necessary (causes conflicts).
- Use "spring-boot-starter-*" dependencies for modularity.
- Use strict Java version compatibility with Spring Boot release (3.x+ needs Java 17+)
- For multi-module projects, keep parent/base configuration DRY.

#### Initializing Core Dependencies

- Always begin with `spring-boot-starter-web` or `spring-boot-starter`.
- Add only required starters: data-jpa, security, actuator, etc.
- Remove unused starters to minimize classpath and build speed issues.
- Lombok is popular, but not required (simplifies models, DDD code).

#### IDE Setup & Productivity Plugins

- IntelliJ IDEA (Community or Ultimate) - best Spring integration.
- Eclipse with Spring Tools Plugin.
- VS Code with "Spring Boot Extension Pack".
- Useful plugins: Lombok, Docker, Checkstyle, .editorconfig, Git integration.

#### Common Pitfalls & Troubleshooting

- **Mismatched Java versions:** Ensure Eclipse/IntelliJ/VSCode are configured for Java 17 or 21 (Project SDK).
- **Failed to start embedded Tomcat:** Wrong main class or structure, check that `@SpringBootApplication` is on a class
  in the root package.
- **Unresolved dependencies/build errors:** Delete `.m2/repository` or Gradle caches, run `mvn clean install` or
  `gradle clean build` again.
- **Port in use errors:** Default port is 8080; either shut down other running apps or set another port in
  `application.properties` (`server.port=8081`).
- **IDE build errors:** Invalidate caches/restart, ensure Maven/Gradle sync is complete and all plugins loaded.
- **Outdated plugin/config warning:** Use latest plugins for Java and Spring Boot support. Review release notes for
  breaking changes (e.g. Java 21, new default configurations).

#### Useful 2025 References

- [Official Step-by-Step Guide](https://spring.io/guides/gs/spring-boot/)
- [Spring Boot 3.5.x Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.2-Release-Notes)
- [Best Practices & Troubleshooting](https://amigoscode.com/blogs/spring-boot-roadmap-2025)
- [Advanced Gradle Spring Boot Setup](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/)

---

### 3. Spring Boot Core Concepts

Spring Boot’s “core concepts” lay the foundation for rapid, production-grade Java application development in 2025.
Mastering these enables you to diagnose problems, build robust applications, and answer interview/architecture questions
with authority.

---

#### 3.1. `@SpringBootApplication` and Meta-Annotations

- **Definition:** Combines three key Spring annotations:
    - `@Configuration`: Marks the class as a source of bean definitions.
    - `@EnableAutoConfiguration`: Tells Spring Boot to guess how to configure Spring beans based on classpath contents.
    - `@ComponentScan`: Enables scanning for `@Component`, `@Service`, `@Repository`, and `@Controller` in sub-packages.

- **Placement:**  
  Always put `@SpringBootApplication` on your main class at the root of your codebase package.  
  Example:
  ```java
  package com.example.demo;

  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;

  @SpringBootApplication
  public class DemoApplication {
      public static void main(String[] args) {
          SpringApplication.run(DemoApplication.class, args);
      }
  }
  ```
- **Advanced:** You can explicitly use the individual annotations for fine control.

- **Best Practices:**
    - Only one main class annotated with `@SpringBootApplication` per application.
    - Main class should be in the root package to ensure component scanning works for all sub-packages.
    - Use meta-annotations (`@SpringBootApplication`) for simplicity; customize only if needed.
    - In multi-module setups, prefer the main application module as the only bootstrapping entry point.

- **Troubleshooting:**
    - “No qualifying bean” errors may be due to the main class being in a non-root package.
    - Multiple main classes: Only one should be designated as the application entrypoint unless using special launchers.

---

#### 3.2. Configuration: `application.properties` vs `application.yml`

Spring Boot uses *externalized configuration* so your code and environment setup are cleanly separated.

- **Default location:** `/src/main/resources/`
- `application.properties`: Traditional Java properties, `key=value` format.
- `application.yml`: YAML format, supports hierarchical, structured configuration.

**Example—`application.properties`:**

```
server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/db
spring.datasource.username=admin
```

**Example—`application.yml`:**

```yaml
server:
  port: 8081
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/db
    username: admin
    password: secret
```

- **Which to choose?**
    - Use `application.yml` for more complex, nested config or when you want grouped sections.
    - Use `application.properties` for small, simple projects.

- **Profile-specific configs:**
    - `application-dev.yml` or `.properties`, `application-prod.yml`, etc.
        - Spring Boot loads the base config, then profile-specific ones according to the `spring.profiles.active` value.

- **Overriding Config:**
    - **Order of precedence:** Command line arguments > OS environment variables > profile config > application default
      config.
    - Supports `SPRING_APPLICATION_JSON` env var, Docker secrets, Kubernetes config maps, and HashiCorp Vault in
      cloud-native setups.

- **Loading external config:**
    - Via `--spring.config.location` CLI parameter
    - Via cloud config (Spring Cloud Config Server)
    - Injected with `@Value("${property.name}")` or through `@ConfigurationProperties`

**Best Practices:**

- Store secrets outside VCS and outside deployed artifact.
- Document all custom config keys.
- Use `@ConfigurationProperties` classes for type-safe config binding.

**Common Pitfalls:**

- YAML formatting errors (indentation!) cause silent failures—always validate your YAML.
- Property names are case-sensitive.
- Confusing which value is being used? Turn on debug logging or use Actuator `/configprops`.

---

#### 3.3. Auto-Configuration & Custom Configuration

- **Auto-Configuration:**
    - Spring Boot scans your classpath for known dependencies (“starters”) and automatically configures beans/settings
      for you.
    - Example: If `spring-boot-starter-web` is present, it configures embedded Tomcat, web MVC, static resource serving,
      etc.

- **Custom Configuration:**
    - You can override auto-config by providing your own config classes and beans.
    - Use `@Configuration` for defining manual beans:
      ```java
      @Configuration
      public class WebConfig {
          @Bean
          public MyCustomBean myCustomBean() {
              return new MyCustomBean();
          }
      }
      ```
    - Remove or exclude unwanted auto-config with:
        - `@SpringBootApplication(exclude = SomeAutoConfiguration.class)`
        - `spring.autoconfigure.exclude=...` in properties.

- **How does it work?**
    - Auto-config is enabled from `@EnableAutoConfiguration` meta-annotation.
    - Uses “ConditionalOn…” annotations to only apply beans if certain classes/properties exist.

- **Best Practices:**
    - Prefer auto-config until you need customization for business/infra requirements.
    - Document exclusions and overrides for future maintainers.

- **Troubleshooting:**
    - Missing beans? Check dependency inclusion and exclusions.
    - Unexpected config? Check what auto-config class is providing default—Spring Boot’s debug logs and Actuator
      `/beans`, `/conditions` endpoint help here.

- **Interview/Knowledge Tip:**  
  Know *how* Boot finds and matches configuration classes by reading the
  official [auto-configuration reference](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration).

---

#### 3.4. Conditional Beans

Conditional activation lets you wire up “smart” beans, only present in certain environments or if other beans, classes,
properties, or profiles exist.

- **Most commonly used:**
    - `@ConditionalOnProperty`: Only create bean if property matches value.
    - `@ConditionalOnClass`: Only if a class is present (e.g. a driver).
    - `@ConditionalOnMissingBean`: If no bean of this type is already defined.
    - `@ConditionalOnExpression`: SpEL-based conditions.
    - `@Profile`: Only active when profile matches.

**Example—Bean enabled via property:**

```java
@Bean
@ConditionalOnProperty(name="feature.new.enabled", havingValue="true")
public NewFeatureHandler newFeatureHandler() {
    return new NewFeatureHandler();
}
```

**Example—Bean only in dev:**

```java
@Bean
@Profile("dev")
public TestDataGenerator testDataGenerator() {
    return new TestDataGenerator();
}
```

- **Advanced:** Create your own `@Conditional` annotation for custom logic.
- **Why use conditionals?**
    - Feature toggling, blue/green deployments, modules that depend on external services.

- **Best Practices:**
    - Avoid excessive conditional beans—can hurt readability.
    - Use descriptive property names for toggles.
    - Document *why* a bean is conditional for maintainability.

- **Troubleshooting:**
    - Not sure why a bean was or wasn’t loaded? Use the `Actuator` `/conditions` endpoint.

---

#### 3.5. Profiles

Profiles allow your app to vary configuration and bean setup for different environments (dev, test, prod, staging,
CI/CD, etc.).

- **Configuring:**
    - In `application.properties`/`yml`: `spring.profiles.active=dev`
    - Environment variable: `SPRING_PROFILES_ACTIVE=prod`
    - As a CLI arg: `--spring.profiles.active=test`
    - Profiles can be set programmatically via `SpringApplication.setAdditionalProfiles("dev")`

**Example—Profile-specific bean:**

```java
@Bean
@Profile("prod")
public DataSource prodDataSource() {
    // Real cloud or DB connection
    return new HikariDataSource();
}
```

- **Profile config files:**
    - `application-dev.yml` will override settings when active profile is "dev".

- **Best Practices:**
    - Separate sensitive configs by profile.
    - Always define default profile for local development.
    - Use profiles for testability: mock integrations, different DBs, etc.

- **Pitfalls:**
    - Forgetting to set the active profile can result in wrong beans/config.
    - “Dev” config accidentally deployed to production—double check profile activation during build/deploy!

- **Interview Tip:**  
  Be able to explain how profiles help with continuous delivery and multi-environment deployments.

---

#### 3.6. Embedded Server Options

Spring Boot comes *preconfigured* with embedded servlet containers.

- **Default:** Apache Tomcat (`spring-boot-starter-web`)
- **Alternatives:**
    - Jetty (`spring-boot-starter-jetty`)
    - Undertow (`spring-boot-starter-undertow`)

- **To swap**, remove default starter and add chosen server dependency (in Maven or Gradle).

**Why embedded?**

- No need to install/manage containers separately.
- Single artifact (JAR) deploys everywhere—popular for Docker and cloud.

**Changing server port and settings:**

```properties
server.port=8085
server.servlet.context-path=/api
server.tomcat.threads.max=200
```

**Enable HTTPS:**

```properties
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=secret
server.ssl.key-store-type=PKCS12
server.port=8443
```

- **Advanced tuning:**  
  Fine-tune connection pool, threads, timeouts in `application.properties` depending on the server.

- **Monitoring health:**  
  Enable Actuator endpoints for `/health`, `/metrics`, `/logfile`.

- **Best Practices:**
    - For microservices and containers, always use embedded servers.
    - For legacy enterprise deploys, consider WAR packaging and external server—but only if required.

- **Pitfalls:**
    - Port conflicts most common in dev—change `server.port` or kill conflicting process.
    - Memory and thread tuning needed for heavy workloads.

- **Interview Tip:**  
  Know the advantages (portability, simplicity), but also recognize latency and scalability limits for large
  monoliths—when would you need something else?

---

#### 3.7. Logging Configuration

Logging in Spring Boot is handled through SLF4J with Logback (default) or Log4j2.

- **Default log config:**
    - Console logging enabled.
    - Pattern, log file, and rolling policy configurable via properties.
    - Config file: `logback-spring.xml` (preferred!) in `/resources`

**Example:**

```properties
logging.level.org.springframework=INFO
logging.level.com.example=DEBUG
logging.file.name=logs/app.log
logging.pattern.console=%d{HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

- **Changing log backend:**
    - Add `spring-boot-starter-log4j2` and exclude Logback dependency.

- **Logging in production:**
    - Ship logs to ELK, Grafana Loki, or dedicated cloud log aggregators.
    - Structure logs for searchability—use JSON format in modern setups.

- **Actuator endpoints:**
    - `/actuator/loggers` lets you change log level at runtime (if enabled).

- **Best Practices:**
    - Never log secrets or sensitive fields.
    - Use appropriate levels—INFO for business flows, WARN for recoverable blockages, ERROR for crashes, DEBUG only in
      dev.
    - Use MDC context for request tracing.

- **Pitfalls:**
    - Too verbose logs degrade performance and clutter disk.
    - Misplaced log patterns make maintenance hard—use standard config approach.

- **Troubleshooting:**
    - Failed to log? Check file permissions, rolling config or backend selection.
    - Use Actuator’s `/logfile` endpoint for quick diagnosis.

---

#### 3.8. Key Updates in Spring Boot 3.x / 3.5 (2025)

Spring Boot continues evolving for modern Java and cloud-native needs, especially since 3.x:

- **Java 17+ and Project Loom (Java 21):**
    - Spring Boot 3.x requires minimum Java 17; Java 21 recommended for “virtual threads” and optimized JVM features.
    - Virtual threads: Lightweight concurrency, great for web apps.  
      Enable with:
      ```properties
      spring.threads.virtual.enabled=true
      ```
      Lowers thread usage, boosts scalability—ideal for modern HTTP APIs.

- **RestClient (New HTTP Client API):**
    - Next-gen replacement for RestTemplate.
    - Typesafe, supports fluent API, integrates directly with Spring Boot.
    ```java
    RestClient restClient = RestClient.builder()
        .baseUrl("https://api.example.com")
        .build();

    MyResponse resp = restClient.get()
        .uri("/data")
        .retrieve()
        .body(MyResponse.class);
    ```
    - Prefer RestClient for new code; WebClient for fully reactive/non-blocking.

- **Java Records for DTOs:**
    - Native Java “records” for Lightweight immutable value objects—useful for DTOs and API responses:
    ```java
    public record UserDTO(Long id, String username, String email) {}
    ```

- **Actuator Upgrades:**
    - More granular health checks, custom endpoints, distributed trace integration (Micrometer, OpenTelemetry).

- **Jakarta EE 9 migration:**
    - All Spring APIs switch from `javax.*` to `jakarta.*` packages since Spring Boot 3.x.
    - Breaking change: Update old code and dependencies to be Jakarta-compliant.

- **Other:**
    - Native compilation with GraalVM (AOT/Ahead-of-Time) for fast startup and small Docker images.
    - Improved metrics, support for records, and HTTP interfaces.

**Best Practices (2025):**

- Use virtual threads for HTTP/REST workloads unless you have very specific legacy threadpool or reactive needs.
- Migrate old code to Java 17/21 and Jakarta.
- Prefer RestClient over RestTemplate for new development.
- Make DTOs with records for immutability.
- Always read the migration guide when upgrading across major versions!

**Interview/Mastery Notes:**

- Articulate the major differences moving from Spring Boot 2.x → 3.x (Jakarta, Java, features).
- Virtual threads: Explain pros/cons vs traditional pool-based concurrency.
- RestClient: Be able to demo usage, why it's preferred over legacy RestTemplate/WebClient.

---

#### 3.9. Interview “Must Knows”, Practice & Troubleshooting Tips

- Know the difference between classic Spring config and Boot auto-config.
- How and why to customize auto-config (incl. `@ConditionalOnMissingBean` usage).
- Embedded servers—when/why? Their pros/cons.
- How profiles help with CI/CD and multi-env deployments.
- Describe virtual threads’ advantages versus traditional concurrency.
- Debugging config & beans: Use Actuator, check main class position, config precedence, etc.
- Logging: Know levels, config files, runtime change; avoid common logging mistakes.
- Keep up-to-date with breaking changes and new features.

**Hands-on:**

- Try changing your main class or package to see effect on component-scan/beans.
- Switch logging configs and practice setting log levels at runtime.
- Write conditional beans, test feature toggles by flipping properties.

---

#### Further Reading & Practice Links

- [Spring Boot Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)
- [Conditional Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration)
- [Embedded server docs](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web.servlet.embedded)
- [Virtual Threads in Boot 3](https://www.danvega.dev/blog/spring-boot-3-2)
- [Spring Boot Logging](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.logging)
- [Spring Boot 3 Migration Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#migration)
- [RestClient reference](https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#restclient)
- [Jakarta EE migration FAQ](https://spring.io/blog/2022/08/10/spring-and-jakarta-ee-9)

---

### 4. Building RESTful Web Applications

Designing and implementing REST APIs with Spring Boot is foundational for backend development—these patterns power
microservices, mobile backends, and most cloud/web platforms.

---

#### 4.1 What is REST? REST vs. SOAP

- **REST** (Representational State Transfer): An architectural style—stateless, client-server, resource-oriented.
    - HTTP verbs (GET, POST, PUT, DELETE, PATCH) act on resources (e.g. `/users/123`).
    - URL structure: /resource/{id}
    - Designed for simplicity, scalability, and ease of integration.
- **SOAP:** Heavier, XML-based protocol, usually for older enterprise integrations or strongly defined contracts; rarely
  used for modern web APIs.

---

#### 4.2 Basic Spring Boot REST Controllers

- Use `@RestController` for API endpoints—combines `@Controller` with `@ResponseBody`.

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        // fetch from service/repo, return DTO
    }
    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserRequest req) {
        // validate, save, return created DTO
    }
}
```

- Endpoint mapping:
    - `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping`
    - `@RequestParam`, `@PathVariable`, `@RequestBody` for argument binding

**Common Patterns:**

- DTOs/records for request and response shape (avoid using entities directly)
- Use `ResponseEntity<T>` for rich response management (status codes, headers)

---

#### 4.3 Mapping URLs and HTTP Methods

- RESTful APIs use HTTP verbs:
    - `GET`: Fetch/read resource(s)
    - `POST`: Create a new resource
    - `PUT`: Replace entire resource
    - `PATCH`: Partially update resource
    - `DELETE`: Remove resource
- Example best practice URL structures:
    - `/users` (GET all, POST new)
    - `/users/{id}` (GET/PUT/PATCH/DELETE one)
    - Hierarchical: `/users/{userId}/orders/{orderId}`

---

#### 4.4 Request Parameters, Validation, and Error Handling

- Use `@RequestParam` for query params, `@PathVariable` for URL params, `@RequestBody` for JSON payload.
- Input validation: Annotate DTOs and use Bean Validation (Jakarta Validation, e.g. `@NotBlank`, `@Min`).

```java
public record CreateUserRequest(@NotBlank String username, @Email String email) {}
```

- Add `@Valid` to validate request body automatically:

```java
@PostMapping
public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest req) {...}
```

- **Global error handling**:

```java
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        // map errors to fields/messages
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex) {...}
}
```

---

#### 4.5 Standardized API Structure & Best Practices

- Always return helpful error responses (not just 500 or stacktrace)
    - Body shape: `{ "timestamp": ..., "status": 400, "error": ..., "message": ..., "path": ... }`
- Consider using a consistent envelope for API responses, esp. for mobile/SPA clients.
- For POST/PUT, return Location header and appropriate status (`201 Created`).
- Use enums or constants for status codes and error types in error DTOs.
- Query, body, and headers all should be validated and sanitized.

---

#### 4.6 Content Negotiation & API Versioning

- Support both JSON and XML when needed (JSON default). Use
  `@RequestMapping(..., produces = MediaType.APPLICATION_JSON_VALUE)`.
- Version your API:
    - URL versioning: `/api/v1/resource`
    - Header versioning: Clients send `Accept-version: 1.0`
    - Media type versioning: Custom media type—rare (e.g. `application/vnd.company.v1+json`)
    -
    See [API Versioning Guide](https://aditya-sunjava.medium.com/api-versioning-in-spring-boot-a-complete-guide-with-code-examples-6928c0627b52)
- **Best Practice (2025):** URL versioning is simplest and most common; design for backward compatibility.

---

#### 4.7 Testing REST Endpoints (Manual & Automated)

- Manual: Use Postman, curl, Insomnia, HTTPie.
- Automated (recommend for all real projects!):

```java
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired MockMvc mockMvc;

    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("bob"));
    }
}
```

- For integration/end-to-end: consider [Testcontainers](https://www.testcontainers.org/) to start real DBs in Docker,
  wire up app with real dependencies for tests (modern approach).

---

#### 4.8 OpenAPI & Swagger Documentation

- OpenAPI (Swagger) is *standard* for documenting REST APIs.
    - Use [springdoc-openapi](https://springdoc.org/) library for annotation-based generation.
    - Auto-generates `/swagger-ui.html` and `/v3/api-docs` endpoints.
    - Document your endpoints, request/response payloads, errors, security schemes.

```java
@Operation(summary = "Get user by ID", description = "Returns a user for the given ID")
@ApiResponse(responseCode = "200", description = "success", content = ...)
@GetMapping("/{id}")
public UserDTO getUser(@PathVariable Long id) {...}
```

- Always publish up-to-date docs for frontend/mobile/integration developers.

---

#### 4.9 HATEOAS & Response Best Practices

- HATEOAS (Hypermedia as the Engine of Application State): Return links and actions in your responses so clients can
  “discover” next steps—useful for enterprise APIs.
    - Example: `/users/123` returns links to `/users/123/orders`
    - [Spring HATEOAS docs](https://docs.spring.io/spring-hateoas/docs/current/reference/html/)
- Use pagination (`Pageable`, `@PageableDefault`), sorting, filtering in GET endpoints for large datasets.

---

#### 4.10 Validation with Jakarta Bean Validation (2025)

- Use modern validation annotations (e.g. `@Size`, `@Pattern`, `@Min`) on request payloads.
- Custom validators for business rules.
- Global handler for validation errors; never leak stacktraces to clients.

---

#### 4.11 Configuring CORS (Cross-Origin Resource Sharing)

- Allows web clients from other domains to call your API (needed for mobile apps, frontend SPAs).
    - Default in Spring Boot 3.x is strict (no CORS unless enabled).

```java
@RestController
@CrossOrigin(origins = "https://myfrontend.com")
public class UserController {}
```

- Fine control: configure global CORS in `WebMvcConfigurer`.

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
```

- Security: Never use `allowedOrigins("*")` in production unless you’re building a public API.
- Test CORS via browser DevTools (OPTIONS preflight requests).

---

#### 4.12 Advanced Patterns & Mistakes to Avoid (2025)

- Don’t expose entities directly—use DTOs/records always!
- Use PATCH for partial updates; PUT for whole object replaces.
- Always validate incoming payloads to prevent buggy or malicious inputs.
- Don’t leak internal errors—centralize error handling.
- Version your API before you “need” to break compatibility.
- Document and maintain predictable status codes (e.g. always 404 for not found, not 200 with body=“not found”).
- OpenAPI docs must be kept up-to-date (never let them drift from actual implementation).
- Avoid overloading endpoints with too many responsibilities (“fat controllers”). Move business logic to services.
- Explicitly configure timeouts and rate limits (see security section for details).

---

#### 4.13 Interview Points & Study Prompts

- What’s the difference between REST and SOAP? Why prefer REST?
- How do you validate and sanitize incoming requests?
- What would a good error response look like versus a bad one?
- How do you implement and manage API versioning over time?
- Why use DTOs, not entities, in API payloads?
- What tools and strategies exist for live, automated testing?
- How do you expose, secure, and document endpoints for external consumers?

**Practice**:

- Build a small CRUD API for a simple resource (user, product, etc.) covering all methods, validation, error handling,
  OpenAPI docs.
- Try breaking the app with missing inputs, bad requests—fix with validation and error handling.
- Test API from Postman, curl, browser (for CORS).
- Write JUnit tests for every endpoint, and try using Testcontainers for real DB.

---

**Recommended References:**

- [Spring Boot REST Tutorial](https://spring.io/guides/tutorials/rest)
- [Top REST Patterns for 2025](https://ramakrishna-01.medium.com/spring-boot-rest-api-7-mistakes-you-must-avoid-in-2025-fix-them-before-2026-d528164484f1)
- [OpenAPI/Swagger for Spring Boot](https://springdoc.org/)
- [API Versioning Best Practices](https://aditya-sunjava.medium.com/api-versioning-in-spring-boot-a-complete-guide-with-code-examples-6928c0627b52)
- [Testcontainers.org](https://www.testcontainers.org/)
- [30+ API Interview Questions (2025)](https://levelup.gitconnected.com/30-spring-boot-3-rest-api-patterns-and-interview-questions-to-ace-your-java-coding-game-2025-a7f3337eca7b)

---

### 5. Data Access with JPA and Hibernate

Spring Boot leverages JPA (Java Persistence API) for robust data access and Hibernate as the default provider—the
integration is the gold standard for modern Java backend development.

---

#### 5.1 What is JPA? What is Hibernate? (Core Theory)

- **JPA:** Java’s official specification (interface) for object-relational mapping (ORM).
- **Hibernate:** The most popular concrete implementation of the JPA spec—adds more features, vendor-specific tuning.

**Spring Data JPA**: A library that makes working with JPA/Hibernate even easier, adding CRUD interfaces, paging,
generic queries, and eliminating boilerplate.

---

#### 5.2 Why Use JPA + Hibernate in Spring Boot?

- Simplifies DB CRUD and advanced operations with minimal code
- Integrates with transaction management, validation, and Spring’s dependency injection
- Portable between H2, Postgres, MySQL, Oracle, etc
- Supports code-first (DDL auto-generation), migrations, advanced queries, caching

---

#### 5.3 Setting Up Databases and Spring Boot Integration

- Add the right “starter”: `spring-boot-starter-data-jpa` plus your JDBC driver (e.g., `postgresql`, `mysql`, `h2` for
  dev)
- Example `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/app_db
    username: app_user
    password: secret
  jpa:
    hibernate:
      ddl-auto: update  # (dev only! use 'validate' or migrations for prod)
    show-sql: true
    properties:
      hibernate.format_sql: true
```

- Auto-wires `EntityManager`, sets up repositories automatically.

---

#### 5.4 Entity Design (@Entity, @Table, Relationships)

- Map Java objects to relational tables.

```java
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false)
    private String username;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}
```

**Key Mapping Annotations:**

- `@Id`, `@GeneratedValue`, `@Column`, `@Table`
- For relationships:
    - `@OneToOne` (user–profile), `@OneToMany` (user–orders), `@ManyToOne`, `@ManyToMany`
    - Control fetch type (`FetchType.LAZY`/`EAGER`), cascade options
    - Join columns: `@JoinColumn`, `@JoinTable` for many-to-many

**Pitfalls:** Circular and bidirectional references—always map the “owning” side, use DTOs for API, control JSON
serialization with `@JsonManagedReference`, `@JsonBackReference`.

---

#### 5.5 Repository Pattern: CrudRepository, JpaRepository, Paging

- **CrudRepository:** Simple, CRUD operations (`save`, `findById`, `findAll`, `delete`, etc.)
- **JpaRepository:** Adds paging, sorting, batch ops

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);
}
```

- **Custom methods:**
    - Signature-based queries (e.g. `findByStatusAndTypeOrderByCreatedDesc`)
    - JPQL (`@Query`) and native (`@Query(value = "...", nativeQuery = true)`) queries
- **Projections/DTOs:**
    - Use projection interfaces or return DTOs directly for efficiency
    - @Query("select new com.example.UserSummary(u.id, u.username) from User u")

---

#### 5.6 CRUD Operations & Advanced Queries

- Create, Read, Update, Delete all work with `save`, `findById`, `deleteById`, etc.
- Batch operations: `saveAll`, `deleteAll`
- Paging and sorting: `Pageable`, `Sort`

```java
Page<User> users = userRepository.findAll(PageRequest.of(0, 10, Sort.by("username")));
```

---

#### 5.7 DTOs, Projections & Mapping Best Practices

- Expose DTOs/records to API (never entities directly)

```java
public record UserDTO(Long id, String username, String email) {}
```

- Use [MapStruct](https://mapstruct.org/) or ModelMapper for auto-mapping (optional but recommended for large apps)
- Projections speed up queries (return only what you need)

---

#### 5.8 Modern ORM Techniques

- **Lazy vs Eager:**
    - Default is lazy; only load joined data when accessed. Eager can lead to performance issues/N+1.
    - Use `@EntityGraph` or fetch joins for optimal eager loading.
- **Caching:**
    - L1 (session, always enabled), L2 (needs config: Ehcache, Hazelcast, etc)
    - Annotate cacheable query methods with `@Cacheable` in your service layer
- **Advanced Type Mappings:**
    - For JSON columns (Postgres): Use `@Type(type="jsonb")` from Hibernate Types or custom converters
    - UUID columns: Use field as `UUID`—supported in modern JDBC drivers

---

#### 5.9 Transactions & Error Handling

- Transactions: Use `@Transactional` on service/DAO layer for multi-step ops
- Catch and handle `DataIntegrityViolationException`, `ConstraintViolationException` for uniqueness, not null, etc.
- For service-layer custom errors (e.g. duplicate email), throw your own business exceptions

---

#### 5.10 Testing JPA/Hibernate Code

- Use Spring Boot’s `@DataJpaTest` for fast, in-memory tests with rollback
- Testcontainers: Launch real DB (Postgres/MySQL/etc) in Docker for full integration/e2e tests

```java
@DataJpaTest
class UserRepositoryTests {
    @Autowired UserRepository repo;
    @Test
    void canSaveAndFindUser() {
        User user = repo.save(new User(...));
        Optional<User> found = repo.findByUsername(user.getUsername());
        assertThat(found).isPresent();
    }
}
```

---

#### 5.11 Schema Management: Migrations with Liquibase/Flyway

- Prefer code-first (DDL auto-gen) for early prototypes/dev, but for production *always* use explicit migration tools
- Add either `liquibase-core` or `flyway-core` to your dependencies
- Use `/db/changelog/db.changelog-master.xml` (Liquibase) or `db/migration/V1__init.sql` (Flyway) for scripts
- Each migration is applied at app startup, logged to `_schema_version` or `databasechangelog` table

---

#### 5.12 Troubleshooting & Tips

- **N+1 selects problem:** Use `@EntityGraph`, fetch joins in queries, set log level to DEBUG for SQL; diagnose with
  tools like Hibernate Statistics, JPA Buddy
- **Database auto-migration warnings:** Never use `ddl-auto: update` in production (risk of data loss/corruption)
- **LazyInitializationException:** Always fetch relationships eagerly when needed in service layer/DTO, never in
  controller
- **Inefficient queries:** Profile queries with Spring Boot’s SQL logs or use JPA/Hibernate statistics and DB profiler
  tools

---

#### 5.13 Interview/Workplace Must-Knows

- What is the difference between JPA, Hibernate, Spring Data JPA?
- Why use DTOs/records instead of direct entity binding?
- How do you solve N+1 problem?
- What is the risk of auto schema update in production?
- Explain bidirectional relationship mapping and how to avoid infinite loops in serialization.
- What’s the difference between @OneToMany and @ManyToMany, and when do you use each?
- How is database migration managed and why is it critical?

---

#### Recommended Practice

- Build entity models (with various relationships) and repositories for 2+ resource types
- Configure both H2 (dev/test) and Postgres or MySQL (prod)
- Try breaking schema, duplicate keys, unique/email constraints—handle all errors in a test
- Implement and call migration scripts, revert a failed migration, test rollback

---

#### Further Reading / Modern References

- [Official Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [GeeksForGeeks Hibernate + JPA](https://www.geeksforgeeks.org/java/spring-boot-integrating-hibernate-and-jpa/)
- [JPA Best Practices](https://medium.com/javarevisited/spring-data-jpa-complete-guide-and-cheat-sheet-c8138577b71a)
- [Modern Hibernate Types for JSON/UUID](https://vladmihalcea.com/hibernate-types-json/)
- [Liquibase Docs](https://docs.liquibase.com/), [Flyway Docs](https://flywaydb.org/)

---

### 6. Spring Boot Profiles and Configuration Management

Configuration management in Spring Boot is essential for building robust apps that run across different environments (
dev, test, prod, cloud) with security and consistency.

---

#### 6.1 Profiles: Basic and Advanced

- **Profiles** allow different configurations (DBs, endpoints, beans) for each environment—`dev`, `test`, `prod`,
  `stage`, `cloud`, etc.
- Profile is set with:
    - `spring.profiles.active=dev` (in `application.properties` or `yml`, env, or JVM system property)
    - Environment variable: `SPRING_PROFILES_ACTIVE=prod`
    - CLI: `java -jar app.jar --spring.profiles.active=test`
- Annotate beans/config with `@Profile("prod")`, `@Profile({"dev", "test"})` for conditional creation.
- **File naming:**
    - `application.yml` (base)
    - `application-dev.yml`, `application-prod.yml` (layered on top when profile is active)
- Spring merges active profiles in order: *base → selected profile(s)*.

**Best Practices:**

- Use at least `dev`, `test`, and `prod`—name profiles after real deploy stages.
- Ensure only production-safe beans/config are active in prod.
- Always document which properties are profile-specific.

---

#### 6.2 Advanced: Property Layering and Feature Toggles

- You can set properties in multiple places—Spring uses a clear order of precedence:
    1. Command-line args, e.g. `--server.port=9000`
    2. OS env vars (`export SERVER_PORT=9000`)
    3. Profile-specific files (`application-prod.yml`)
    4. `application.yml` (base)
    5. Defaults in code (with `@Value` or `@ConfigurationProperties`)
- **Feature toggles:** Use properties like `feature.x.enabled=true`, activate beans with `@ConditionalOnProperty`.

Example:

```java
@Bean
@ConditionalOnProperty(name="feature.api.caching.enabled", havingValue="true")
public CachingService newCacheBean() { ... }
```

---

#### 6.3 Safe Secret Management: Env Vars, Vault, Cloud

- **Never** hard-code passwords, tokens, API keys into code or checked-in config files!
- Use:
    - OS env variables (e.g. `DB_PASSWORD`)
    - Secret stores: [HashiCorp Vault](https://spring.io/guides/gs/vault-config/), AWS Secrets Manager, Azure Key Vault,
      GCP Secret Manager
    - Spring Cloud Vault integration: reads secrets from Vault at runtime
    - For Docker/K8s: use `envFrom`, config maps, mounted secrets

Example Vault usage:

```yaml
spring:
  cloud:
    vault:
      uri: http://localhost:8200
      token: abc-123
      generic:
        enabled: true
        backend: secret
```

- Spring automatically resolves `@Value` fields from Vault at app startup.

---

#### 6.4 Spring Cloud Config for Enterprise/Cloud-native Apps

- **Spring Cloud Config** provides central, version-controlled config for many services/apps.
    - Config stored in Git, Vault, or database.
    - `Config Server` exposes config over HTTP; client apps read config at boot or at runtime refresh points (
      `/actuator/refresh`).
    - Allows config rollbacks, consistent deployment, and dynamic refresh.
- **Usage:**
    - Add `spring-cloud-config-client` to your service’s dependencies.
    - Set `spring.config.import=optional:configserver:http://config-server:8888` in `bootstrap.yml` (or modern pattern:
      `application.yml`)
    - Each service/application can have its own config or override shared config.

  See [Spring Cloud Config Docs](https://spring.io/projects/spring-cloud-config) for full setup.
- **Why use?**
    - Solves “manage config at scale” and “12 Factor App” problems.
    - Supports dynamic property refresh (no restarts needed in most cases).

**Pitfalls:**

- Watch for startup order/config refresh hazards in distributed systems.
- Sensitive values (passwords) must be reloaded securely; never store unencrypted secrets in Git.
- Monitor config changes with audit logs and version history.

---

#### 6.5 Cloud-native, Enterprise Patterns, and Troubleshooting

- Set up config for each microservice and profile in cloud environments (Azure, AWS, GCP) using Key
  Vault/Secrets/Parameter Store and Cloud Config
- Leverage container-native config: Docker secrets, Kubernetes config maps (mount as files/env vars)
- Use `@ConfigurationProperties` and records for type-safe, well-tested config
- Validate crucial configs at startup (fail fast if missing/invalid)
- For serverless/cloud functions: bind secrets/config at deploy time
- Be strategic about config reloads—control which props can reload at runtime (`@RefreshScope`)
- Extensive monitoring/auditing: track config drift, failed lookups, access failures
- Troubleshoot by enabling Spring’s config debug logs, querying `/actuator/env` and `/actuator/configprops` for current
  state

---

#### 6.6 Interview/Expert Knowledge Recap

- Explain the Spring Boot config loading order and how to override a prop in different layers
- Why are profiles useful and how do you handle dangerous dev/test configs in prod?
- Compare property, env, Vault, and Cloud Config approaches for secrets
- How do you safely rotate secrets in production?
- What’s the difference between `application.yml` and `bootstrap.yml`? (legacy vs modern pattern)
- When would you use Spring Cloud Config and why is it critical for distributed/cloud deployments?
- Troubleshooting: What to check if a property is not resolved/updated after deployment?

---

**Practice:**

- Build an app with three profiles (`dev`, `test`, `prod`), prove config separation and proper bean wiring with logging
- Store/dev/test a secret in Vault or OS env, confirm property resolution at runtime (never checked in)
- Set up Docker or Kubernetes deployment with config/env mounting, validate isolation
- For cloud apps: try dynamic config refresh via Spring Cloud Config and `/actuator/refresh`

---

**Recommended Reading & Modern Examples:**

- [Spring Profiles Docs](https://docs.spring.io/spring-boot/reference/features/profiles.html)
- [Vault Config Guide](https://spring.io/guides/gs/vault-config/)
- [Spring Cloud Config Docs](https://spring.io/projects/spring-cloud-config)
- [Enterprise Config Patterns](https://keyholesoftware.com/centralizing-configurations-with-spring-cloud-config/)
- [@ConfigurationProperties modern usage](https://medium.com/@mudassar.hakim/spring-boot-configuration-done-right-value-vs-configurationproperties-records-profiles-ed25fb24017b)

---

### 7. Transaction Management

Transaction management is crucial for ensuring data integrity and consistency in modern, concurrent, and distributed
Spring Boot applications. Spring Boot (and Spring Framework) makes it robust and mostly automatic—if you understand the
patterns!

---

#### 7.1. ACID Transactions (Theory)

- **Atomicity** – All steps in a transaction succeed or all fail (no partial commit)
- **Consistency** – Data moves from one valid state to another, preserving business rules
- **Isolation** – Concurrent transactions behave as if run sequentially (see levels below)
- **Durability** – Once committed, data changes survive crashes

**Spring context:**

- All DB write operations should be done inside a transaction
- Operations that update multiple resources/data stores require careful boundary setting

---

#### 7.2. Declarative vs Programmatic Transaction Management

- **Declarative:** Use `@Transactional` at class or method level

```java
@Service
public class BankService {
    @Transactional
    public void transfer(Long from, Long to, BigDecimal amount) { ... }
}
```

- **Programmatic:** Use `TransactionTemplate` and callbacks. Rare, for custom scenarios

---

#### 7.3. `@Transactional` Explained

- **Scope:** Can be applied to entire service class or a specific method
- **Visibility:** By default, only public methods are managed. Use with protected/package-private with care (from v6.0)

**Common attributes:**

- `propagation` – How this transaction interacts with other (nested) transactions
- `isolation` – How/when one transaction sees others’ uncommitted changes
- `rollbackFor`, `noRollbackFor` – Force rollback for certain exceptions, or prevent it
- `timeout` – Time before force rollback
- `readOnly` – Hints DB driver to optimize for non-mutating queries

---

#### 7.4. Transaction Propagation

Describes behavior if a transaction is already running:

- **REQUIRED** (default): Use current, or create new if missing
- **REQUIRES_NEW**: Suspend outer transaction, start and commit/rollback new one independently
- **SUPPORTS**: Join if present, else non-transactional
- **MANDATORY**: Fail if none present
- **NESTED**: Savepoints within main transaction (limited DB support)
- **NEVER**: Fail if any active transaction
- **NOT_SUPPORTED**: Suspend transaction, run without

Use Cases:

- Regular business ops: default is enough
- Audit log or notification inside main TX: REQUIRES_NEW
- Complex rollback nesting: NESTED

---

#### 7.5. Isolation Levels

How much “see through” between concurrent transactions:

- **DEFAULT**: DB-specific default (usually READ_COMMITTED)
- **READ_UNCOMMITTED**: See uncommitted changes (dirty reads)
- **READ_COMMITTED**: See only committed (common for OLTP)
- **REPEATABLE_READ**: Query same row/column within transaction: always same
- **SERIALIZABLE**: Strongest, no dirty/non-repeatable/phantom reads—lowest concurrency

**Configure on `@Transactional(isolation = Isolation.REPEATABLE_READ)`**

---

#### 7.6. Rollback and Commit Behavior

- By default, **rollback on RuntimeException and Error**, commit otherwise
- To rollback on checked exceptions:

```java
@Transactional(rollbackFor = CustomCheckedException.class)
public void doWork() { ... }
```

- **Partial rollbacks:**
    - Use REQUIRES_NEW or NESTED to rollback inner but not whole transaction
- Always test rollback with integration tests

---

#### 7.7. Advanced Topics

- **Self-invocation**: Transactional method calling another in same bean bypasses proxy—no transaction! Split logic
  across beans or use AOP-aware invoker
- **Proxy-based limitations:** Only public methods by default, override with caution
- **Timeouts and read-only:** Use to prevent long/blocking queries from hanging systems

```java
@Transactional(timeout = 10, readOnly = true)
public List<Foo> readData() { ... }
```

- **Multi-datasource/XA/distributed:**
    - Not enabled by default (slow, complex)
    - Use Atomikos/Bitronix if you need ACID across DBs/queues
    - Consider eventual consistency for microservices (saga, compensating transactions)

---

#### 7.8. Testing, Troubleshooting, and Tools

- Use `@Transactional` or `@Rollback` in JUnit tests to auto-clean DB after each test
- For debugging transactions, use latest IDE Spring debugging plugins
- Logging: Enable `org.springframework.transaction` to DEBUG for transaction logs
- Watch out for “no transaction found” or “unexpected rollback”
- Common root causes: visibility/proxying, calling transactional via `this`, data layer exceptions not mapped

---

#### 7.9. Interview Practice & Critical Exploring

- What’s the difference between propagation types? When would you use REQUIRES_NEW/NESTED?
- What’s the effect of isolation levels?
- How does @Transactional interact with checked/unchecked exceptions?
- How do you define transactional boundaries in microservices? (ACID vs eventual consistency)
- Why can self-invocation break transactional boundaries, and how to fix?
- What happens if a transaction timeout is hit, or DB connection is lost mid-TX?

---

**Practice:**

- Write integration tests to check rollback/commit/timeout, test all propagation types
- Try creating intentional deadlocks, verify correct isolation/rollback
- Break a transactional boundary by self-invocation—watch what happens, then fix it
- Try mixing class/method level @Transactional annotations—who wins?

---

**References:**

- [Spring Transactions Doc](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html)
- [Propagation/Isolation Demo](https://www.geeksforgeeks.org/advance-java/transaction-propagation-and-isolation-in-spring-transactional-annotation/)
- [Spring Boot Roadmap: Transactions](https://amigoscode.com/blogs/spring-boot-roadmap-2025)
- [Debugging Transactions (IDEA)](https://blog.jetbrains.com/idea/2025/08/debugging-transactions-let-spring-debugger-do-the-heavy-lifting/)

---

### 8. Localization and i18n

Internationalization (i18n) lets your app “speak” to users in their own language, region, or variant. Spring Boot
provides all the hooks to build truly global apps!

---

#### 8.1. What is i18n and Why Do It?

- **Use cases:**
  - Serve websites, APIs, or UIs for different languages/countries (en, es, fr, zh, etc.)
  - Regional formatting: dates, numbers, currencies
  - Accessibility and expansion: reach more users, adapt to new markets

---

#### 8.2. How i18n Works in Spring Boot

- Uses resource bundles (`messages.properties` files) for all translatable text
- Locale chosen based on:
  - HTTP `Accept-Language` header (browser default)
  - URL/query param/user preferences
  - Default fallback

---

#### 8.3. Basic Setup: messages.properties Structure

- Create `/src/main/resources/messages.properties` (default, English/text fallback)
- For each language, create corresponding files:
  - `messages_es.properties` (Spanish)
  - `messages_fr.properties` (French)
  - `messages_zh_CN.properties` (Chinese)
- Put all user-facing/UI/API text (error/info/welcome/button labels) as key-value pairs:

```properties
welcome=Hello, {0}! Welcome!
error.notfound=Resource not found.
error.validation=Validation failed for field {0}.
```

---

#### 8.4. Configuring MessageSource in Spring Boot

- Spring Boot auto-configures a `MessageSource` if you use the messages properties file, but you can customize:

```java
@Configuration
public class I18nConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:messages");
        ms.setDefaultEncoding("UTF-8");
        ms.setCacheSeconds(3600); // Reload messages every hour
        return ms;
    }
}
```

- By default, messages from `messages_{locale}.properties` are resolved automatically.

---

#### 8.5. Setting and Resolving Locale (LocaleResolver)

- **AcceptHeaderLocaleResolver**: Use the browser’s language header (default for APIs)
- **SessionLocaleResolver**: Store locale in HTTP session (user can switch)
- **CookieLocaleResolver**: Store preference in a client-side cookie
- **URL change:** Users can set their language with `?lang=fr`, `?locale=zh_CN`, etc.

Example:

```java
@Configuration
public class LocaleConfig {
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
```

- Now `?lang=es` in the URL will switch language for UI APIs that use sessions/cookies.

---

#### 8.6. Using i18n in Controllers, Services, and Views

- **Controllers/Services:** Inject `MessageSource` and resolve messages by key and locale.

```java
@Autowired MessageSource ms;
public String getWelcome(String username, Locale locale) {
    return ms.getMessage("welcome", new Object[]{username}, locale);
}
```

- **Thymeleaf/Freemarker templates:**
  - Use `${#messages.msgOrNull('my.key')}` in templates

---

#### 8.7. Localizing Validation and Error Messages

- Place validation error messages in `messages.properties`
- Example entity with validation:

```java
public class UserDTO {
    @NotBlank(message = "{user.name.notblank}")
    private String name;
}
```

- Add in properties:

```properties
user.name.notblank=Name must not be empty.
user.name.notblank_es=El nombre no puede estar vacío.
```

- Spring validator will auto-resolve based on current locale.

---

#### 8.8. Advanced: DB-backed or Dynamic i18n

- For large/enterprise apps, sometimes you want to store translations in a DB or pull them from a localization platform.
- Use custom `MessageSource` implementation or delegate to third-party libraries
  like [Lokalise](https://lokalise.com/integrations/spring-boot/)
  or [SimpleLocalize.io](https://simplelocalize.io/blog/posts/spring-boot-simple-internationalization/).
- Combine with cache for performance.

---

#### 8.9. Testing, Tips, and Troubleshooting

- **Testing:**
  - Write integration tests for endpoints/UI with different Accept-Language headers
  - Use REST clients (Postman) with manual language header
- **Pitfalls:**
  - Missing key: fallback to default, or exception if misspelled (always test all lang/message combos)
  - Hardcoded text: All user-facing strings must be in messages files
  - Poor cache config can cause messages not to reload

---

#### 8.10. Best Practices/Modern i18n Tips (2025)

- Use `UTF-8` everywhere—even in editors! Avoid non-UTF properties
- Group message keys by UI/page/feature for clarity and easier extraction
- Keep resource bundles under `/src/main/resources` for easy packaging and auto-reload in dev
- For dynamic or DB-driven apps, cache translations but make sure to support live update triggers
- Work with translators/QA to validate finished interface in every locale—never launch with “?”/English-only leftovers
- Parameterize templates: “welcome=Hi {0}!” is better than concatenation
- For mobile/SPA/REST APIs: design endpoints for easy language switching, let clients pick preferred language
- If using microservices, keep services stateless—the client sends language preference, or session/cookie for UI

---

#### 8.11. Interview Must-Knows & Practice

- What’s the role of `MessageSource`?
- How does Spring Boot resolve language for a request/API call?
- Where does validation error localization plug in?
- How do you test/verify i18n is correct and complete before shipping?
- Name a pitfall when externalizing i18n to a database? (latency, cache-consistency, dynamic reloads)

**Practice:**

- Add at least two languages to your app. Change browsers, use tools, and verify correct translations in all interfaces.
- Test validation, error, and system messages for all supported locales

---

**References & Further Study:**

- [Spring Boot i18n Step-by-Step](https://lokalise.com/blog/spring-boot-internationalization/)
- [Java i18n Full Practical Guide](https://phrase.com/blog/posts/java-i18n-guide/)
- [Baeldung: REST-Localized Messages](https://www.baeldung.com/rest-localized-validation-messages)
- [SimpleLocalize Spring Boot i18n](https://simplelocalize.io/blog/posts/spring-boot-simple-internationalization/)

---

### 9. Connection Pooling

Efficient database connectivity is critical for high-performance Spring Boot applications. Connection pooling is what
allows you to handle hundreds or thousands of concurrent requests reliably.

---

#### 9.1. What is Connection Pooling and Why Use It?

- **Concept**: Reuse a set of pre-established DB connections, rather than opening/closing a new connection for every
  request
- **Benefits**: Drastically increases performance, reduces DB server load, prevents connection exhaustion
- **Without pooling?** You hit resource limits and get slowdowns or "connection refused"/timeouts under moderate load

---

#### 9.2. HikariCP: Spring Boot’s Default Pool (2025+)

- Since Spring Boot 2.x, [HikariCP](https://github.com/brettwooldridge/HikariCP) is the default JDBC connection pool
- It’s the fastest, most reliable Java pool—lightweight and suitable for microservices and monoliths alike

**Minimal setup:** No config is required for it to work; but tuning is essential for real projects.

---

#### 9.3. Configuring HikariCP in Spring Boot

Add, in `application.properties` or `application.yml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/app
spring.datasource.username=myuser
spring.datasource.password=secret
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=60000 # 60 seconds
spring.datasource.hikari.max-lifetime=1800000 # 30 minutes
spring.datasource.hikari.connection-timeout=30000 # 30 seconds
spring.datasource.hikari.leak-detection-threshold=15000 # Warn if conn not returned in 15s
```

- **maximum-pool-size**: Max simultaneous connections (set smaller for dev, larger for prod)—typically set
  to [(number of CPU cores) * 2] or DB’s max conn minus some wiggle room
- **minimum-idle**: Idle conns to keep present
- **idle-timeout**: How long to keep idle conns before closing
- **max-lifetime**: How long to keep a conn in the pool before recreating (avoid stale conns)
- **connection-timeout**: How long to wait for a connection if the pool is exhausted
- **leak-detection-threshold**: Log a warning if a conn is checked out too long (possible bug/memory leak)

---

#### 9.4. Monitoring, Health Checks & Diagnostics

- Enable [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html):
  - `/actuator/health` shows DB & pool status
  - `/actuator/metrics/hikaricp.connections.active` for live pool stats (on by default in 3.x)
- GraalVM/Micrometer/OpenTelemetry can give deeper observability

You can also check HikariCP logs for warnings/errors on pool exhaustion, connection leaks, or lifetime expiries.

---

#### 9.5. Sizing and Production Hardening

- Use pool sizing guides:
  - Start with (CPU cores * 2) or multiples; never exceed DB’s real max
  - Benchmark with prod-like traffic/load testing, adjust up/down
- Health check queries: Use lightweight queries (`select 1`) to minimize delay
- Tune **timeouts** for your network; never use arbitrarily high values (otherwise threads can pile up)
- Set maxLifetime slightly smaller than DB’s connection timeout to avoid surprise disconnects

---

#### 9.6. Alternatives and Custom Pools

- You usually don’t need to swap HikariCP!
- If you must (e.g., using Oracle Universal Connection Pool, Apache DBCP, or legacy constraints):

```xml
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-dbcp2</artifactId>
</dependency>
```

- Use `spring.datasource.type` to select your pool class (see docs)

---

#### 9.7. Troubleshooting, Pitfalls, and Best Practices

- Watch for errors like `HikariPool-1 - Connection is not available, request timed out after 30000ms`—usually too small
  a pool or DB needs tuning
- "Apparent connection leak detected"? Use leakDetectionThreshold to debug, check for missing `.close()` in raw JDBC
- Pool size too large: Can overwhelm DB/server, cause slowdowns for *all* services
- Pool size too small: Pool exhaustion, slow response, queued threads
- Make sure all `@Repository` and service methods return connections, never block on long operations or network calls
  within a transaction!
- For distributed/cloud: Pool sizing must match scaled-in/out pod/instance count (in Kubernetes, set per pod)
- For cloud DBs: Set lower maximum-lifetime than the DB platform’s global connection max

---

#### 9.8. Hands-on & Diagnostic Practice

- Enable Actuator and monitor `/actuator/metrics/hikaricp.*`
- Intentionally exhaust pool (load test) and tune maximum-pool-size for stability
- Use leak detection (set threshold low in dev) to catch leaks before prod
- Test app scaling (multiple pods/instances): Monitor pooled connections don’t exceed DB pool or cloud provider limits

---

#### 9.9. 2025 Interview Must-Knows

- What is connection pooling and why do modern apps need it?
- Describe HikariCP’s main tuning options (and how/why you set them)
- How do you debug a connection leak or pool exhaustion?
- What’s the effect of setting maxLifetime too high or low?
- How does pool sizing relate to CPU count, DB limits, and distributed computing?
- When (if ever) would you need a custom pool over HikariCP?

---

**Best References & Dive Deeper**:

- [Spring Boot Hikari Guide](https://www.baeldung.com/spring-boot-hikari)
- [HikariCP Settings and Best Practices](https://medium.com/@AlexanderObregon/connection-pool-settings-that-matter-in-spring-boot-apps-4534f02d3d66)
- [Fine Tuning HikariCP Performance](https://javainspires.blogspot.com/2025/08/fine-tuning-hikaricp-performance-in.html)
- [Oracle DB HikariCP Best Practices](https://blogs.oracle.com/developers/post/hikaricp-best-practices-for-oracle-database-and-spring-boot)
- [Spring Boot Performance Analysis Example](https://github.com/pbelathur/spring-boot-performance-analysis)
- [Actuator Metrics Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints.metrics)

---

### 10. Security and Best Practices: REST and Microservices

Security is paramount for any production application—especially REST APIs and microservices. Modern Spring Security and
good architectural habits defend against most common vulnerabilities, compliance issues, and production outages.

---

#### 10.1. Security Fundamentals for REST APIs

- Always secure endpoints: never trust user input, always validate and sanitize.
- Use HTTPS everywhere in production (enforce via `server.ssl.*` and reverse proxies).
- Principle of least privilege: only allow as much access as each endpoint needs.
- Never expose stack traces, debug, or sensitive info in error responses.
- Secure dependencies: Track and patch CVEs (use `spring-boot-starter-security` and update quarterly or after every
  CVE).

---

#### 10.2. Modern Spring Security 6 Setup

- Add `spring-boot-starter-security`.
- Core config changes (2025):
  - Use component-based configuration (no more `WebSecurityConfigurerAdapter`).
  - Configure with a bean:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // For pure REST APIs only; keep enabled for browser/session apps
            .authorizeHttpRequests()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .httpBasic(); // Or use JWT auth as shown below
        return http.build();
    }
}
```

---

#### 10.3. Password Encoding, OAuth2 & JWT

- Never store passwords in plain text; use `PasswordEncoder` (e.g., BCrypt):

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

- Modern authentication options:
  - **JWT (JSON Web Token)** – stateless, scalable for APIs & microservices.
  - **OAuth2 (Spring Security 6+ supports resource server and client patterns):** Supports login with Google, GitHub,
    institutional/enterprise SSO.
- Example for JWT-based authentication:
  - Use `spring-boot-starter-oauth2-resource-server` and declare security settings.
  - Implement JWT parsing and validation with [
    `NimbusJwtDecoder`](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html).
  - [Full code example](https://medium.com/@sibinraziya/spring-boot-3-spring-security-6-jwt-authentication-and-authorization-e586bc186805).

---

#### 10.4. Role-based and Method-level Security

- Annotate controller/service methods:

```java
@PreAuthorize("hasRole('ADMIN')")
public List<User> getAllUsers() { ... }

@Secured("ROLE_USER")
public UserProfile getUser() { ... }
```

- Enable with `@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)` if not present by default (
  check your Spring Security version—most enabled in 2025 scaffolds).

---

#### 10.5. Protecting API Endpoints: CORS and CSRF

- CORS: Allow cross-origin requests *only* as needed. For REST APIs, define CORS policy in your `SecurityFilterChain` or
  in a bean:

```java
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("https://myfrontend.com"));
    configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", configuration);
    return source;
}
```

- CSRF: Usually **disabled for stateless REST APIs**, enabled for any session-based browser/web login flows.
- Always understand: CORS/CSRF are not substitutes for real access control—use both as part of multi-layer security.

---

#### 10.6. Secure Configuration: Parameterized Queries, Secrets, and Logging

- Never concatenate user input into SQL queries—always use Spring Data repositories or parameterized queries (`@Query`).
- Don’t log sensitive data (passwords, tokens, API keys, JWTs).
- Store secrets in env variables, HashiCorp Vault, Azure Key Vault, AWS Secret Manager, etc.—never in source or
  committed config.
- Consider using Spring Cloud Config/Vault as described earlier for API keys and credentials.

---

#### 10.7. API Rate Limiting and Abuse Protection

- Use libraries like **Resilience4j** or **Bucket4j**:
  - Block or slow down excessive requests by IP/token/user.
  - Plug in Redis, in-memory, or cloud-based buckets.

```java
@RateLimiter(name = "rateApi")
@GetMapping("/api/v1/data")
public ResponseEntity<Data> fetchData() { ... }
```

    - [Bucket4j Spring Boot Example](https://www.baeldung.com/spring-bucket4j)

- Always return HTTP 429 (Too Many Requests) for throttled clients.

---

#### 10.8. Secure Error Handling and Logging

- Use `@ControllerAdvice` and exception handlers to:
  - Return standardized error responses; avoid exposing internal classes, stack traces, or queries in errors.
  - Log exception details server-side for analysis, but return **generic** messages to users.
  - Mask sensitive info in logs (passwords, JWTs, internal IDs).

```java
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handle(Exception ex) {
        log.error("Exception!", ex);
        return ResponseEntity.status(500).body(new ApiError("Something went wrong."));
    }
}
```

---

#### 10.9. Secure Microservices Patterns (2025)

- Use an **API Gateway** (Spring Cloud Gateway or NGINX) for external traffic, delegating authentication and global
  filters.
- Secure intra-service traffic with mTLS or signed JWTs.
- Use separate credentials for each service/microservice.
- Consider “Zero Trust” network architecture: never assume trust between services, always authenticate/authorize via
  JWT/OAuth2 even inside your cluster.
- Always validate tokens, expiration, audience, and scopes in resource servers.
- For distributed tracing, ensure all security- and user-context headers propagate correctly.

---

#### 10.10. Must-Knows/Interview and Practice Questions

- How does Spring Security filter chain work, and how do you customize or debug it?
- How do you securely handle user auth and password storage? Why is BCrypt preferred?
- Difference between stateless vs session security, and implications for REST/microservices?
- How do you implement, tune, and test API rate limiting?
- When/how do CORS/CSRF protect your app—and when can they be safely disabled?
- How do you isolate secrets/config between dev, test, and prod? How do you rotate secrets?
- What happens if a user’s JWT is stolen? How do you invalidate or handle that scenario?
- How do you avoid leaking exception stack traces or details to attackers/clients?

---

**Hands-on & Further Exploration:**

- Build secure signup/login endpoints with BCrypt, JWT, CORS, and global exception handler.
- Write and tune a rate limiter per-IP or per-user; test with different loads.
- Try breaking your app via invalid JWTs, over-limit requests, or CORS/CSRF exploits—and fix all holes and error leaks.
- Securely log and mask sensitive errors in a production-logging simulator.

---

**Modern References and Practice Links**:

- [Spring Security Best Practices 2025](https://medium.com/@shahharsh172/spring-boot-security-best-practices-a-developers-complete-guide-e91c49dfd5d3)
- [JWT Auth Example for Spring Security 6](https://medium.com/@sibinraziya/spring-boot-3-spring-security-6-jwt-authentication-and-authorization-e586bc186805)
- [Bucket4j Rate Limiting](https://www.baeldung.com/spring-bucket4j)
- [Spring Security Docs (6+)](https://docs.spring.io/spring-security/reference/)
- [Exception Handling for APIs (2025)](https://medium.com/@sunil17bbmp/exception-handling-in-spring-boot-2025-best-practices-and-complete-guide-9bb21c98ba08)

---

### 11. Microservices With Spring Boot

Microservices let you break up complex, slow-moving monoliths into independently deployable, scalable, and focused
services. Spring Boot—with Spring Cloud—makes microservices practical, robust, and cloud-native in 2025.

---

#### 11.1. What Are Microservices? Monolith vs Microservice

- **Monolith:** One large codebase/app, single DB, all logic in one deployable unit.
- **Microservices:** Independent, focused services—each owns its data/resources, communicates via APIs/events, is
  deployable/testable/upgradable in isolation.
- **Key benefits:** Agility, scalability, independent deploys, fault isolation.
- **Trade-offs:** Initial complexity, distributed debugging, cross-cutting concerns (security, data consistency, etc.)

---

#### 11.2. Design Principles and Patterns

- **Smart endpoints, dumb pipes:** Business logic in services, minimal coupling in transport.
- **Domain-Driven Design (DDD):** Boundaries based on real business subdomains.
- **APIs as contracts:** Stable, versioned interfaces; evolve safely without breaking clients.
- **Database per service:** Each service owns its schemas; only communicate via APIs/messages.
- **Resiliency and Observability:** Every microservice must be independently recoverable and diagnosable.

---

#### 11.3. REST API Design for Microservices

- Use [Spring Boot REST practices](#building-restful-web-applications): DTOs, versioning, HAL/HATEOAS, pagination, error
  handling.
- Clearly document APIs (OpenAPI/Swagger).
- Use correlation IDs for tracing (pass as header through all requests).

---

#### 11.4. Service Discovery (Eureka, Consul, Kubernetes)

- In microservices, separating where a service *lives* (IP/port) from *how to call it* is essential for auto-scaling.

**Popular options:**

- **Spring Cloud Netflix Eureka**: Java/Spring-centric, easy to get
  started ([Guide](https://spring.io/guides/gs/service-registration-and-discovery))
- **Consul**: Polyglot, supports Spring Boot via [Spring Cloud Consul](https://spring.io/projects/spring-cloud-consul)
- **Kubernetes**: Built-in via service DNS/endpoints

**Setup (Eureka):**

- Add `spring-cloud-starter-netflix-eureka-server` to your registry, and `spring-cloud-starter-netflix-eureka-client` to
  service(s)
- Register services with Eureka on startup (via config)
- Services find each other by name

---

#### 11.5. API Gateway (Spring Cloud Gateway, NGINX)

- **Purpose:** Central entry point for clients, hides service topology, does authentication, SSL termination, routing,
  rate limiting, and API aggregation.
- **Spring Cloud Gateway** is the modern, actively developed cloud-native choice for Java
  apps ([Guide](https://blog.bitsrc.io/how-to-build-an-api-gateway-with-spring-cloud-gateway-and-eureka-the-beginners-guide-0985f0c42527)).
- Can enforce global security, header/cookie policy, propagates correlation IDs, and more.

---

#### 11.6. Load Balancing & Client-Side Discovery

- Spring Cloud LoadBalancer auto-integrates with service registries (Eureka, Consul).
- In cloud/K8s, service name resolves to a local endpoint with built-in platform load balancer.
- Options: round-robin, random, response-time-weighted

---

#### 11.7. Circuit Breaker, Retry, Rate Limiting (Resilience4j)

- To withstand downstream outages, network flakiness, and abuse:
  - **Circuit Breaker:** Trips after repeated errors, resets after cooldown; prevents "cascading failure".
  - **Retry:** For transient errors, may auto-replay calls a finite number of times.
  - **Bulkhead:** Isolates failures so one overloaded endpoint doesn't crash the whole service.
  - **Rate Limiter:** (See security!)

**With Resilience4j:**

```java
@CircuitBreaker(name = "fooService", fallbackMethod = "fallbackFoo")
public Bar callFooService() { ... }
```

- Actuator exposes `/actuator/circuitbreakers` and `/metrics/resilience4j.*` for monitoring.

---

#### 11.8. Distributed Tracing & Logging

- Use [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth) or [Micrometer](https://micrometer.io/) for
  distributed trace IDs (logs show flows across services)
- Connect tracing to Zipkin or Jaeger for visual/cross-service tracing
- Use correlation headers in all HTTP/gRPC calls
- Centralize logs via ELK/Grafana/Cloud-native tools (Stackdriver/Azure Monitor)

---

#### 11.9. Centralized Config (Spring Cloud Config, Vault)

- Use central config server (shared or profile/env by service)
- Rotate passwords/secrets via cloud store or Vault
- Use `/actuator/refresh` or redeploy services to reload config
- Document config key ownership—avoid ambiguity in overriding!

---

#### 11.10. Securing Microservices

- See Security section: always use TLS everywhere in prod, even internal APIs! Use mutual TLS when possible for
  intra-service auth.
- JWT/OAuth2: AuthN/AuthZ via OpenID Connect, central Auth Server
- Protect each service as if exposed to the world—never trust "internal only" services (Zero Trust)
- Use short-lived tokens, frequent key rotation
- Segregate dev, test, prod credentials
- Run security/vulnerability scans as part of CI/CD

---

#### 11.11. Deploying Microservices (Docker, Kubernetes, CI/CD)

- Containerization is standard: package each service as Docker image (see Spring Boot plugin for easy builds)
- Kubernetes/YAML for production cloud: define resource usage, readiness/liveness probes, auto-scaling
- Prefer cloud-native managed DBs, caches, pubsub where possible (reduce ops work)
- Automate deployments with CI/CD (GitHub Actions, GitLab, Jenkins, etc.)
- Use health checks, rolling updates, canary or blue/green deploys for zero-downtime releases

---

#### 11.12. Interview/Practice & Debugging

- Explain the pros/cons of microservices vs monoliths; when should you choose each?
- Show how service registration/discovery works in Spring Boot (Eureka, Consul, K8s)
- How does API Gateway help with consolidation, auth, and throttling?
- What’s the circuit breaker/retry pattern?
- How do you debug a distributed microservice issue (tracing/correlation, logs, and metrics)?
- How do you segregate configs, secrets, and data per environment?
- Practice: Build a mini-system with 2-3 services, each with own DB, API, and config. Secure, trace, and deploy via
  Docker Compose or K8s.

---

**Must-Read References/Guides**:

- [Spring.io Microservices Portal](https://spring.io/microservices)
- [Service Discovery Guide](https://spring.io/guides/gs/service-registration-and-discovery)
- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
- [Mastering Circuit Breaker/Resilience4j](https://medium.com/@smita.s.kothari/mastering-the-circuit-breaker-pattern-in-microservices-with-spring-boot-and-resilience4j-5df944297c48)
- [Distributed Tracing/Sleuth](https://spring.io/projects/spring-cloud-sleuth)
- [Java Microservices Architecture 2025 (Practical)](https://medium.com/@shahharsh172/java-microservices-architecture-guide-spring-boot-best-practices-for-production-2025-9aa5c287248f)

---

### 12. Project Ideas & Hands-On Practices

- Mini e-commerce backend
- User management system w/ JWT security
- Blog platform (CRUD, i18n, pagination)
- Integration with third-party APIs (social login, payment)
- Real-time chat with WebSocket
- Microservice: Order & Inventory system
- Cloud deployment project
- Database migration & rollback example

### 13. Recommended Tools & Resources (2025)

The right ecosystem tools can greatly accelerate your Spring Boot learning, development, and deployment workflow. Here’s
what you should know, use, and regularly explore in 2025:

---

#### 13.1. Essential IDEs & Plugins

- **IntelliJ IDEA (Community/Ultimate):** Best-in-class Spring & JPA support: live templates, auto-complete, navigation,
  Spring diagrams. *Must-have for serious Spring work.*
- **Eclipse + Spring Tools Suite (STS):** Free, great for beginners and enterprise developers. Spring support baked in.
- **Visual Studio Code**: Lightweight with Spring Extension Pack, good for beginner/intermediate, containers, and cloud
  projects.
- **Key plugins:**
  - Lombok plugin (auto-generates boilerplate code)
  - Checkstyle/SonarLint (code quality and static analysis)
  - Docker/Kubernetes plugins (DevContainers, microservices)
  - .editorconfig (enforce code formatting)

---

#### 13.2. API & Documentation Tools

- **Springdoc-openapi:** Automatically generates OpenAPI (Swagger) docs for your APIs. Exposes `/swagger-ui.html` for
  easy testing and sharing with frontend/devops.
- **Postman/Insomnia/HTTPie:** Manual testing for REST APIs. Good for debugging, regression, and exploratory testing of
  endpoints.
- **REST Assured:** For automated, code-driven API testing in integration tests.

---

#### 13.3. Database & JPA/ORM Tools

- **DBeaver, DataGrip:** IDE-agnostic GUI database clients for Postgres, MySQL, H2, etc. Great for schema/data
  inspection and ad-hoc queries.
- **Testcontainers:** Run real DBs (Postgres, MySQL, Redis, etc.) in dockerized containers for testing.
- **JPA Buddy plugin:** Generate and inspect entity diagrams, manage repositories, and optimize mappings.
- **Flyway/Liquibase:** Zero-downtime schema migration/versioning; essential for production-grade deployments.
- **Hibernate Profiler/Statistics:** Advanced query/debugging for performance tuning and detecting N+1/select issues.

---

#### 13.4. Observability, Monitoring, and Tracing

- **Spring Boot Actuator:** Adds REST endpoints for health, metrics, info, and operational debugging. Enable
  `/actuator/*` endpoints for deep diagnostics.
- **Micrometer:** Unified metrics facade; supports exporting to Prometheus, Grafana, Datadog, New Relic, and cloud
  providers.
- **Spring Cloud Sleuth:** Distributed tracing and correlation IDs for logs; plug into Zipkin/Jaeger for visual tracing.
- **ELK/EFK Stack (Elasticsearch, Logstash/Fluentd, Kibana):** Best for centralizing logs in production. Many
  cloud-native alternatives if using AWS, Azure, GCP.
- **Grafana Dashboards:** Visualize live metrics and alerts.

---

#### 13.5. Security and Secrets Management

- **Spring Security (starter, docs, community):** All authentication and authorization needs.
- **Spring Cloud Config + Vault:** Centralized, versioned config and secrets for microservices and cloud.
- **OWASP Dependency-Check plugin:** Automatically scans Maven/Gradle dependencies for CVEs.

---

#### 13.6. Cloud, Container, and Deployment Tools

- **Docker Desktop:** For local containers, Compose, and CI/CD simulation. Essential for modern microservice dev.
- **Kubernetes (kubectl, tools):** For managing, scaling, and troubleshooting cloud-native apps.
- **Spring Boot Maven & Gradle Plugins:** Build runnable JARs/images; advanced integration for cloud vendors.
- **Helm:** Package, configure, and deploy K8s applications simply.
- **GitHub Actions, GitLab CI/CD, Jenkins, etc.:** Automate testing, builds, security scanning, and deployment.
- **Platform/Cloud SDKs:** (Azure CLI, gcloud, AWS CLI) for smooth cloud integration/automation.

---

#### 13.7. Testing Ecosystem

- **JUnit 5:** Core for all unit/integration testing in Java world.
- **Mockito/MockK:** Modern, story-style mocking for isolated unit tests.
- **AssertJ/Hamcrest:** For expressive, readable test assertions.
- **Spring Test:** For easy @WebMvcTest, @DataJpaTest, @SpringBootTest with transactional rollbacks.

---

#### 13.8. Communities, Knowledge & Reference

- **Official Docs:**
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Spring Framework](https://spring.io/projects/spring-framework)
  - [Spring Cloud](https://spring.io/projects/spring-cloud)
- **Online Communities:**
  - [Stack Overflow - spring-boot tag](https://stackoverflow.com/questions/tagged/spring-boot)
  - [Reddit r/SpringBoot](https://www.reddit.com/r/SpringBoot/)
  - [Spring Blog & YouTube](https://www.youtube.com/@SpringDevelopers)
  - [Awesome Spring Boot List](https://github.com/ityouknow/awesome-spring-boot) (curated useful libs and articles)
- **Top Learning Sites for 2025:**
  - Amigoscode, JavaBrains, Spring Academy, Baeldung, GeeksForGeeks
  - Udemy, Coursera, Pluralsight: Updated video courses and full paths

---

#### 13.9. Additional Must-have Tools for 2025

- **Resilience4j & Bucket4j:** For API rate limiting/circuit breaking
- **Jacoco:** Code coverage tool plugin
- **SonarQube:** Code quality, static analysis, SAST
- **Swagger Editor / UI:** Rapid API doc/test prototyping
- **LocalStack:** Simulate AWS, S3, SQS, etc. for testing integrations
- **WireMock/MockServer:** Mock external services for end-to-end testing

---

**Practice Tip for Mastery:**

- Try integrating 2-3 new tools on every project (e.g. switch from Postman → Rest Assured + Testcontainers, or from
  local logs → cloud metrics). Every new project cycle, expand your stack with a new piece until you are fluent and
  ready for any team or context.

---
