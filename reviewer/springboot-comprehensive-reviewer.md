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

#### What is Spring Boot? (2025 Edition)

Spring Boot 3.5+ is the modern, leading framework for building production-grade, enterprise Java applications. Created
by Pivotal (now VMware Tanzu/Broadcom), it delivers rapid development and operational maturity, synthesizing the best of
the Spring ecosystem for cloud-first, container-native, and microservices architectures.

**Key Baseline (2025):**

- **Requires:** Java 21+ (LTS)
- **Enterprise APIs:** Full migration to **Jakarta EE 10/11** (no javax.*, now jakarta.*)
- **Spring Boot 3.5** is the latest stable, last 3.x—focusing on cloud-native, modern concurrency, and operational
  excellence.

**Main Value Proposition:**

- **Convention-over-configuration:** Sensible defaults, industry patterns, and auto-configuration means less “yak
  shaving”, more business velocity.
- **Embedded servers:** (Tomcat, Jetty, Undertow 2025) out-of-the-box, optimized for Docker/Kubernetes.
- **Production ready at install:** Actuator, metrics, health, and security all included & ready for enterprise ops from
  the start.
- **Cloud-native focus:** Out-of-the-box compatibility with container platforms, Kubernetes, cloud config, and modern
  deployment pipelines (Docker image builds, SBOM, etc).
- **Zero-trust, industry-grade security:** Deep integration with Spring Security 6+ and OAuth2/JWT flows as first-class
  patterns.

---

#### Spring Boot Evolution and Why It Matters Now (2025)

- **Boot 1.x–2.x:** Reduced boilerplate, popularized embedded servers and starter-deps.
- **Boot 3.x (2022–2025):**
  - **Java 17+ baseline**; Java 21+ as the de facto minimum for new projects
  - **Full Jakarta EE packages:** (breaking, but future-proof)
  - **Reactive, native, and cloud-native compatibility**
  - **Virtual threads, modularity, and native executables**
  - **Growing integration with Kubernetes, distributed tracing, config layering
  - **Release cadence:** Fast minor, ~1 major per 12–18 months; always target latest 3.x for new enterprise builds

---

#### Enterprise-Ready Concurrency: Project Loom & Virtual Threads

- **Spring Boot 3.2+ fully supports Java 21 virtual threads.**
  - Enables ultra-light, blocking-friendly, highly concurrent web APIs and backends
  - **How to enable:**
    ```properties
    spring.threads.virtual.enabled=true
    ```
  - **Best For:** Heavy IO, REST APIs, web apps—huge scalability at JVM efficiency
  - **Gotchas:** Less benefit for CPU-bound or legacy JDBC (check pinning)
  - **2025 Default:** Most new enterprise APIs should prefer virtual threads (Loom) unless deep, known reason otherwise

---

#### GraalVM Native Images & Project CRaC: Startup/Mem Game-Changers

- **GraalVM native image:** Compile Spring Boot to a small, blazing-fast binary. Huge win for serverless, cloud.
  - **Command:**
    ```
    ./mvnw -Pnative native:compile
    ```
  - **Benefit:** ~50ms startup, ~80% lower memory for most services; AOT safe; fits K8s/serverless
  - **Limitations:** Some frameworks/extensions may require special config/hints
- **CRaC (Coordinated Restore at Checkpoint):** For JVM-mode apps, pause & snapshot warm state, restore instantly (esp.
  for "scale to zero"). Linux only.
- **When to use each:** Native image for lowest mem/colocated cloud, CRaC for advanced JVM workloads needing near-zero
  cold-start but JIT power.

---

#### Modern Observability, Tracing & Structured Logging

- **Actuator, Micrometer, OpenTelemetry**: Standard for all cloud/ops metrics and traces
  - Default endpoints: `/actuator/health`, `/actuator/metrics`, `/actuator/traces`, etc.
  - Built-in structured logging: output in JSON, ECS, etc., suitable for ELK/Prometheus/Grafana
    ```properties
    logging.structured.format.console=ecs
    ```
  - **Trace IDs**: Auto added to logs/HTTP responses (`X-Trace-Id`)
  - Exporters: Prometheus, OTLP, others out-of-the-box
- **2025 Practice:** Apps should implement zero-config distributed tracing, expose liveness/readiness for K8s, and
  enable structured logs for AIOps/data lakes

---

#### Configuration Advances: Layering, Profiles, Cloud-Native Patterns

- **Env Var & JSON Loading:** Overlays, deep config from environment vars or `SPRING_APPLICATION_JSON`
- **Profile and Cloud Config:** Per-environment property files (`application-prod.yml`), and centralized config using
  Spring Cloud Config
- **2025 Pattern:** Secure secrets via env/config server; document overlays & order of precedence
- **SBOM:** (Software Bill of Materials) automatically produced for transparency, security

---

#### Fully Jakarta EE: Modern APIs and Migration

- **Migration:** app code, APIs, and libraries now _require_ `jakarta.*` imports and Jakarta EE 10/11 capabilities
- **Consequence:** Legacy Java EE (javax) not supported. All persistence, REST, validation, etc., now Jakarta APIs.
- **Upgrade path:** Use Spring Boot and library migration guides for 2.x to 3.5+.

---

#### Operational Excellence: K8s-Ready, DevOps-First

- **Cloud-native core:** Embedded servers optimized for container platforms (Tomcat/Jetty/Undertow)
- **Readiness/liveness probes:** `/actuator/health/liveness`, `/actuator/health/readiness` endpoints natively exportable
  to K8s
- **Secrets & config:** support Docker secrets, config maps, Vault, etc.
- **Native Docker builds:** via Buildpacks and plugins, official multi-arch support
- **Testcontainers:** First-class support for true production-like testing using Docker
- **SBOM, supply chain:** Secure, auditable dependencies out-of-the-box

---

#### Modular Monoliths, Microservices, and Modern Architectures

- **Prefer modular monolith for simple domains; microservices for scale/independent deploys.** Both can be built with
  Boot/K8s if engineered with clear boundaries.
- **Project, package, and module structure:** strongly encouraged to use feature/domain-driven design (not strict
  layering)
- **2025 Best Practices:**
  - Modular codebase, domain/feature packaging
  - Strong separation of concerns, documented public interfaces
  - Container or K8s-first operationalization

---

#### Mastery/Interview Notes (2025)

- Why Java 21+ and Jakarta EE 10/11 are now required? What are the most common migration pitfalls/trade-offs?
- Virtual threads (Loom): How are they different from regular threads, and how do you enable/configure them in Spring
  Boot?
- Native images vs CRaC vs JIT: When do you use each, and what are the practical enterprise implications?
- How does Spring Boot structure support true cloud-native ops (actuator, health, structured logging, SBOM, Docker/K8s
  tests)?
- What’s new in metrics, distributed tracing, and logging (Micrometer, OTel, zero-config)?
- What are the core differences between modular monolith vs microservice in 2025 Boot, and how do you architect both?
- Security: How does Boot 3.5+ enable Zero Trust, OAuth2/JWT, and role- or attribute-based controls by default?
- Configuration layering: What is the order of precedence, and how do you handle multi-layered cloud config?

---

#### Further Reading / Practice (2025)

- [Spring Boot 3.5 Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Release Notes & Migration Guides](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.5-Release-Notes)
- [Spring Boot Docker/K8s Reference](https://spring.io/guides/gs/spring-boot-docker)
- [Observability with Micrometer and OTel](https://micrometer.io/)
- [K8s Health Probes](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints.health)
- [Virtual threads/project Loom](https://openjdk.org/projects/loom/)
- [GraalVM Native Image](https://www.graalvm.org/reference-manual/native-image/)
- [Structured Logging Formats](https://www.elastic.co/guide/en/ecs/current/ecs-reference.html)
- [Modern Testing: Testcontainers](https://www.testcontainers.com/)

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

### 3. Inversion of Control (IoC) in Spring Boot

#### What is Inversion of Control (IoC)?

- **Inversion of Control (IoC)** is a design principle at the heart of all modern Spring Boot development. IoC flips the
  traditional idea of "I make all the objects I need myself" on its head—instead, you let the framework create, manage,
  configure, and inject objects (“beans”) into your code.
- Instead of your app’s classes being in control of how dependencies are constructed or configured, **you give that
  control to the "Spring IoC container."**
- You describe what you want (using annotations like `@Component`, `@Service`, `@Repository`, etc.), and Spring creates
  the instances, sets up their dependencies, manages their lifecycle, and injects them where needed.

#### Why is IoC central to Spring Boot?

- IoC enables loose coupling, modularity, and easy testability. Code can be written and tested in isolation, with
  dependencies provided automatically by Spring—rather than "hardwired" with `new SomeService()`.
- **Everything in Spring Boot—automation, configuration, web endpoints, beans, scheduling, AOP, security—relies on the
  IoC container managing object lifecycles.**

#### IoC Container: What does it do?

- The IoC container (in Spring, it's most often `ApplicationContext`) is the brain that:
  - Discovers and creates all objects ("beans") annotated for management
  - Resolves what depends on what (reading constructor args, `@Autowired`, etc.)
  - Wires up those dependencies automatically (that’s dependency injection)
  - Manages bean lifecycle (creation, injection, initialization, destruction)
  - Applies cross-cutting concerns (AOP, proxies, etc.)
  - Makes beans available for injection anywhere in the context

#### IoC in Practice (2025):

- **You almost never write or see XML for beans.**
- Use annotations: `@Service`, `@Component`, etc. mark beans to be managed by Spring.
- Use constructor injection (recommended), or field/setter injection for dependencies.
- All beans are created and injected at startup, unless defined as lazy/prototype.

Example:

```java
@Service
public class TicketService {
    private final NotificationService notification;
    public TicketService(NotificationService notification) {
        this.notification = notification;
    }
    // ...
}
```

Here, you do NOT create or "new" the NotificationService anywhere; Spring finds it, makes it, and provides it.

#### How does Dependency Injection relate to IoC?

- **Dependency Injection (DI)** is the technique Spring uses to achieve IoC: you tell Spring what you need, Spring
  injects it for you.
- DI is a practical strategy—IoC is the overall philosophy that "the framework controls the app context, not my code."

#### Why does it matter?

- With IoC, you:
  - decouple objects for reuse
  - make testing simple (swap in mocks, test doubles)
  - centralize object graph config and lifecycles
  - gain all the power of container-level infrastructure (wiring, AOP, proxies, lifecycle hooks, etc)

**Mental model:**
> Think “My code describes what it needs; Spring figures out how and when it gets constructed and supplies it
> automatically. I only write business logic.”

---

### 3.1. Spring Beans: The Core of Dependency Injection

---

### 3.1. Spring Beans: The Core of Dependency Injection

#### What is a Spring Bean?

A **Spring bean** is any object that is managed by the Spring container (ApplicationContext). Beans are the building
blocks of every Spring and Spring Boot application—everything from controllers, services, repositories, to
infrastructure objects (like ObjectMapper, DataSource, etc) are beans. A bean's creation, lifecycle, wiring, and
destruction are all managed by the container.

#### How Are Beans Created?

There are several ways to define beans in a modern (2025) application:

- **Annotation-Based (most common):**
  - `@Component`: Generic bean
  - `@Service`: Business logic/service
  - `@Repository`: Data access/persistence/DAO (adds persistence exception translation)
  - `@Controller`/`@RestController`: MVC/Web/REST endpoints
  - `@Configuration` + `@Bean`: Programmatically registered beans
- **XML-Based (rare, legacy):** Manually in `beans.xml` files
- **Spring Boot Auto-Configuration:** Adds hundreds of beans based on classpath, properties, and conventions

Example:

```java
@Service
public class EmployeeService { ... } // This is a bean managed by Spring

@Configuration
public class InfraConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

#### Core Spring Bean Annotations

- `@Component` – Most generic; tells Spring to scan and manage as a bean
- `@Service` – For services/business logic (semantically indicates intention)
- `@Repository` – For DAOs (adds persistence exception translation)
- `@Controller` / `@RestController` – For web/endpoints
- `@Configuration` – For classes that create beans via explicit `@Bean` methods
- `@Bean` – To register an arbitrary bean by method

#### Dependency Injection (DI)

Spring DI is the mechanism that injects beans wherever they're needed. There are 3 primary types:

- **Constructor injection (recommended/best):**
  ```java
  @Service
  public class OrderService {
      private final PaymentGateway gateway;
      public OrderService(PaymentGateway gateway) {
          this.gateway = gateway;
      }
  }
  ```
  - Most robust, testable, and immutable.

- **Field injection (not recommended for new code):**
  ```java
  @Autowired
  private OrderRepository repository;
  ```

- **Setter injection:**
  ```java
  private ShipmentService shipmentService;
  @Autowired
  public void setShipmentService(ShipmentService shipmentService) {
      this.shipmentService = shipmentService;
  }
  ```

Spring automatically wires beans by type (and optionally by qualifier), resolving dependencies between beans.

#### Bean Scopes

Defines bean lifecycle and sharing semantics:

- **singleton** (default): One bean instance per app context
- **prototype**: New instance for every injection
- **request** (web apps): One per HTTP request
- **session** (web apps): One per HTTP session
- **application, websocket**: Other advanced/rare scopes

Example:

```java
@Component
@Scope("prototype")
public class Invoice {} // New bean created each time
```

---

#### Bean Lifecycle (2025 Enterprise Standard)

The lifecycle of a Spring bean is a well-defined sequence of phases, governed by the IoC container. Understanding this
lifecycle is crucial for resource management, robustness, and maintainability.

**Bean Lifecycle Phases (2025):**

1. **Definition/Scanning**: The container scans for annotated bean definitions (e.g. `@Component`, `@Service`, `@Bean`)
   and loads bean configurations.
2. **Instantiation**: The container instantiates the bean class—via constructor, static factory, or provided method.
3. **Dependency Injection (Wiring)**: Dependencies are injected into the bean (via constructor, setter, or field
   injection). Property values and references resolved.
4. **Aware Interfaces (Optional)**: If a bean implements `BeanNameAware`, `BeanFactoryAware`, `ApplicationContextAware`,
   or other `*Aware` interfaces, the container calls the callback(s) to provide context or meta-data.
5. **BeanPostProcessor/BeanFactoryPostProcessor Hooks**: Registered post-processors allow programmatic modification of
   beans before and after initialization (most AOP, proxies, and advanced instrumentation happen here).
6. **Initialization**:
  - **@PostConstruct**: Methods annotated with `@PostConstruct` (from Jakarta EE) are executed after dependencies are
    injected, before the bean is in use. NOTE: In 2025, prefer Spring-native mechanisms for new code if possible;
    `@PostConstruct` is only available via `jakarta.annotation`.
  - **InitializingBean.afterPropertiesSet()**: If implemented, runs after `@PostConstruct`.
  - **Custom init-method**: If defined via `@Bean(initMethod = "...")` or in XML, called after prior steps.
  - **@EventListener**: For advanced, staged, or async initialization (e.g.
    `@EventListener(ApplicationReadyEvent.class)`), use to trigger late-wiring or resource setup after entire context is
    up and running.
7. **Bean Ready/In Use**: The bean is fully initialized and available for injection/use throughout the app context.

**Destruction/Shutdown:**

8. **@PreDestroy**: If present, runs before bean is destroyed (context shutdown, scope end).
9. **DisposableBean.destroy()**: If implemented, runs after `@PreDestroy`.
10. **Custom destroy-method**: If defined in bean config or `@Bean(destroyMethod = "...")`, runs last.
11. **Lifecycle/SmartLifecycle**: For advanced containers (batch, integration), beans that implement these can
    participate in coordinated, phased application shutdown (with ordered startup/shutdown).
12. **BeanPostProcessor/BeanFactoryPostProcessor before destruction**: Final hooks for any cleanup or teardown logic.

**Modern/Enterprise Patterns for 2025:**

- **Favor `@EventListener` for staged or async initialization** (e.g. waiting for DB connection or full context):
  enables cleaner split of late setup versus core dependency injection.
- **Use `@PreDestroy` for resource cleanup** in typical app code; implement `DisposableBean/SmartLifecycle` only for
  infrastructure or advanced orchestration.
- **Avoid direct use of InitializingBean/DisposableBean in application code**—prefer annotations or
  `@Bean(initMethod/destroyMethod)` for decoupling.
- **Jakarta EE 10+**: All `@PostConstruct`/`@PreDestroy` should be imported from `jakarta.annotation.*` (not `javax.*`).
- **Avoid heavy blocking logic in lifecycle methods**; prefer scheduling or async event handling for startup tasks.
- **Leverage BeanPostProcessor only for framework/infra extensions.**
- **Always register a JVM shutdown hook in non-web apps for graceful bean cleanup**.

**Enterprise Lifecycle Example:**

```java
@Component
public class ResourceCache implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() { /* safe init */ }
    @PreDestroy
    public void cleanup() { /* release resources */ }
    @Override
    public void destroy() { /* final cleanup, rarely needed */ }
    @EventListener(ApplicationReadyEvent.class)
    public void lateInit() { /* post-startup alignment */ }
}
```

**References/Additional Reading:**

- [Spring Framework Docs - Lifecycle Callbacks](https://docs.spring.io/spring-framework/reference/core/beans/factory-nature.html)
- [Spring Boot Bean Lifecycle With Best Practices (2025+)](https://medium.com/@ayoubseddiki132/deep-dive-into-spring-boot-3-bean-lifecycle-modern-approaches-and-best-practices-fcc46d7f87c2)
- [Jakarta EE 10 @PostConstruct/@PreDestroy](https://jakarta.ee/specifications/platform/10/)

---

#### Bean Lifecycle

Beans have a managed lifecycle:

- **Initialization**: After bean creation & DI, `@PostConstruct` method is called (if present)
- **Destruction**: When app context is closed, `@PreDestroy` is called (if present)

Advanced hooks:

- Implementing `InitializingBean` and `DisposableBean`
- Implementing `BeanPostProcessor` for custom actions

Example:

```java
@Component
public class CacheManager {
    @PostConstruct
    public void warmUp() { ... }
    @PreDestroy
    public void shutdown() { ... }
}
```

#### Spring Boot Auto-Configuration and Beans

- Spring Boot auto-configuration defines many `@Configuration` classes with `@Bean` methods (includes
  `@ConditionalOnClass`, etc).
- These supply “infrastructure beans” (ObjectMapper, DataSource, RestTemplate, etc) that your code can inject without
  extra wiring.
- You can **override** any auto-configured bean by defining your own bean of the same type or name.

#### Customizing/Overriding Beans:

- Just declare your own bean (with `@Bean` or `@Component`  ̶; highest precedence wins).
- Use `@Primary` to make a specific bean default if there are multiple choices.
- Remove/exclude beans via configuration or use `spring.autoconfigure.exclude=...`.

#### Why Beans Matter

- **Modularity:** Everything is loosely wired by interfaces, easily swapped
- **Testability:** Beans can be mocked or replaced in tests
- **Decoupling:** Your code never “news” a dependency, enabling inversion of control (IoC)
- **Observability/Tracing:** Many operations (e.g. AOP, transaction, security) all work via bean proxying
- **Lifecycle Management:** Initialization/shutdown are managed automatically

#### Spring Beans: Enterprise FAQ & Must-Knows (2025)

**Q: What is a Spring bean? How is it different from a plain Java object?**

A Spring bean is any object managed, constructed, and injected by the Spring IoC container (usually ApplicationContext).
Beans benefit from:

- Lifecycle management (init, destroy, custom callbacks)
- Dependency injection (DI): Spring wires dependencies automatically
- Scope and contextualization (singleton by default, see below)
- AOP: Interceptors, proxies, transactional & cross-cutting support
- Declarative configuration (annotation, Java config, XML, etc.)

A plain Java object—sometimes called a POJO—has none of this. You create and manage it manually (via `new`), missing out
on DI, lifecycle, scopes, and infrastructure wrappers.

---

**Q: Name all ways to register a bean (annotation, @Bean, XML, auto-config).**

**1. Annotation-based (most common):**

```java
@Component
public class MyService {}

@Service
public class OrderService {}

@Repository
public class OrderRepo {}

@Controller
public class UserController {}
```

**2. Java Config `@Bean`:**

```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

**3. XML-based (legacy, rare):**

```xml
<bean id="myService" class="com.example.MyService"/>
```

**4. Auto-Configuration:**
Via Spring Boot, beans can be registered automatically based on classpath and property conventions. Example: adding
`spring-boot-starter-web` adds and auto-wires DispatcherServlet, Tomcat, WebMvc beans, etc.

**5. Programmatic Register (rare, infra code):**

```java
GenericApplicationContext context = ...;
context.registerBean("myBean", MyBean.class, MyBean::new);
```

---

**Q: Explain the difference between singleton and prototype scopes.**

- `singleton` (default): Only one bean instance is created per ApplicationContext for the bean definition. All
  @Autowired injections and lookups get the same instance.
- `prototype`: A new instance is created for each injection (or lookup via `getBean()`), not managed after
  instantiation.

Example:

```java
@Component
@Scope("singleton") // default
public class SingletonBean {}

@Component
@Scope("prototype")
public class PrototypeBean {}
```

- Other special scopes: `request`, `session`, `application` (web only).
- Best practice: Prefer singleton for stateless beans, prototype for stateful/short-lived objects.

---

**Q: Show constructor vs field vs setter injection. Why is constructor the enterprise standard?**

**Constructor injection (recommended):**

```java
@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) {
        this.repo = repo;
    }
}
```

**Field injection (discouraged):**

```java
@Service
public class UserService {
    @Autowired
    private UserRepository repo;
}
```

**Setter injection (optional/legacy or for optionals):**

```java
@Service
public class UserService {
    private UserRepository repo;
    @Autowired
    public void setRepo(UserRepository repo) {
        this.repo = repo;
    }
}
```

**Constructor injection is the enterprise standard in 2025 because:**

- Forces explicit dependency declaration (self-documenting)
- Promotes immutability (fields can be final)
- Easiest to test with plain Java (can instantiate with mocks/stubs)
- Prevents cyclic dependencies at compile-time
- Compatible with modern frameworks, AOT, records, Lombok, Kotlin, etc.

---

**Q: What happens if two beans of the same type are registered? How do you control which gets injected?**

By default, Spring will fail with `NoUniqueBeanDefinitionException`. To control which bean gets injected:

- Use `@Primary` on the preferred bean definition:
  ```java
  @Bean
  @Primary
  public DataSource mainDataSource() {...}
  ```
- Use `@Qualifier` on the injection point:
  ```java
  @Autowired
  @Qualifier("mainDataSource")
  private DataSource dataSource;
  ```
- For XML: `<bean id="mainDataSource" .../>` and then use `@Qualifier("mainDataSource")` at the injection point or
  `ref="mainDataSource"` in XML.

---

**Q: How do you override or customize an auto-configured bean in Spring Boot?**

Just provide your own bean definition of the same type or name:

- For most Boot auto-configured beans, declare a `@Bean` or `@Component` of the type/class (or the same name).
- Use `@Primary` or `@Qualifier` as needed for further selection.
- To disable default auto-config, use:
  - `@SpringBootApplication(exclude = SomeAutoConfiguration.class)`
  - `spring.autoconfigure.exclude=...` in `application.properties`
- For advanced/infra use: extend/implement the auto-config bean or use BeanPostProcessor.

---

**Q: Describe bean lifecycle (initialization, PostConstruct, PreDestroy, destruction).**

See the full 'Bean Lifecycle (2025 Enterprise Standard)' section above for a phased/modern breakdown. In summary:

1. Bean is instantiated, then dependencies injected.
2. Optional `*Aware` callbacks (e.g., `BeanNameAware`).
3. Pre-initialization BeanPostProcessors (e.g., AOP proxies).
4. Initialization in order: @PostConstruct method, InitializingBean.afterPropertiesSet, custom init method,
   @EventListener (for staged/late init).
5. Bean in use (injected, available as dependency, etc.).
6. On context shutdown: @PreDestroy (cleanup), DisposableBean.destroy, custom destroy method, SmartLifecycle (for phased
   shutdown).

All phases are managed automatically by the container. (See above for advanced hooks, phased startup/shutdown, and best
practices.)

---

**Q: Why is DI important for modularity and testability?**

- **Modularity:** DI decouples components/classes from their dependencies—classes request collaborators via interfaces
  or constructors, not hard-wired objects. This enables code reuse, easier replacements, and swapping implementations
  for different environments (e.g., test vs prod).
- **Testability:** Because dependencies are injected, it's easy to substitute mocks/fakes in unit tests without
  requiring a full application context. Constructor-based DI especially makes beans testable via plain `new`
  ...instantiations.
- **IoC Principles promote single responsibility, clear ownership, and layered architecture, which drive maintainable,
  enterprise-grade apps.**

---

**References:**

- [Spring Core: Beans, Scopes, and Contexts](https://docs.spring.io/spring-framework/reference/core/beans.html)
- [Official Spring Boot Bean Customization](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration.customizing)
- [Spring DI Patterns and Tips (2025)](https://medium.com/@ayoubseddiki132/deep-dive-into-spring-boot-3-bean-lifecycle-modern-approaches-and-best-practices-fcc46d7f87c2)

### 3.2. Spring Application Events: Decoupling and Modular Design

#### Why Use Spring Events?

Spring application events provide a powerful, in-process pub/sub mechanism for decoupling components, freeing you from
direct service-to-service calls. They enable:

- **Modular Monoliths**: Internal domain events can trigger modular, decoupled business logic (DDD, clean architecture)
- **Loose Coupling**: Callers and listeners do not need to know about each other
- **Extensibility**: New features (auditing, notifications, workflows) added with no change to the core logic
- **Side Effects**: Fire-and-forget operations, e.g. sending emails, logging, stats gathering

#### Defining and Publishing Events

- **Event Definition**: Any POJO can be an event (since Spring 4.2+). Extending `ApplicationEvent` is optional.
  ```java
  public class TicketCreatedEvent {
      private final Long ticketId;
      public TicketCreatedEvent(Long ticketId) { this.ticketId = ticketId; }
      public Long getTicketId() { return ticketId; }
  }
  ```
- **Publishing**: Inject `ApplicationEventPublisher` and call `publishEvent()`.
  ```java
  @Service
  public class TicketService {
      @Autowired
      private ApplicationEventPublisher eventPublisher;
      public void createTicket(Ticket ticket) {
          // ... business logic ...
          eventPublisher.publishEvent(new TicketCreatedEvent(ticket.getId()));
      }
  }
  ```

#### Listening to Events

- **Classic:** Implement `ApplicationListener<TicketCreatedEvent>`:
  ```java
  @Component
  public class TicketNotificationListener implements ApplicationListener<TicketCreatedEvent> {
      @Override
      public void onApplicationEvent(TicketCreatedEvent event) { /* ... */ }
  }
  ```
- **Modern:** Use `@EventListener` annotation (recommended in 2025):
  ```java
  @Component
  public class AuditLogListener {
      @EventListener
      public void logEvent(TicketCreatedEvent event) { /* ... */ }
  }
  ```
- **Conditional Listener with SpEL:**
  ```java
  @EventListener(condition = "#event.critical == true")
  public void onCriticalTicket(TicketCreatedEvent event) {...}
  ```
- **Generic Events:** Listeners can listen to event base types; use for common polymorphic logic.

#### Asynchronous Event Handling

- Synchronous by default; all listener methods execute on publisher thread.
- Asynchronous handled with `@Async` annotation and `@EnableAsync` configuration:
  ```java
  @Async
  @EventListener
  public void handleAsyncEvent(CustomEvent event) {...}
  ```

#### Transactional Event Listeners

- **@TransactionalEventListener** allows you to trigger event listeners *within* or *after* a transaction:
  ```java
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void afterCommitListener(DomainEvent event) {...}
  ```
  - Phases: BEFORE_COMMIT, AFTER_COMMIT (default), AFTER_ROLLBACK, AFTER_COMPLETION
- Powerful in DDD for domain events, guarantees consistency with DB state.

#### Ordering Multiple Listeners

- Multiple listeners for the same event have an undefined order by default.
- Enforce order with `@Order` annotation on listener methods/classes.

#### Modular Monoliths and Event Persistence (Spring Modulith)

- **Spring Modulith** (2023+): Enterprise pattern for modular monoliths
  - Provides event persistence ("event outbox"), reliable delivery, event replay, externalization to Kafka/RabbitMQ/etc.
  - Use `@ApplicationModuleListener` for durable, replayable events; supports transactional guarantees
  - Great for testability and resilience in complex apps

#### Best Practices (2025)

- Use events to decouple module boundaries, implement side effects, build for extensibility
- Avoid events where strict ordering or mandatory execution is required; call service methods directly in critical/core
  flows
- Prefer `@EventListener` over legacy interfaces
- Use async events for long-running/out-of-band processing, but ensure you handle reliability/failure
- For high resilience: consider Spring Modulith or integrate with message brokers for persistent delivery
- Keep listener logic minimal and focused
- Test event listeners thoroughly (use `ApplicationEventPublisher` in tests)

#### Real-World Patterns

- Domain events (DDD): State changes fire events, other modules react
- Audit logging, email notifications, external system sync, stats/metrics
- Modular monoliths: module boundaries communicate via events
- Microservices migration: start with events internally, externalize later

#### Caveats, Limitations, and Weaknesses

- Ordinary Spring events are in-memory only and can be lost on crash (modulith or brokers needed for durability)
- Bad for core, critical workflow steps
- Async listeners must be managed for error handling and retries
- Transactional event listeners only trigger if the transaction exists

#### Interview/Mastery Notes

- Compare `@EventListener` vs. `ApplicationListener`
- Explain when to use events vs. service calls
- What are transactional events and when required?
- How to make events async?
- What is Spring Modulith and why use it?
- How to write/test event-driven modular Java apps?
- Pitfalls: listener execution order, reliability, performance, event loss
- How to document and test which events your app produces/consumes?

---

### 3.3. Spring Resource Abstraction & ResourceLoader

#### What Is a Resource in Spring?

A **Spring resource** is an abstraction representing data (e.g. files, URLs, blobs, config, streams) regardless of its
underlying source. This lets you work with application resources (files, data, templates, config, static assets) in a
uniform way—across classpath, filesystem, web, JARs, cloud, or even custom protocols.

The central interface: `org.springframework.core.io.Resource` with methods:

- `getInputStream()`, `getFile()`, `exists()`, `getURL()`, `getFilename()`, `getDescription()`, etc.

#### How to Load Resources

**1. ResourceLoader (core method):**

- All Spring application contexts implement `ResourceLoader` (method: `getResource(String location)`).
- You can inject `ResourceLoader` or use the context (e.g. `ApplicationContext`).

```java
@Service
public class ConfigService {
    @Autowired
    private ResourceLoader resourceLoader;
    public String loadConfigFile() {
        Resource resource = resourceLoader.getResource("classpath:config.yaml");
        // read with InputStream, etc.
    }
}
```

**2. @Value injection:**

```java
@Component
public class FileLoader {
    @Value("classpath:templates/template.html")
    private Resource htmlTemplate;
    // ... use htmlTemplate.getInputStream() ...
}
```

**3. ResourceUtils:**

- Utility for classic `File`/`URL`/classpath resolution (`ResourceUtils.getFile("classpath:xyz.txt")`)

#### Resource Types

- `ClassPathResource`: Loads from classpath/JAR/WAR (`classpath:myfile.txt`)
- `FileSystemResource`: Loads from OS filesystem (`file:/var/data/foo.txt`)
- `UrlResource`: Loads from any valid URL (`file:`, `http:`, `https:`, etc.)
- `ServletContextResource`: Web contexts (rare for non-Legacy Spring Boot)
- `ByteArrayResource`, `InputStreamResource`: In-memory or custom
- (*) Many more: see [docs](https://docs.spring.io/spring-framework/reference/core/resources.html)

#### Usage Examples

- Loading config or data files:
    ```java
    @Value("classpath:data/sample.json")
    private Resource sampleData;
    String json = IOUtils.toString(sampleData.getInputStream(), StandardCharsets.UTF_8);
    ```
- Binary files, static media, templates: use `Resource` to provide input to service logic, PDF/image generation, etc.
- Reading resource as `File` (be careful—works only on exploded WAR/JAR in dev, NOT always in prod):
    ```java
    File image = resourceLoader.getResource("classpath:img/logo.png").getFile();
    ```

#### Resource Wildcards, Path Matching, Resolution Order

- Use `ResourcePatternResolver` for wildcards (`classpath*:com/example/**/*.xml`) to load many at once
    ```java
    @Autowired ResourcePatternResolver resolver;
    Resource[] resources = resolver.getResources("classpath*:templates/*.html");
    ```
- Order: `classpath:` and `file:` prefixes (explicit) control loader type. No prefix: default context behavior (
  classpath for most Boot apps).
- Path matching and Ant-style wildcards supported with context-aware loaders

#### Custom Resource Implementations, Custom ResourceLoader

- For advanced needs: implement `Resource` for DB, S3, network, etc. ProtocolResolver: add new prefix (ex: `s3://`)
- Register new protocols for resources with ResourceLoader in Boot
- Used in SaaS/multi-tenant, content APIs, plug-in discovery

#### Best Practices

- Place all app data/resources in `src/main/resources/`; static, template, or asset subfolders as needed
- Use classpath resources for common config, data, templates, test stubs
- Design code to accept `Resource`, not `File` or `InputStream` for testability and packaging flexibility
- Be aware: `File` will fail for resources inside JAR/WAR (prefer streams, filenames, or copy to temp)
- Test resource loading in both dev (exploded) and prod (packaged/JAR) to avoid surprises

#### Pitfalls

- File-based loading (`getFile()`) fails in packaged apps (JAR/WAR)
- Classpath confusion if resource in wrong directory or wrong case
- Wildcards (`classpath*`) are powerful but have edge cases with JARs/classloaders—test in CI/CD
- Multiple resources with same name in several JARs—Spring may use first found!

#### Interview Notes/Q&A

- Resource abstraction vs. File/URL
- @Value/resourceLoader in testable design
- When NOT to use `File` (answer: packaged apps!)
- Strategies for large-scale resources or DB/cloud loader
- How to test multi-classpath-location loads

---

Spring Boot’s “core concepts” lay the foundation for rapid, production-grade Java application development in 2025.
Mastering these enables you to diagnose problems, build robust applications, and answer interview/architecture questions
with authority.

---

#### 3.4. Data Binding and Type Conversion in Spring Boot

#### What is Data Binding in Spring?

- **Data binding** is the automatic process of mapping external data (e.g. web requests, forms, JSON, configs) to Java
  objects (controllers, DTOs, @ConfigurationProperties, beans).
- Enables seamless population of POJOs from requests, properties, YAML, web forms, etc.

#### Core Abstractions

- **DataBinder / WebDataBinder**: The core objects for mapping Strings to object fields. Used internally by web/MVC and
  config binding. You can extend/customize (rare for modern use).
- **BeanWrapper**: Lower-level utility for setting/getting bean properties (supports nested paths, etc). Used
  internally.
- **PropertyEditor** _(legacy)_: Thread-unsafe, string-to-type; still supported for backward compatibility but not
  recommended in new code.

#### ConversionService (Modern Standard)

- **ConversionService**: Central type conversion API (thread-safe, modern, strongly-typed). Used by Spring Boot, Spring
  MVC, config, validation.
  - Built-in converters: String <-> Number, String <-> Date, Enum, Boolean, collections, maps, Java 8 time, etc.
  - **Formatter**: Higher-level API for parsing/printing (esp. with localization/i18n and display as well as input).

Example:

```java
@ConfigurationProperties(prefix = "notify")
public class NotifyProps {
    private Duration debounceTimeout; // "1m" in YAML → Duration
    // setters/getters
}
// in application.yml: notify: debounceTimeout: 1m
```

Spring uses ConversionService to bind "1m" to `Duration`.

#### Registering Custom Converters & Formatters

- **Converter<S, T>**: Convert source to target type (e.g., String→UserId)
  ```java
  @Component
  public class StringToUserIdConverter implements Converter<String, UserId> {
      public UserId convert(String s) { return new UserId(Long.parseLong(s)); }
  }
  ```
- **Formatter<T>**: For types with both parse (String→T) and print (T→String)
  ```java
  @Component
  public class MoneyFormatter implements Formatter<Money> {
    ... // parse(String, Locale), print(Money, Locale)
  }
  ```
- Register as beans (auto-detected in Boot), or add via `WebMvcConfigurer.addFormatters()`
- **@DateTimeFormat, @NumberFormat** annotations: Per-property auto-conversion/formatting for web forms and config

#### Using @InitBinder for Controller-Specific Conversion

```java
@Controller
public class AccountController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new MoneyFormatter());
    }
}
```

#### Data Binding in MVC/Web

- @ModelAttribute maps form/query/path fields to POJOs via DataBinder & ConversionService
- JSON/XML binding via Jackson (uses ConversionService for field conversions)
- MVC pipes everything through ConversionService (global and local)

#### Advanced: Conditional, Context-Aware, Chained Converters

- Write ConverterFactory or GenericConverter (e.g., handling hierarchies, multi-criteria)
- Use TypeDescriptor for generic types, annotations, metadata

#### Property Editors (Legacy):

- JavaBeans `PropertyEditor` support for compatibility—still used in @InitBinder, but only for old code; not
  thread-safe, prefer Converter/Formatter.

#### Error Handling, Nulls, and Validation

- Conversion errors by default trigger binding errors in Spring MVC/config
- To handle errors: use `@Valid` and binding result handling for custom messages
- Nulls: non-convertible fields are null unless required/not-null constraint

#### Pitfalls

- Custom PropertyEditors are NOT thread-safe; prefer Converter/Formatter
- `getFile()` pitfalls: only works on disk, not with resources in JAR/WAR
- Carefully match field/property names/types for successful binding
- Global vs. local conversion: accidentally conflicting formatters/converters (test thoroughly)

#### Best Practices (2025)

- Prefer strongly-typed, thread-safe `Converter`/`Formatter` via bean registration
- Use `@ConfigurationProperties` for config with type safety
- Use `@DateTimeFormat`, `@NumberFormat` for local formatting
- Leverage global ConversionService for domain types across app
- Test custom converters with unit and integration tests (inject ConversionService, try edge cases)
- Document expected formats for config, request, and display for frontends

#### Interview/Mastery Notes

- ConversionService vs. PropertyEditor?
- How does formatter registration order affect binding?
- What’s the difference between Converter, Formatter, ConverterFactory, GenericConverter?
- How does @InitBinder work? Why use it?
- What happens if a type is not convertible? How do you override a global converter?
- How does type conversion work for config properties and web endpoints?
- How to test converters and diagnose binding failures?

#### Further Reading

- [Spring Conversion and Formatting Docs](https://docs.spring.io/spring-framework/reference/core/validation/convert.html)
- [Baeldung: Spring Type Conversions](https://www.baeldung.com/spring-type-conversions)
- [Spring MVC Data Binding](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-config/conversion.html)
- [@ConfigurationProperties Binding](https://docs.spring.io/spring-boot/docs/current/reference/html/configuration-properties.html)

---

### 3.5. Spring Expression Language (SpEL): Dynamic Power in Annotations and Config

#### What is SpEL?

- **Spring Expression Language (SpEL)** is a powerful, runtime expression language built into the Spring ecosystem
- Evaluates snippets (“expressions”) in configuration, annotations, and code: enables dynamic values, property
  injection, security, and conditional logic
- Used for: dynamic bean wiring, property injection, conditional beans/binding, filtering collections, advanced
  security, and more

#### Core Syntax and Capabilities

- Expression marker: `#{...}` (evaluates at runtime)
- **Literals:** `'string'`, `123`, `true`, `null`
- **Property/method access:** `#{user.name}`, `#{user.getEmail()}`
- **Operators:** arithmetic (`+,-,*,/`), comparison (`==,!=,<,>`, etc), logical (`&&,||,!`), ternary, Elvis (`?:`)
- **Static method calls:** `#{T(java.lang.Math).max(2, 10)}`
- **Variables/refs:** `#{#root}`, `#{#this}`, `#{#user}`, `#{@myBean.someMethod()}`
- **Collections/maps:** `#{user.orders[0]}`, `#{myMap['key']}`

#### Where SpEL is Used (Spring Boot/Framework)

- **@Value:** inject dynamic/derived/config/property values into bean fields
    ```java
    @Value("#{2 * 60 * 1000}")
    private int twoMinutesMillis; // Evaluates to 120000
    @Value("#{@appPropsBean.timeout ?: 5000}")
    private int timeout; // Use bean property, fallback to 5000
    ```
- **@ConditionalOnExpression:** Auto-config/Boot—include/exclude beans based on SpEL evaluation
    ```java
    @ConditionalOnExpression("'${spring.profiles.active}' == 'dev'")
    ```
- **@EventListener(condition = ...):** Listen only under runtime condition
    ```java
    @EventListener(condition = "#event.priority == 'HIGH'")
    public void handleHighPriority(Event event) {...}
    ```
- **@PreAuthorize, @PostAuthorize:** Advanced method security (Spring Security):
    ```java
    @PreAuthorize("hasRole('ADMIN') || #userId == authentication.principal.id")
    public void deleteUser(Long userId) { ... }
    ```
- **@Scheduled:** Dynamic cron from bean/expression
    ```java
    @Scheduled(cron = "#{@cronProvider.getCron()}")
    ```
- **@Cacheable:** Dynamic cache keys, conditional caching
    ```java
    @Cacheable(value="users", key="#user.id", condition="#user.active")
    ```
- **application.yaml/properties:** For some Boot features (rare, risky—prefer Java/annot usage)

#### Programmatic SpEL Usage

```java
@Autowired
Environment env;
ExpressionParser parser = new SpelExpressionParser();
int val = parser.parseExpression("1 + 2").getValue(Integer.class); // == 3
String adminEmail = parser.parseExpression("'help@' + systemProperties['user.domain']").getValue(String.class);
```

#### Advanced Features

- **Custom SpEL Functions:** Register static methods for use in SpEL
- **Collection projections/filtering:** `orders.?[status=='PAID']`, access/stream/filter/transform
- **Bean references:** Access other beans with `@` syntax: `#{@orderService.topOrders(5)}`
- **Context objects:** `#root`, `#this`, method argument names in security/event expressions

#### Best Practices & Anti-patterns

- Use SpEL for *configuration*, property wiring, and *conditional logic*, NOT for business logic
- Keep expressions short, simple, and maintainable; avoid deep chains or complex code-in-strings
- Validate expressions at startup (fail fast); prefer compile-time safety when possible
- In security: use SpEL as intended, but don’t allow user input into expressions
- Use modern Boot features (`@ConfigurationProperties`, service beans) for most config, only use SpEL for dynamic bits

#### Pitfalls & Security

- SpEL errors only appear at runtime (linter/IDE doesn’t check)
- Overusing SpEL makes code brittle/harder to refactor/test
- SpEL is powerful enough to be dangerous: can instantiate classes, call methods, potentially risky if expressions are
  ever user-controllable
- Performance: Avoid in performance-critical inner loops

#### Debugging SpEL

- Parse errors: catch at startup if used in config, or via clear stack traces in test
- Use logs or boot debug to see evaluation failures
- Write unit tests/cases for custom SpEL

#### Interview/Mastery Q&A

- What is SpEL and how is it used in modern Spring Boot?
- Where is SpEL used in config vs annotation vs code?
- How does SpEL compare to Java EE EL or OGNL/other ELs?
- What are pitfalls and anti-patterns with SpEL? Security issues?
- How do you troubleshoot/validate/integration-test SpEL-heavy code?

#### Further Reading

- [Official SpEL Documentation](https://docs.spring.io/spring-framework/reference/core/expressions.html)
- [Baeldung: Spring Expression Language](https://www.baeldung.com/spring-expression-language)
- [SpEL with Method Security](https://www.javacodegeeks.com/2025/05/fine-grained-authorization-with-spring-security-and-preauthorize-annotations.html)
- [Spring Scheduling with SpEL](https://docs.spring.io/spring-framework/reference/integration/scheduling.html#scheduling-annotation-support)

---

### 3.6. Aspect-Oriented Programming (AOP) in Spring: Separation of Cross-Cutting Concerns

#### What is AOP in Spring?

- **Aspect-Oriented Programming (AOP)** is a paradigm to modularize “cross-cutting concerns” (logging, security,
  transactions, metrics, auditing) so they don’t pollute your business logic.
- Enables you to define *aspects* and *advices* that are applied “around” methods, decoupling infrastructure from core
  code.
- In enterprise Java, AOP is critical for transaction management, access control, observability, and unified logging:
  all invisible to the primary code.

#### Core AOP Concepts

- **Aspect:** A class that encapsulates cross-cutting concerns (e.g., LoggingAspect)
- **Join Point:** A point during execution (usually a method call) where an aspect can be applied
- **Advice:** The action to be taken at a join point (`@Before`, `@After`, etc)
- **Pointcut:** Predicate/expression that matches join points (e.g., all methods in package, or @Transactional methods)
- **Target:** The object being advised
- **AOP Proxy:** The runtime proxy that intercepts method calls (created by Spring)
- **Weaving:** Process of linking aspects and code (Spring: done at runtime with proxies)

#### Spring AOP vs. AspectJ

- **Spring AOP**: Proxy-based (JDK proxy for interfaces, CGLIB subclass if no interfaces). Only method execution join
  points on Spring beans. Weaving occurs at runtime.
- **AspectJ**: Compile-time or load-time weaving. Supports field, constructor, method, and more. Use for super-advanced,
  non-bean, or highly fine-grained concerns.

#### Types of Advice (code samples)

- **@Before**: Executes before the join point
  ```java
  @Aspect
  @Component
  public class LoggingAspect {
      @Before("execution(* com.example.service.*.*(..))")
      public void logBefore(JoinPoint jp) { log.info("Before: {}", jp.getSignature()); }
  }
  ```
- **@After**: After method completes (regardless of outcome)
  ```java
  @After("execution(* com.example.service.*.*(..))")
  public void logAfter(JoinPoint jp) { log.info("After: {}", jp.getSignature()); }
  ```
- **@AfterReturning**: After method returns successfully
  ```java
  @AfterReturning(pointcut = "execution(* com.example.*.*(..))", returning = "result")
  public void afterReturning(JoinPoint jp, Object result) { log.info("Returned: {}", result); }
  ```
- **@AfterThrowing**: After method throws exception
  ```java
  @AfterThrowing(pointcut = "execution(* com.example.*.*(..))", throwing = "ex")
  public void afterThrowing(JoinPoint jp, Exception ex) { log.warn("Exception: {}", ex.getMessage()); }
  ```
- **@Around**: Surrounds method execution (can short-circuit or modify input/output)
  ```java
  @Around("execution(* com.example.service.PaymentService.*(..))")
  public Object timeExecution(ProceedingJoinPoint pjp) throws Throwable {
      long start = System.currentTimeMillis();
      Object result = pjp.proceed();
      long elapsed = System.currentTimeMillis() - start;
      log.info("{} executed in {}ms", pjp.getSignature(), elapsed);
      return result;
  }
  ```

#### Defining and Configuring Aspects

- Mark aspect classes with `@Aspect` + `@Component`, use `@EnableAspectJAutoProxy` in config/main app
- Pointcut expressions: `execution`, `within`, `args`, `@annotation`, etc.
  - `execution(* com.example..*Service.save*(..))`: All save* methods in ...Service
  - `@annotation(org.springframework.transaction.annotation.Transactional)`: All transactional methods
- Bind arguments in pointcuts: use named arguments (`args(name,...)`)

#### Real-World Patterns and Enterprise Use Cases

- **Logging:** Unified before/after/audit/metrics logging
- **Security:** Access control, permission checks, method security
- **Transaction Management:** Core strategy for @Transactional
- **Validation:** Pre-processing arguments (with JoinPoint)
- **Caching:** Cross-cutting cache, fallback, eviction
- **Resilience:** Combine with Resilience4j (@Around)
- **Tracing/Observability:** Metrics/telemetry spanning methods (
  OpenTelemetry, custom metrics)

#### How Proxies Work (JDK Proxy vs. CGLIB)

- Spring AOP creates a runtime proxy for any eligible Spring bean
- If bean has interface: uses JDK proxy (only proxies interface)
- If bean has no interface: uses CGLIB to create subclass (all public/protected methods)
- **Limitation:** Only calls from outside bean go through proxy—internal method calls (this.foo()) do not trigger advice
- **Private/static/final methods cannot be advised!**

#### Best Practices and Common Pitfalls

- Design aspects for *single* concerns: do not bundle unrelated logic
- Avoid placing business logic in aspects; focus on infrastructure, audit, security, logic overlays
- Be cautious with pointcut expressions—test for accuracy, avoid over-broad or overlapping pointcuts
- Mind the performance: heavy advice code, or Too Many proxies, degrades perf
- **Proxy trap:** Advice only applies to calls through the Spring IoC/proxy, not to internal method-to-method calls

#### Testing and Troubleshooting Aspects

- Unit-test aspects directly or via integration tests
- Use logs to verify advice is applied
- Use proxy-target-class, `@AspectJAutoProxy`, and check `AopProxyUtils.ultimateTargetClass()` if debugging

#### When to Choose AspectJ

- Use pure AspectJ (compile/load-time weaving) for non-bean, non-public, field-level, or third-party integration
- For most Boot apps, stick with Spring AOP unless you require super-fine-grained joint points

#### Advanced Integrations

- Combine AOP with @Transactional, security (Spring Security), Resilience4j annotations, and OpenTelemetry
  metrics/tracing (@Around for span creation)

#### Interview/Mastery Notes & Q&A

- Compare Spring AOP vs AspectJ—when/why each?
- How does an @Around advice differ from @Before/@After? Use case?
- What’s a join point, pointcut, advice? Example for each?
- How do proxies work? Why isn’t advice applied to private/static/final/self-invoked methods?
- List steps to debug an aspect that “isn’t running.”
- Trade-offs of placing business or visibility logic in aspects?
- How to test/monitor/prove AOP works in prod?
- What’s the effect of AOP on proxy performance, and how do you mitigate?

#### Further Reading

- [Spring AOP Reference](https://docs.spring.io/spring-framework/reference/core/aop/introduction-spring-defn.html)
- [Baeldung: Guide to Spring AOP](https://www.baeldung.com/spring-aop)
- [Spring Boot AOP Best Practices (2025)](https://medium.com/@sharmapraveen91/mastering-spring-aop-the-ultimate-guide-for-2025-55a146c8204c)
- [AspectJ Project](https://www.eclipse.org/aspectj/)
- [OpenTelemetry for Java: Spans & AOP](https://opentelemetry.io/docs/instrumentation/java/manual/#spring-apps)

---

### 3.7. `@SpringBootApplication` and Meta-Annotations

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

#### 3.7. Configuration: `application.properties` vs `application.yml`

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

#### 3.8. Auto-Configuration & Custom Configuration

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

#### 3.9. Conditional Beans

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

#### 3.10. Profiles

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

#### 3.11. Embedded Server Options

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

#### 3.12. Logging Configuration

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

#### 3.13. Key Updates in Spring Boot 3.x / 3.5 (2025)

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

#### 3.14. Interview “Must Knows”, Practice & Troubleshooting Tips

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

#### 3.15. Further Reading & Practice Links

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

### Deep Dive: How JPA and Hibernate Work in 2025 Enterprise/Cloud Apps

#### JPA: Jakarta Persistence API—2025 Spec Standpoint

- **What it is:** JPA is a Jakarta EE specification (now at Jakarta Persistence 3.2+), not a library or implementation.
  It defines standard annotations (`@Entity`, `@Table`, `@Id`, `@ManyToOne`, etc.), the `EntityManager` API, and the
  object/relational mapping model, but does *not* ship as a library you use directly. Its purpose is to provide a
  portable, predictable, standardized way of persisting Java objects as relational data.
- **Why it matters:** By targeting the JPA API/annotations, your code is portable between compliant providers (
  Hibernate, EclipseLink, OpenJPA…), but *in practice* Hibernate is used in nearly all modern Java and Spring projects.
- **Key 2025 changes:** All imports are now `jakarta.persistence.*` (not `javax.*`). Java 17+ (Java 21+ typical) and
  Jakarta EE 11 are now minimums. Criteria API is more type-safe, and entity DTOs can now be Java records out of the
  box. JPA is always extended (under the hood) by a provider—see below.

#### Hibernate (7.x): Modern ORM Engine & JPA Provider

- **What it is:** Hibernate is the most popular JPA implementation. When you use JPA in a Spring Boot or Quarkus
  project, you’re almost always using Hibernate as the engine beneath the API. Hibernate also offers a native, powerful
  ORM API (`Session`, `SessionFactory`, HQL, stateless operations, advanced second-level cache, entity listeners, custom
  type support, batch operations, and more).
- **Why developers care:** Hibernate tracks the JPA spec but adds:
  - Native HQL query language—a superset of JPQL/JPA’s query system
  - New typesafe query/criteria APIs (QuerySpecification, Restrictions)
  - Compile-time static metamodel/codegen: safer queries, fewer runtime bugs
  - Full DTO/Java record projection (for ultra-fast, typesafe APIs)
  - StatelessSession for streaming/batch/data warehousing workloads (no first-level cache overhead, 2nd-level cache by
    default in 7.x)
  - Deep support for JSON, UUID, arrays, embeddable types, window functions, etc.
  - Jakarta Data, Spring Data JPA, record-based repositories
- **2025 must-know:** Hibernate 7 (2025):
  - Now requires Apache 2.0 license (no more LGPL confusion)
  - Baselines on Java 17+/21+ and Jakarta Persistence 3.2/EE 11
  - Major improvements to query expressiveness, type safety, metamodel generation, and batch/stateless ops
  - Native projections for Java records: ultra-performant write/read models
  - Fixes many gotchas from legacy versions (e.g., stateless session now respects 2nd-level cache by default)

#### Spring Data JPA, Jakarta Data, and Modern Repository Patterns (2025)

- **Repository Abstraction:** For 99% of enterprise and cloud Java, the preferred way to use JPA/Hibernate is via a
  repository abstraction:
  - **Spring Data JPA:** Lets you define interfaces (e.g., `UserRepository extends JpaRepository<User, Long>`) that
    auto-generate CRUD methods, query derivations, and paging/sorting
  - **Jakarta Data (NEW in 2025):** Similar to Spring Data, tighter integration with Hibernate 7+, compile-time
    query/type validation, less boilerplate, works outside Spring too
- **Why use this pattern:**
  - It completely eliminates most boilerplate CRUD code
  - Keeps your data layer clean, testable, and strictly typed
  - Lets you focus on query/DTO/logic, not plumbing/transactions/sessions
- **Modern Codestructure:**
  - Define entities as POJOs or records with JPA annotations
  - Define repositories as interfaces only
  - Use method signatures or `@Query` for complex queries (JPQL, HQL, or native SQL)
  - Real-world: almost no code touches the `EntityManager`/`Session` directly except for rare performance or edge cases

#### JPA vs Hibernate Direct Usage: How to Choose

**Default to JPA (via repository abstraction) if:**

- You want code that remains portable and understandable to any Java team
- Most CRUD, queries, and batch/streaming needs are satisfied by repository or JPA annotations/JPQL
  **Reach for Hibernate-native APIs if:**
- You need:
  - ultra-performance tuning (caching, batch, query hints, fetch profiles, stateless session)
  - HQL features beyond JPQL (window functions, CTEs, DML, custom SQL, advanced joins, etc)
  - Database vendor-specific enhancements
  - Ultra-type-safe programmatic query building (`Restrictions`, JPA static metamodel, DTO projections)
  - Compile-time query validation (Annotation Processor/Metamodel)
    **2025 best-practice:**
- Use repository abstraction (Spring Data JPA/Jakarta Data) everywhere you can
- Drop down to native Hibernate only if you hit a real limitation or see strong evidence for performance/complexity
  gains

#### 2025-Grade Code Structure, Queries, and Patterns

- **Entities:** Use as POJOs or Java records, annotated with `@Entity`, etc. Stick to simple structures—avoid “fat”
  entities, put logic in services.
- **Repositories:** Prefer interface-based repositories (`JpaRepository`, `CrudRepository`, or `@Repository` interface
  in Jakarta Data). No implementation code written by you.
- **DTO projections:** Use record classes and explicit projections for performance and clarity on queries/REST APIs (
  avoid leaking entities outside service boundaries).
- **Transactions:** Cover service layer entrypoints with `@Transactional`; avoid explicit transaction/EntityManager
  handling except for advanced cases.
- **Querying:**
  - For simple needs: derive method name (`findByEmailAndStatusOrderByCreatedDesc`)
  - For custom logic: `@Query` with JPQL/HQL/native SQL. In modern Hibernate, use type-safe projections, records, static
    metamodel references.
  - For dynamic/complex: Criteria API, QuerySpecification, Restrictions API
- **Batch/streaming/ETL:** Use Hibernate’s `StatelessSession` for memory-efficient bulk operations, especially with
  large data or streaming.
- **Caching:** Use transactional control for 1st-level/2nd-level cache; @Cacheable for app-level cache (spring
  abstraction).

#### Migration, Compatibility, and Enterprise Pitfalls (2025)

- **Jakarta migration:** All `javax.*` must become `jakarta.*`; legacy code/libs will break; check dependencies for
  latest versions.
- **JPA version matters:** Hibernate 7+ requires Jakarta Persistence 3.2+, Java 17+/21+.
- **Repository method changes:** Advanced query signatures, projections, and type-safety (see Jakarta Data/Metamodel
  docs).
- **Batch/stateless session:** StatelessSession now uses 2nd-level cache by default in 7.x; revisit any custom logic
  from 5/6.
- **Projection/DTO bug fixes:** Java records can be used for “constructor result” queries, but parameter order must
  exactly match the query select order.
- **Query language:** Hibernate HQL is richer than JPQL; don’t expect strict portability if relying on non-standard
  features.
- **Pooling/config:** HikariCP/Agroal are new defaults. Avoid deprecated pools (Vibur, UCP, Proxool). See
  Hibernate/Spring Boot docs for tuning.
- **Testing:** Use `@DataJpaTest`, Testcontainers, synthetic data for integration/CI—avoid raw JDBC/persistence.xml
  unless absolutely required.

#### FAQ (2025 Edition)

- **Should I use native Hibernate everywhere?** No—use repository/annotation/JPQL/HQL/DTO patterns everywhere you can.
  Drop down to Hibernate-native only for advanced needs.
- **Is `Session` better than `EntityManager` now?** For most app code, use the higher-level repository abstractions that
  wrap both. In batch/ETL/streaming or for custom JDBC logic, prefer `StatelessSession` or directly tune the native API.
- **Do I still need DAOs?** Not in the classic sense. Most modern code uses interface-only repositories and DTOs,
  eliminating boilerplate DAOs.
- **Any breaking changes I must know?** Yes—Java 17+, Jakarta EE APIs, stateless session cache changes, and metamodel
  generation for DTO/type-safe querying.
- **How to handle advanced queries (window, JSON, array, spatial)?** Use Hibernate-native HQL, types, and static
  metamodel APIs.
- **Where can I go deeper?**
  - [Hibernate ORM documentation (7.x/2025)](https://docs.jboss.org/hibernate/orm/7.0/userguide/html_single/Hibernate_User_Guide.html)
  - [Jakarta Persistence 3.2+](https://jakarta.ee/specifications/persistence/3.2/)
  - [Spring Data JPA ref](https://docs.spring.io/spring-data/jpa/reference/index.html)
  - [Jakarta Data quickstart](https://jakarta.ee/specifications/data/1.0/)

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

#### 5.8.1 Modern ORM Techniques: 2025 Enterprise Deep Dive

This section extends and modernizes ORM patterns for enterprise-grade applications as of 2025, providing practical,
authoritative guidance building on both current reviewer guidance (section 5.8) and latest industry practice.

**A. Enterprise Entity Mapping & Relationship Strategies (2025 conventions)**

- Prefer Java records for immutable DTOs and projections; use POJOs for entities, but annotate with `@Entity`, `@Table`,
  `@Id`, etc. as per Jakarta Persistence 3.2+.
- Always baseline on `jakarta.persistence.*` (no `javax.*`). Use UUIDs for IDs with modern JDBC (define as `UUID`, not
  String) when required.
- Carefully annotate relationships: always specify `fetch=FetchType.LAZY` for `@ManyToOne`/`@OneToOne` by default to
  avoid accidental N+1.

**B. Repository Patterns: Jakarta Data, QuerySpecification, and Modern Interfaces**

- Whenever possible, define repositories using interface-based pattern:
  ```java
  public interface UserRepository extends JpaRepository<User, Long> {}
  // For Jakarta Data (2025+):
  public interface UserRepo extends jakarta.data.repository.PageableRepository<User, Long> {}
  ```
- Leverage type-safe, programmatic querying via QuerySpecification (Hibernate 7):
  ```java
  QuerySpecification<User> spec = QuerySpecification.select(User.class)
      .where(User_.status.eq("ACTIVE"));
  List<User> actives = entityManager.createQuery(spec).getResultList();
  ```
- Use repository signatures and method names as much as possible; add `@Query` for custom/join/fetch queries, preferring
  JPQL/HQL over native except for vendor-specific needs.

**C. Efficient Batch & Bulk Operations**

- Use the StatelessSession API in Hibernate 7 for bulk operations and streaming:
  ```java
  StatelessSession session = sessionFactory.openStatelessSession();
  Transaction tx = session.beginTransaction();
  for(int i=0; i<N; i++) {
    session.insert(new MyEntity(...));
    if(i%batchSize == 0) session.flush();
  }
  tx.commit();
  session.close();
  ```
- Prefer CriteriaUpdate and CriteriaDelete for batch modifications to avoid N individual statements.

**D. DTOs, Java Records & Decoupled Projections**

- Use Java `record` DTOs as return types from repository custom queries, for both concise API and solid immutability:
  ```java
  // DTO record
  public record UserDTO(Long id, String username, String email) {}
  // Repository projection
  @Query("SELECT new com.acme.UserDTO(u.id, u.username, u.email) FROM User u WHERE u.active = true")
  List<UserDTO> fetchActiveUsers();
  ```
- Use interfaces for open projections when needed.

**E. Fetching Strategies: EntityGraph, BatchSize, Join Fetch**

- Use `@EntityGraph` to specify eager/partial fetch scenarios without code bloat:
  ```java
  @EntityGraph(attributePaths = {"orders", "address"})
  List<User> findByActiveTrue();
  ```
- Annotate batch size at entity or relationship as needed (`@BatchSize(size = 25)`).
- For explicit one-shot query joins, use JPQL fetch join:
  ```java
  @Query("SELECT u FROM User u JOIN FETCH u.orders WHERE u.id = :id")
  User findUserWithOrders(Long id);
  ```

**F. Second-Level and Query Caching**

- For entities with high read/low write, enable second-level cache:
  ```java
  @Entity
  @Cacheable
  @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
  public class Product { ... }
  ```
- Configure Ehcache/Hazelcast/etc. as needed in application config. Don’t cache highly mutable entities; select
  concurrency strategy per read/write patterns. Test invalidation.
- For expensive queries, enable and tune the Hibernate query cache.

**G. Advanced Query Techniques (Criteria API, HQL, Native SQL)**

- Prefer Criteria API for safe, dynamic queries (and batch updates/deletes).
- Use HQL (Hibernate Query Language) for advanced features (window functions, vendor extensions) as needed; be mindful
  of portability.
- Resort to native SQL only for cases where HQL/JPQL cannot express requirements.

**H. Diagnostics and Performance Analysis**

- Enable Hibernate statistics at dev/test (disable in prod):
  ```properties
  spring.jpa.properties.hibernate.generate_statistics=true
  ```
- Profile query times, cache hits, connection pool with Actuator, Hibernate statistics, and SQL logs.
- Routinely check for N+1 issues; use logs, statistics, JPA Buddy/IDEA tools.
- Use Testcontainers for integration tests with real DBs, not just in-memory tests, to uncover query performance issues
  in realistic environments.

**I. Modern Pitfalls & Recommendations**

- Avoid leaking entities outside service boundaries; always return DTO/projection.
- Never use `ddl-auto:update` in production—use migrations (Liquibase/Flyway).
- Explicitly manage fetch loading for large/complex object graphs—avoid silent N+1 issues.
- Monitor/bound batch sizes; too large can cause memory spikes, too small negates batch efficiency.
- For JSON/array fields (esp. Postgres), use Hibernate Types and validate driver version compatibility.
- Regularly audit for transaction and session leaks.

**J. Authoritative References (2025)**

- [Hibernate ORM 7 Documentation](https://docs.jboss.org/hibernate/orm/7.0/userguide/html_single/Hibernate_User_Guide.html)
- [Jakarta Data 1.0 Specification](https://jakarta.ee/specifications/data/1.0/)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/reference/)
- [Best Practices for Spring Data JPA](https://www.javaguides.net/2025/02/top-10-best-practices-for-spring-data.html)
- [QuerySpecification and Type-Safe Queries (Hibernate Blog)](https://www.javacodegeeks.com/2025/09/hibernate-orm-in-2025-modern-data-access-type-safe-queries-and-performance-boosts.html)

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
