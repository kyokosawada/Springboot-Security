# Enterprise Reviewer: Spring Security (2025)

## Table of Contents

1. Introduction to Spring Security
2. Authentication and Authorization Concepts
3. Key Features of Spring Security
    - Authentication
    - Authorization
    - Protecting Web Apps
   - Advanced Session Management
   - Observability, Logging & Compliance
   - Microservices & Distributed Systems
4. Spring Security Architecture
    - Filters
    - Authentication Manager
    - Security Context
    - Filter Ordering
5. Setting up Spring Security: Enterprise Patterns (2025)
6. Authentication
    - Authentication Mechanisms
        - In-Memory Authentication
        - Customizing Login Behavior
        - Authentication with Database
        - OAuth2/OpenID Connect
7. Authorization
    - RBAC: Roles and Permissions
   - ReBAC: Relationship-Based Access Control
    - Method-level Security
    - URL-based Authorization
    - Expression-based Authorization
    - Protecting REST APIs with RBAC
8. Securing REST APIs
    - JWT Authentication for Stateless Auth
        - Setup
        - Token Issuance/Validation
    - CSRF
    - Securing Endpoints
9. Session Management
    - Manage User Session
    - Concurrent Sessions
   - Distributed Session Stores
   - Session Fixation
   - Session Timeouts
   - Microservices Strategies
   - Logout and Revocation Flows
   - Compliance
10. Encryption
    - Password Encoding
    - Data Protection

---

## 1. Introduction to Spring Security

### What is Spring Security?

Spring Security is a comprehensive, modular security framework for Java applications—designed to deliver authentication,
authorization, and protection against the evolving spectrum of modern threats, supporting both traditional web and
reactive applications. It is the security backbone of the Spring ecosystem, deeply integrated with Spring Boot, Spring
MVC, WebFlux, and more.

### Historical Evolution & Importance

- **Born from necessity:** Since its inception in the mid-2000s (initially as Acegi), Spring Security has evolved from a
  simple web perimeter defense into a sophisticated, enterprise-grade platform. It supports session/cookie
  authentication, OAuth2, OpenID Connect, JWT, SAML2, LDAP, multi-factor, passwordless, and more.
- **Open-source DNA and leadership:** Developed by the core Spring team, Spring Security has a vast community, rapid
  iteration, and best-in-class vulnerability response.
- **A decade+ of trust:** It's the standard for Java security in industries from finance to healthcare to modern SaaS.

### Why Enterprises Choose Spring Security (2025 trends)

- **Zero-trust enablement:** Granular, explicit support for least-privilege (RBAC/ABAC), hierarchical roles, dynamic
  policies, and zero-trust patterns.
- **AI-driven threat landscape:** Feature advancements combat advanced persistent threats, credential stuffing, and
  AI/ML-powered attacks, with advanced telemetry hooks for modern SIEM/SOAR tools.
- **Compliance and auditability:** Configurations and policies are explicit, testable, and ready for audit.
- **Microservices & distributed systems:** Full stack compatibility for OAuth2, JWT, OpenFGA, Keycloak; seamless
  integration for API gateways, SSO, and distributed tracing.
- **Reactive & cloud-native:** Designed for high-performance, event-driven, and serverless architectures.

### Architectural Edge

- **Filter chain:** All HTTP requests pass through an ordered set of customizable filters (authentication, CSRF, tenant
  context, SSO, authorization). This makes layering, interleaving, and customizing security straightforward.
- **DelegatingFilterProxy:** Tightly integrated with Spring’s bean lifecycle for plug-and-play filter management.
- **Declarative & programmatic:** Secure with annotations (`@PreAuthorize`, `@Secured`), Java DSL, or classic XML. Adapt
  security to your codebase and compliance needs.

### Core Features Snapshot

- **Authentication:** MFA, SSO, OAuth2, passwordless, biometrics, and enterprise identity integration.
- **Authorization:** Role-based (RBAC), attribute-based (ABAC), policy-driven, URL and method-level, dynamic
  expression-based decisions.
- **Secure session & context:** Stateless JWT or classic session management, robust storage and propagation.
- **Defensive defaults:** Out-of-the-box brute-force defense, CSRF/CORS protection, secure headers (HSTS, X-Frame, CSP),
  secure default cookies/tokens.
- **Password and secrets handling:** Modern password hashing (BCrypt, SCrypt, PBKDF2), environment secret best
  practices.
- **Observability:** Hooks for auditing and monitoring with SIEM/log correlation.

### Security is a Requirement—Not an Option

With rising breach costs, regulatory demands, and supply chain threats, 2025’s enterprises must adopt secure-by-design.
Spring Security is more than code—it's a strategic decision: architectural, organizational, and cultural.

### Key Takeaways

- Use Spring Security to align with the highest bar for security, regulatory, and organizational requirements.
- Layer security concerns below business logic for maintainability and scalability.
- Treat security as a first-class participant from project planning through deployment and lifecycle management.

## 2. Authentication and Authorization Concepts

### Authentication: Proving Identity

Authentication answers “Who are you?”—verifying a user’s or system’s identity before access is considered. Modern
enterprise environments face increasingly sophisticated attacks (AI-driven credential stuffing, phishing, session
hijacking), so robust approaches are required:

- **Single-factor authentication**: Traditional login (username/password). Now discouraged for anything sensitive.
- **Multi-factor authentication (MFA)**: Requires two or more of: something you know (password), something you have (
  TOTP, hardware key, mobile device), something you are (biometrics).
- **Passwordless authentication (WebAuthn, FIDO2)**: Hardware tokens or biometrics, no static password,
  phishing-resistant.
- **Single sign-on (SSO)**: Single authentication point for many systems (using SAML, OpenID Connect, or OAuth2),
  federated identity, minimizing password fatigue.
- **External provider integration**: Link enterprise authentication to IdPs (Azure AD, Okta, Google), enabling strong
  access controls, audit, and streamlined RBAC.
- **Token-based authentication**: JWT, opaque tokens, API keys—stateless, scalable for distributed systems and APIs.

Spring Security enables all of these, offering pluggable authentication providers (LDAP, OAuth2, custom, etc.),
authentication filters, extensible context management, and state-of-the-art support for SSO and MFA flows.

### Authorization: Granting Access

Authorization answers “What can you do?”—enforcing what an authenticated user or system is allowed to access or perform.
Enterprise-grade authorization must defend against privilege escalation, lateral movement, and separation-of-duties
failures:

- **Role-Based Access Control (RBAC)**: Assigns permissions to roles, then roles to users. Supports role hierarchies (
  e.g., ADMIN > MANAGER > USER). Enforced by code, annotations (`@PreAuthorize("hasRole('ADMIN')")`), or config.
- **Attribute-Based Access Control (ABAC)**: Makes decisions using user attributes (department, region, clearance),
  resource attributes, action, and context (adaptive/policy-based). Enables fine-grained, dynamic controls.
- **Policy-based access control (PBAC)/OpenFGA**: Policies as code or externalized authorization (Open Policy Agent,
  OpenFGA) for microservices and APIs.
- **Expression-based & contextual auth**: Custom logic at method or endpoint level (e.g.,
  `@PreAuthorize("hasRole('EDIT') and #owner == authentication.name")`).
- **Least privilege & separation of duties**: Minimize user/system permissions, strictly separate admin/auditor roles,
  rotate credentials, and monitor privilege changes.
- **Zero-Trust**: Never assume trust based on network or location, always authenticate and re-authorize.

Spring Security unifies these with configuration (Java DSL, XML), annotations, and hierarchical or delegated
authorization managers—backed by granular logging and audit trails.

### Best Practices (2025)

- Always implement MFA and prefer passwordless mechanisms for critical systems and APIs.
- Use federated SSO where possible to reduce password reuse and centralize access management.
- Enforce RBAC/ABAC and document all roles, permissions, and policies.
- Treat all requests as potentially hostile, requiring re-validation (zero-trust/API security).
- Rotate secrets/credentials, audit privilege grants/changes, require attestation where possible.
- Leverage Spring Security’s extensibility to future-proof against new authentication/authorization standards and
  threats.

## 3. Key Features of Spring Security

### 3.1 Authentication Deep Dive

Authentication in 2025 is multi-layered, modular, and designed for enterprise rigor and compliance. Spring Security now
provides:

- **Multi-Factor Authentication (MFA):** Can be applied via TOTP (Google Authenticator, Authy), hardware keys (Yubikey,
  FIDO2), biometrics (face, fingerprint), or mobile push. These can be layered on form/login and SSO flows, configurable
  per user, per endpoint, or per business function. Providers integrate via AuthenticationManager chains using modular
  configuration.
- **Passwordless Authentication:** WebAuthn and FIDO2 standards, supporting user/device sign-in without static
  passwords. Prevents phishing attacks, credential stuffing, and compliance headaches for regulated sectors.
- **Single Sign-On (SSO), Federated Identity:** Built-in OpenID Connect, OAuth2, and SAML2 support with enterprise
  IdPs (Azure AD, Okta, Google, AWS Cognito, Keycloak). SSO flows are auditable, support step-up auth, and provide
  centralized control for distributed microservices.
- **Token-based Authentication:** JWT/OAuth2 with advanced, stateless flows. Short-lived tokens (≤15m) for access,
  long-lived HTTP-only refresh tokens (7–30d) for session continuity. Token revocation lists and blacklist caches are
  standard for advanced compliance.
- **Biometric Authentication:** Supported natively via external IdP integration or custom provider chains; biometrics
  can form part of MFA or passwordless patterns for critical apps.
- **Reactive Authentication (WebFlux/R2DBC):** Supports non-blocking, async authentication for high-performance APIs and
  microservices; enables advanced scaling, telemetry, and event-based security control.
- **LDAP/SAML2 and Directory Integration:** Advanced directory federation with attribute mapping, group hierarchies, and
  delegated credential rotation; fully integrated with enterprise audit trails.
- **Custom Authentication Providers:** Modular, extensible—plug in custom business rules, legacy system auth, external
  REST providers, or hybrid composite logic. Each provider can be compliant with SIEM integration, anomaly tracking, and
  incident response hooks.
- **Auditing and Compliance:** Logging of login attempts, success/failure, step-up events, SSO activity, token issuance,
  and revocation; full SIEM compatibility by default. All credential flows are traceable by user, device, and source IP.
- **Security context propagation:** Integration across thread, request, and distributed transaction boundaries, critical
  for cloud and serverless.
- **Common Pitfalls (2025):**
   - Leaving MFA/token revocation disabled in production APIs.
   - Using static passwords or storing secrets outside enterprise vault/HSM.
   - Relying solely on session authentication for distributed cloud-native architectures.
   - Not rotating secrets on compliance events (employee exit, vendor change).
   - Storing authentication state in device localStorage (use secure cookies only).
   - Neglecting login attempt rate-limiting (enable brute-force defense on every endpoint).
  - Not providing detailed audit trails and notifications on user lifecycle and credential events

**Enterprise Patterns:**

- Use AuthenticationManager composition for chaining multiple providers—form login (with/without MFA), SSO, custom
  rules, fallback for legacy interfaces.
- Apply security context at every boundary (database, microservice, event stream)—using reactive pipelines for cloud
  optimizations.
- Centralize configuration with environment-driven secrets, keys, endpoints, and audit. Avoid hardcoding
  credentials/config at all times.
- Always monitor and log login flows, SSO events, credential changes, and authorization failures.
- Provide compliance hooks (audit logs, anomaly events, SIEM signals) as part of the authentication lifecycle.

This enterprise orientation means authentication is not just a technical concern, but a strategic asset—driving
security, compliance, and risk management across every line of business.

### 3.2 Authorization Deep Dive

Authorization in 2025 is dynamic, fine-grained, context-aware, and enterprise-compliant. Spring Security’s model is:

- **Role-Based Access Control (RBAC):** Assigns permissions to roles, then roles to users. Enterprise-grade RBAC uses
  explicit and auditable authority mappings, including hierarchical assignments (e.g., ADMIN > MANAGER > USER), all
  versioned and testable for compliance. Annotation-based
  enforcement (`@PreAuthorize("hasRole('ADMIN')")`, `@Secured`, DSL config) ensures maintainability, and
  enterprise policy is now driven by explicit authority aggregation, not magic, "hidden" hierarchies.

### ABAC: Attribute-Driven, Contextual Enterprise Policy

Modern enterprises require fine-grained, dynamic access policies leveraging user, environment, resource, and contextual
attributes. ABAC enables highly adaptive security, centralizing and automating control for increasingly complex business
and regulatory needs.

- **Policy Authoring and Centralization:** Author ABAC policies in code, YAML, or external engines (OPA/OpenFGA), using
  attributes such as user department, region, employment type, device class, zone, geo-location, risk score, or
  data/resource sensitivity. For large-scale/regulated domains, ABAC rules can be managed in a central service (policy
  engine) and versioned/tested independently.
- **SpEL/DSL Dynamic Rules:** Use SpEL expressions in `@PreAuthorize`, `@PostAuthorize`, custom DSL, or
  SecurityFilterChain `.access()` methods for multi-attribute decisions. Example:
  `@PreAuthorize("hasRole('MANAGER') and principal.department == 'FINANCE' and #doc.sensitivity == 'CONFIDENTIAL' and principal.deviceType == 'CORPORATE'")`
  or
  `.requestMatchers("/sensitive/*").access("hasAuthority('SECURE') and principal.region == 'EU' and principal.geoRiskScore < 100")`.
- **Multi-Attribute Enforcement:** Combine several attributes—user, device, resource, time, and context—into composite
  conditions. E.g., "Manager AND in region X AND on managed device AND between 09:00–17:00 AND risk score < threshold."
- **Context Populators & Attribute Injection:** Populate authentication principals with attributes from HRIS (HR
  system), device inventory/MDM, zone tagging, or other enterprise sources. Attributes may be mapped from JWT/OIDC
  claims, SAML assertions, LDAP/AD profiles, or custom directories. Use custom
  JwtAuthenticationConverter/OAuth2UserService/SAML attribute mappers for standardized, trusted population.
- **Integration Patterns:** ABAC integrates with central HR, device, and zone systems for real-time policy adaptation.
  Upon department or risk status change (e.g., HRIS update), access can be auto-revoked or flagged for review. Device
  and location inventory can restrict access to managed or sanctioned endpoints.
- **Regulated Industry Requirements:** For finance, healthcare, government, and other regulated sectors, ABAC is used to
  enforce separation-of-duty, geo-fencing, temporal controls, and fine-grained privilege assignment. Attribute changes (
  department, employment status, device integrity) must trigger immediate access review or revocation.
- **Audit and Policy Traceability:** Every dynamic attribute evaluation is logged with input values, rule details,
  decision outcome, and source system. Attribute and policy changes are versioned and traceable, supporting compliance
  and post-incident forensics.
- **Testing and Compliance Assurance:** Enterprises must routinely test ABAC logic for edge cases (
  missing/null/malicious/outdated attributes), simulate policy change effects, and audit enforcement after each
  code/policy update. Attribute integrity and trust level must be validated for every integrated system.
- **Centralized Rule Management:** For large organizations, ABAC rules should be centrally reviewed, documented, and
  managed; policy-as-code approaches (OPA/OpenFGA) provide automation, audit, CI/CD testing, and scalable multi-tenant
  deployment. Attribute schemas and mapping strategies should be type-safe, versioned, and strictly tested.
- **Integration with OIDC/SAML/HR Systems:** Populate ABAC attributes automatically from OIDC/JWT claims, SAML assertion
  fields, or direct HRIS/device APIs for high confidence and consistency. Ensure fallback/default behaviors for missing
  sources.

Sample enterprise ABAC rule:

```java
@PreAuthorize("hasRole('SUPPORT') and #ticket.customerRegion == principal.region and principal.hrRiskScore < 50 and principal.deviceIntegrity == 'TRUSTED' and T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).parse('08:00')) and T(java.time.LocalTime).now().isBefore(T(java.time.LocalTime).parse('18:00'))")
public void approveSupportTicket(Ticket ticket) { ... }
```

Or via SecurityFilterChain DSL:

```java
.requestMatchers("/finance/**").access("hasAuthority('FINANCE_USER') and principal.department == 'Finance' and principal.zone == 'EU' and principal.hrClearanceLevel >= 3")
```

**Best Practices**:

- Centralize and version ABAC policies; automate audits and recertification.
- Rigorously test all externally sourced attributes for trust, correctness, and completeness.
- Log and trace all access decisions with corresponding attribute values and rule identifiers.
- Collaborate with risk and compliance teams for rule updates; simulate and document change impact.
- Provide APIs or UIs for policy visibility and attribute provenance.

### PBAC: Externalized, Policy-as-Code, and Zero-Trust Enforcement

Policy-Based Access Control (PBAC) centralizes and externalizes all access decisions, enabling code-independent,
testable, and rapidly updatable policy management—critical for microservices, zero-trust, and regulated architectures.

- **External Policy Engine Integration:**
   - Integrate Spring Security with external PDPs (Open Policy Agent—OPA, OpenFGA, custom policy microservices) via API,
     service mesh, or sidecar.
   - On authorization event, Spring consults the PDP—passing the current principal/attributes/resource/action/context as
     a structured payload—and receives an ALLOW/DENY/data-driven response.
   - PDP is configured via policy-as-code (Rego for OPA, JSON/YAML for OpenFGA, enterprise DSL for custom engines),
     allowing security, risk, and compliance teams to author/test/deploy policy outside the codebase.

- **Policy Versioning & CI/CD:**
   - All policies are versioned, tested independently, and deployed using CI/CD, with automated regression and
     compliance policy tests.
   - Simulate, review, and approve policy changes before runtime deployment (gated policy pipelines per
     business/region).

- **Runtime Policy Reload/Cache:**
   - Support for dynamic runtime policy reload (e.g., in response to compliance events or regulatory change) without
     code redeployment; policy engine caches latest valid policy state.

- **Distributed/Microservices Enforcement:**
   - Each microservice or gateway calls the central PDP for coarse- and fine-grained policy checks—allowing global
     change control, rapid incident response, and unified audit across distributed services.

- **Audit, Change, and Decision Logging:**
   - Every policy decision is logged/traced with input, policy version/hash, outcome, and contextual principal/resource
     state. Policy change events are traceable to deployment and author.
   - Privileged/deny (and especially allow-privilege-escalation) decisions raise review events and are reviewed by
     security/ops/compliance.

- **Failover and Error Handling:**
   - If the external PDP is unreachable or policy is in error, fail securely: deny access by default, alert
     operations/security teams, and provide fallback audit trail. Never default to ALLOW without incident escalation and
     observability.
   - Implement retry, circuit-breaker, and configurable failover/fallback policy for dependent services.

- **Multi-Tenant and Zero-Trust Patterns:**
   - Each tenant, business unit, or regulatory zone may use its own delegated policy set, ensuring policy boundaries and
     compliance separation. Policies can enforce least privilege, time/location/risk-based conditions, step-up,
     break-glass, or third-party risk adaptations.

- **Hybrid Patterns:**
   - PBAC can delegate or supplement RBAC/ABAC—e.g., perform quick role checks natively, then defer edge-case/resource
     authorization to external PDP for complex, rapidly changing, or regulated scenarios.
   - In hybrid models, RBAC/ABAC handles simple or high-performance paths, while PBAC governs sensitive flows, privilege
     escalation, or approval/review-required actions.

- **Policy Lifecycle and Operations:**
   - Policies as code are maintained in version control, with pull requests, peer review, approval, and automated
     regression and compliance testing before activation.
   - All policy and PDP configurations are audited, versioned, and observable—trace every policy decision to policy
     version, input, and actor.
   - Integration with compliance, risk, and audit teams is embedded in the SDLC—compliance attestation, exception
     handling, and policy re-certification.

- **Distributed Microservices & Service Mesh Enforcement:**
   - Deploy PBAC at the API gateway, sidecar, or service mesh gateway to intercept and enforce policies across all
     ingress/egress points in a distributed environment.
   - Service-to-service policy checks can use mutual TLS identity or authenticated JWT tokens/claims as PDP input.
   - Supports runtime policy reloading for incident response, regulatory shifts, or business logic changes—no service
     restart needed.

- **Sample PBAC Integration in Spring:**

```java
// (Pseudo) in a CustomAuthorizationManager, Filter, or MethodSecurityInterceptor
auditor.logPbacCheck(principal, resource, action, attributes);
Decision decision = externalPolicyService.evaluate("allow-access", context);
if (!decision.isAllow()) throw new AccessDeniedException("PBAC policy enforcement");

// Example with OPA REST API:
WebClient webClient = WebClient.builder().baseUrl(opaUrl).build();
Mono<Decision> decision = webClient.post()
    .uri("/v1/data/app/allow")
    .bodyValue(Map.of(
        "input", Map.of(
            "principal", principal,
            "action", action,
            "resource", resource,
            "attributes", attributes
        )
    ))
    .retrieve()
    .bodyToMono(Decision.class);
// Decision evaluated async, with logging, tracing, and policy version/hashes recorded.
```

- **CI/CD Best Practices and Auditable Policy Lifecycle:**
   - Policies must be validated in CI/CD with unit/regression tests and policy regression suites.
   - Automate detection of policy drift, unintended consequences, and privileged bypass scenarios.
   - Use policy simulation (“what-if”) tools for safe review prior to cutover.
   - Maintain full, immutable audit logs for all policy changes, policy engine upgrades, and operational overrides.

- **Enterprise Best Practices:**
   - All policy and policy engine config must be versioned, fully auditable, and tested before/after deployment.
   - Integrate automated regression tests, policy drift detection, and emergency policy rollback in deployment
     pipelines.
   - Delegate non-critical/low-risk checks to native RBAC/ABAC for performance; reserve PBAC for critical, rapidly
     changing, or granular controls.
   - Regularly review privileged/allow decisions—especially those requested out-of-band or triggered by
     AppSec/compliance exceptions.
   - Collaborate with risk/compliance teams for all major policy or engine changes and maintain transparency for
     reviews.
   - Enforce deny-by-default at all boundary layers and ensure clear, actionable incident escalation for policy/PDP
     outages or errors.
- **Relationship-Based Access Control (ReBAC):** Modern enterprise apps adopt relationship-driven models, enforcing
  access based on organizations, teams, project ownership, or dynamic social graph relationships. This is supported by
  emerging features in Spring Security and external PDPs—critical for collaborative SaaS platforms.

**Enforcement Patterns:**

- Enforce authorization at every boundary: HTTP layer with SecurityFilterChain DSL (`requestMatchers`, path patterns,
  composite policies), method-level with annotations (`@PreAuthorize`, `@PostAuthorize`, `@Secured`, and advanced SpEL),
  and domain-layer via beans or service-level checks.
- Dynamically compose security conditions for multi-factor business rules (e.g., “Manager AND in branch X AND requested
  between 9-5 with valid clearance”).
- Delegate complex or multi-tenant rules to external PDPs—integrate with OpenFGA, OPA, and distributed microservices
  config for unified, scalable policy.

**Auditing, Logging & Compliance:**

- Every authorization event is auditable: decision logs, access denials, privilege elevations, role grants/changes, and
  boundary crossings are recorded for compliance. Spring Security natively emits events for SIEM/SOAR tools and supports
  detailed event listeners.
- Lifecycle tests are mandatory—every role, permission, and policy changes must be documented and tested for real-time
  compliance.
- Separation of duties and least privilege are enforced via role mapping and regular credential/audit reviews—automated
  wherever possible.
- Explicit “deny by default” policy for endpoint and method protection, never relying on implicit allow.

**Edge Cases and Enterprise Enhancements:**

- Multi-tenancy: Authorization context propagates across tenant boundaries with dynamic claims or tenancy-aware JWT/JWK
  selectors.
- Microservices: Distributed enforcement via sidecar, API gateway, or service mesh architecture; supports
  service-to-service mutual TLS and metadata claims.
- Contextual enforcement: Composite policies for cross-cutting concerns (location, risk, device, time-of-day), adaptive
  to AI/ML-driven environments and modern compliance.

Spring Security delivers all these via modular configuration, annotation support, Java DSL, external integrations, and
robust logging—a strategic layer underneath every enterprise business logic and data access path.

### 3.3 Protecting Web Apps: 2025 Enterprise Deep Dive

Protecting modern web applications is a multi-layered, ever-evolving challenge. The 2025 enterprise standard covers not
just classic server-rendered apps, but also:

- SPA (Single-Page Applications)
- Hybrid (server + client-side rendering)
- Mobile-first/browserless clients
- Distributed and zero-trust architectures

#### 🔒 CSRF (Cross-Site Request Forgery) Advanced Strategies

- **Default:** Spring Security enforces CSRF for all browser-facing, session-based endpoints.
- **SPA/REST:** If APIs are stateless (JWT/OAuth), disable CSRF but audit endpoints for risks.
- **Trusted Tokens:** Always bind CSRF tokens to session or user context. Use Header-based (e.g., `X-CSRF-TOKEN`) for
  AJAX/fetch, and Cookie-based for forms.
- **Untrusted channels:** Never expose CSRF tokens to JavaScript except on trusted origins. Use dual-cookie pattern for
  SPAs with HttpOnly flag where possible.
- **Modern Config Example:**

```java
http.csrf(csrf -> csrf
  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
);
```

- **For SPAs:** Protect login and privileged endpoints (refresh, critical ops) even if main API is stateless!

#### 🌍 Fine-Grained CORS Management

- **Explicit Origin Whitelisting:** Only allow trusted, known origins (don’t use '*').
- **Credentialed CORS:** Require credentials flag for cookies/auth:

```java
http.cors(cors -> cors.configurationSource(request -> {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("https://app.example.com"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    config.setAllowCredentials(true); // Needed for cookies/JWT
    config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-CSRF-TOKEN"));
    return config;
}));
```

- **Preflight Restrictions:** Restrict allowed headers/methods for OPTIONS/prelight; never leak internal APIs.
- **Audit for 'wildcard' or legacy CORS headers.**

#### 🛡️ Advanced HTTP Security Headers

- **Content Security Policy (CSP):** Block XSS, control sources for scripts/assets.
    - Example:
        - `Content-Security-Policy: default-src 'self'; script-src 'self' https://trusted.cdn.com;`
    - Use Spring’s `headers().contentSecurityPolicy(...)` DSL.
- **X-Frame-Options:** Prevent clickjacking (`DENY` or `SAMEORIGIN` config).
- **Referrer-Policy, Permissions-Policy, X-Content-Type-Options:** Minimize data/attack surface.
- **Strict-Transport-Security:** Enforce HTTPS at all times.
- **Spring Config Example:**

```java
http.headers(headers -> headers
    .contentSecurityPolicy("default-src 'self'; script-src 'self' ...")
    .frameOptions(FrameOptionsConfig::sameOrigin)
    .xssProtection(XXssProtectionConfig::block)
    .referrerPolicy((policy) -> policy.policy(ReferrerPolicy.SAME_ORIGIN))
);
```

#### 🪪 Session Management in SPA & Hybrid Flows

- **Session Rotation on Login/Elevation:** Always use `sessionFixation().migrateSession()` on login/privilege change.
- **Spring Boot Config:**
    - `server.servlet.session.timeout=15m` (tune to risk/UX)
- **Logout/Revocation:** Always enforce server-side logout; for JWT, use blacklist or issue new signing key as needed.
- **SPAs:**
    - Short-lived access tokens (5–15m) and long-lived refresh tokens (cookie, secure storage).
    - Silent token refresh logic: App detects expired token, auto-refreshes in background, never blocks user.
    - Use HttpOnly/Secure/SameSite cookies for refresh tokens wherever possible (never localStorage for secrets).
    - Support "global logout" flow: triggers token revocation/device lock, propagating to all app tabs/devices.
- **Multi-tab/session-aware:** Sync logout/token invalidation across all tabs/devices via BroadcastChannel, service
  worker, or polling patterns.

#### ⚡️ Authentication Flows for SPA/Mobile

- **OIDC/OAuth2:**
    - Use PKCE for SPA and mobile flows.
    - Customize consent pages for phishing/hijack protection.
- **Token Storage:**
    - Never store tokens in localStorage if possible; prefer cookies or secure platform storage.
- **Silent Authentication:**
    - Detect token expiry and seamlessly refresh with refresh token (never prompt user for password mid-session).
- **Single Sign-Out (SSO):**
    - Integrate logout propagation with SSO/IdP and app backend logout endpoint.

####🤖 Anti-Automation & Abuse Mitigation

- Distributed rate-limiting (at gateway and endpoint level); respond with 429 for excessive calls.
- Bot/probe detection: Add CAPTCHA, device fingerprint, or risk scoring for login/register flows.
- Enforce exponential backoff or account lock after bad login attempts.
- Monitor for credential stuffing, replay, brute-force attempts; integrate signals into monitoring and SIEM.

#### 🧑‍💻 Platform-Specific Considerations

- **Mobile Web:** Use secure keychain/secure enclave for credentials/tokens.
- **Desktop Apps:** Use OS-provided credential stores.
- **Cross-Domain:** For federated login, never allow implicit grant; favor auth code or device grant.
- **Third-Party Integrations:** Limit delegated token scopes, rotate secrets, and use consent screens for user clarity.

#### 📈 Observability & Compliance

- Integrate security alerts/logs with SIEM/SOAR platforms.
- Log all access denials, privilege escalations, and admin operations with correlationID/sessionID.
- Trace policy violations or anomalous flows in distributed tracing (OpenTelemetry, Zipkin, etc).
- Automate incident review, and enable replay/reconstruction for post-incident learning.

#### 💎 Summary Checklist

- [ ] CSRF protection (all flows)
- [ ] CORS strictness (explicit origins, methods, credentials)
- [ ] All relevant HTTP security headers set
- [ ] Session management: rotation, timeout, invalidation, forced logout
- [ ] Robust auth flows: short-lived JWTs, secure refresh, OIDC
- [ ] Anti-bot, rate limiting, abuse defense
- [ ] Security observability & actionable alerting
- [ ] Platform-adaptive best practices (SPA/mobile/desktop)

---

### 3.4 Advanced Session Management

Modern session management is mandatory for compliance, privacy, and zero-trust operational resilience.

- **Stateless Sessions (JWT):** For REST APIs and microservices, all authentication and security context is stored in
  short-lived JWTs. There is no server-side session state; refresh tokens are kept in secure HTTP-only cookies or
  ephemeral storage. Token revocation (blacklisting), device binding, and key rotation are crucial practices.
- **Stateful Sessions:** For legacy web, admin UIs, or where user experience requires, classic HttpSession support
  persists context at the server (using in-memory, JDBC, Redis, or distributed caches). All sensitive cookies must be
  flagged HttpOnly, Secure, and SameSite. Session fixation protection is enforced by default; new session IDs are
  generated on login. Custom invalidSession and session-expired flows are recommended.
- **Distributed Session Stores:** For cloud/hybrid environments and scale-out architectures, sessions can be backed by
  Redis or JDBC (via Spring Session). This supports failover, horizontal scaling, and centralized session
  invalidation—crucial for compliance-triggered device or user logout scenarios.
- **Concurrent Session Control:** Limit number of sessions per user, per app, and enable programmatic session expiration
  and tracking. This defeats account sharing and supports compliance-mandated "remote logout from all devices." Spring
  Security provides per-user session registries and hooks for security events.
- **Session Revocation/Logout:** All logout events propagate (with web hooks or distributed store/blacklist) across all
  nodes and services; JWT blacklists and session registries are synced instantly in the event of breach or compliance
  event. Token invalidation is enterprise-default; forced logout flows are fully auditable.
- **Session Timeout and Rotation:** Idle and max session lifetime settings are tuned for risk profile (short for public
  APIs, longer for secure admin). Idle sessions are killed at the storage layer with SIEM/monitoring audit logs.
- **Microservices Environment:** Service-to-service authentication never relies on sticky sessions; tokens, mutual TLS,
  or API keys are used. Distributed tracing and context propagation must be fully integrated.
- **Compliance:** Every session change, logout, invalidation, or privilege elevation event is audited. Security context
  is never cached or persisted outside secure session stores or encrypted JWT claims. Regular key rotation is enforced
  throughout.

Spring Security session management now covers everything from cookie/session fixation, to full device logout, scale-out
distributed microservices, and enterprise audit compliance.

## 4. Spring Security Architecture

### Filters

### Filters: The Foundation of Enterprise Security Flows

At the heart of Spring Security’s architecture lies the security filter chain—a sequence of discrete, customizable
interceptors that process every HTTP request. This structure yields uncompromising flexibility for enterprise security
designs:

- **Built-in Filters:** The out-of-the-box stack includes UsernamePasswordAuthenticationFilter, JwtAuthenticationFilter,
  BasicAuthenticationFilter, CsrfFilter, ExceptionTranslationFilter, OncePerRequestFilter subtypes, and more—each with
  clear responsibilities (authentication, CSRF, token validation, session management, error translation, logging).
- **Custom Filter Injection:** Enterprise use cases often require custom policies or telemetry—add these via
  `addFilterBefore` or `addFilterAfter` in the chain. Examples include device fingerprint checks, adaptive risk scoring,
  custom MFA, request tracing, or geofencing. Custom filters should be stateless, thread-safe, and emit observable
  events for audit.
- **Event Emission:** Each filter can generate security events (success, failure, anomalous input, access denied),
  feeding logging systems, SIEM/SOAR, or monitoring dashboards. Enterprise deployments tie these directly to incident
  response or compliance mechanisms.
- **Filter Ordering:** Chain order is explicit and crucial—incorrect placement can break request authentication, leak
  context, or defeat CSRF. Spring Security allows explicit ordering and enforces semantic guardrails (critical filters
  can’t be short-circuited). For complex scenarios (APIs vs. admin), use multiple SecurityFilterChain beans, each with
  distinct order and request matcher.
- **Multi-Tenancy and Multi-Chain:** Architectures supporting separate security flows for different business units,
  tenants, or domains leverage multi-chain configuration, allowing policy divergence, compliance segmentation, and
  scalable zero-trust enforcement.
- **Servlet vs. WebFlux:** Classical servlet stacks utilize servlet Filters; reactive (WebFlux) uses WebFilter and
  implements a non-blocking processing chain. Both models support composable, modular filter chains with custom
  extension points and order control.
- **Context Propagation:** Filters are responsible for establishing and propagating the security context. In
  coroutine/reactive contexts, this includes context snapshotting and restoring, preventing context-loss across async
  boundaries, thread handoffs, or distributed/cloud-native flows.
- **Enterprise Guardrails:** Always monitor for filter drift, accidental bypass, or policy drift in large projects.
  Regularly audit the effective order, presence, and output of every filter; enforce test coverage and validation on
  custom filter logic.

This filter chain model underpins both Spring’s security guarantees and its unique ability to adapt to complex,
enterprise-class deployments at any scale or compliance bar.

### Authentication Manager

The AuthenticationManager is the nerve-center of Spring Security’s authentication system—responsible for handling,
delegating, and chaining authentication flows for everything from passwords to SSO, OAuth2, and biometrics:

- **Compositional Provider Chain:** AuthenticationManager orchestrates a configurable chain of AuthenticationProvider
  instances (DAO, LDAP, SAML, OAuth2, JWT, WebAuthn, custom, etc.), each able to process a subset of authentication
  requests. Providers are invoked by priority—short-circuiting for the first successful authentication or raising
  granular errors for audit/logging.
- **SSO, Federated, and Legacy Integration:** Modular providers support seamless enterprise SSO (SAML2, OIDC, OAuth2
  clients like Google, Okta, Keycloak), directory integration (LDAP/AD), or legacy authentication (remote REST,
  mainframe, custom hashing). Each can be enabled, disabled, or reordered as business and compliance requirements
  evolve.
- **Custom AuthenticationProviders:** Enterprises can author custom providers for specialty business flows, legacy
  systems, advanced MFA, or biometric validation. Custom providers should implement the AuthenticationProvider
  interface, emit detailed authentication events, capture context, and integrate with rate-limiting and anomaly
  detection.
- **Fallback and Error Handling:** Providers may be chained for failover (e.g., first try external IdP, then fallback to
  local DB for admins). All failures are logged with rich context for monitoring/SIEM, while successful flows are
  annotated for compliance. Exception flows are never silent—raising events on lockouts, expiring secrets, or breach
  attempts.
- **Programmatic, Modular Registration:** AuthenticationManager and its providers are registered and configured as
  beans, supporting environment-driven composition (per-tenant, per-app, per-region). Full programmatic definition and
  runtime reconfiguration is supported for cloud-native platforms.
- **Async/Scale-out Capability:** In reactive (WebFlux) or massively scalable architectures, authentication can be
  async/non-blocking, supporting parallel provider chains for multi-region or global deployments. All context
  transmission is validated.
- **Enterprise Patterns:**
   - Chain for least-privilege, composable policy (e.g., SSO for users, legacy for admins, device auth for service
     accounts).
   - Separate provider beans and configuration per security context, tenant, or application.
   - Auditable, observable event emission on success/failure. Integrate with SIEM/SOAR.
   - Always rotate credentials/providers on compliance events.

In summary: The AuthenticationManager offers both rigorous security and flexibility, ensuring that authentication
strategy matches enterprise risk posture, scale, and regulatory requirement at every turn.

### Security Context: Propagation, Visibility, and Enterprise Boundaries

The Security Context is Spring Security’s canonical store of a user’s authentication and authority status for every
request and operation. In modern enterprise environments, secure and accurate propagation is vital:

- **Thread Context (Servlet):** In classic servlet stacks, SecurityContextHolder uses a ThreadLocal (or
  InheritableThreadLocal) by default. This ensures per-request isolation, propagates via thread pools, and is reset at
  request completion. InheritableThreadLocal is useful for child-threads spawned from the main request but should be
  used cautiously to prevent context leakage.
- **Async/Reactive (WebFlux, coroutines):** With async flows, futures, reactors, and coroutines, context propagation
  cannot rely on thread identity. Spring Security supports context bridges (ReactorContext, Project Reactor Hooks,
  coroutines context) to transmit security context across schedulers and event loops. Always configure and validate
  secure context handoff in custom async/executor scenarios to avoid context loss or privilege leaks.
- **Cross-Request/Distributed Flows:** For distributed tracing and cross-request operations (batch, messaging,
  serverless, cloud-native), the context can be serialized and transmitted explicitly—never implicitly—via secure tokens
  or headers. Always validate at every ingress. Multi-tenant applications extend the base context with
  tenant/zone/account-region claims for granular policy enforcement and audit.
- **Context Loss/Recovery:** Loss of context in parallel/async code is a top source of security holes. Always test
  custom parallelism, observables/publishers, and third-party frameworks for context preservation. Use audit trails to
  monitor unexpected context loss and privilege changes.
- **Audit and Tracing:** Every privilege elevation, context change (e.g., run-as, impersonate), or context handoff
  across service boundaries is logged and auditable for compliance.
- **Best Practices:**
   - Use thread-local for blocking code, ReactorContext or equivalent for non-blocking/reactive flows.
   - Never cache or share security context outside the request-lifetime for stateless APIs.
   - For serverless and cloud functions, bootstrap context per-invocation based on secure token identity.
   - Multi-tenant apps must enforce explicit context extension at every controller/service/data boundary.

This model is the backbone of all authorization, session, and audit flows in a regulated, distributed, or cloud-native
Spring Security deployment.

### Filter Ordering

### Filter Ordering: Precision, Compliance, and Operational Control

Correct ordering of filters is an enterprise security prerequisite—one misplaced filter can render critical controls (
authentication, CSRF, audit) moot. Modern Spring Security supports robust, fine-grained ordering and chain partitioning:

- **Explicit Order Control:** Each SecurityFilterChain and filter is assigned an @Order or explicit order value. Spring
  evaluates chains in the order defined—first matching chain for a given endpoint handles the request, and filter
  execution proceeds top-down. Filtering precedence determines whether authentication, authorization, CSRF, or exception
  handling comes first for every route.
- **Multiple Filter Chains:** Use multiple SecurityFilterChain beans to segment policies for groups of endpoints (e.g.,
  public APIs, admin UIs, management interfaces, tenant-specific resources). Each chain can have unique ordering and
  matchers—ensuring strict segregation and compliance.
- **Endpoint Targeting:** Filter and chain ordering is tightly coupled with endpoint matchers and security context
  boundaries. Enterprise systems may layer "defense in depth" using dedicated high-priority chains for sensitive
  business processes (payments, user management, admin flows), and secondary chains for less-sensitive routes.
- **Cross-cutting Policy Enforcement:** Filters enforcing enterprise requirements (SLA enforcement, geo-restrictions,
  data residency) must always appear in the earliest applicable location in the chain, before business logic
  interception or context upgrade occurs.
- **Session and Compliance Flows:** Cookie, CSRF, and session management filters require tightly controlled order
  placement—always before controllers access user state. Regular compliance audits should review and verify filter order
  is unchanged from intended design.
- **Testing and Verification:** Enterprise CI/CD pipelines should include policy and filter order validation, using
  integration tests, order assertions, and auditing filter chain composition for every chain and route. Automated
  security testing tools are recommended to detect drift or bypass in the chain.

This discipline ensures reliable, repeatable, and auditable enforcement of all security policies, positioning Spring
Security as a compliance and operational security backbone in critical applications.

### Modern Extensions, Async, and Cloud-Native Security

Enterprise deployments extend Spring Security’s core design—enabling high-scale, distributed, multi-tenant, and
cloud-native systems:

- **WebFlux/Reactive/Coroutine Patterns:** Modern applications leverage non-blocking, event-driven, or coroutine-based
  models. In these flows, security context bridges (e.g., ReactorContext, coroutine context elements) store and transmit
  context alongside the execution chain, preventing information leakage and privilege drift across scheduler boundaries.
- **Async/Serverless Security:** Authentication and authorization logic can execute outside of the HTTP servlet
  lifecycle—in function-as-a-service (FaaS), message-driven consumers, and scheduled batch jobs. Always bootstrap and
  validate context from secure, signed tokens or communication channels at every invocation.
- **Multi-Region and Multi-Tenancy:** Each region/tenant may register distinct SecurityFilterChains,
  AuthenticationManagers, and external PDP endpoints—enabling policy segregation, compliance zoning, and regional
  autonomy while maintaining minimal, testable surface for attack.
- **External Policy Decision Delegation:** Enterprise platforms increasingly offload core authorization to OpenFGA, OPA,
  or custom policy engines. Filter chains include sidecar or API-call-based PDP filters, with robust failover and audit
  trails for policy upgrades/changes.
- **Runtime Policy and Filter Chain Adaptation:** Policy chains can be reconfigured in response to compliance events (
  breach, regulatory change) or changing business need, using centralized environment-driven configuration and automated
  CI/CD validation.
- **Distributed Tracing and SIEM Integration:** Every filter and context transmission emits traceable spans, logs, or
  telemetry for end-to-end monitoring, incident response, and root cause analysis in large-scale ecosystems.

Best-practice enterprise solutions now address security architecture as a living, adaptive mesh—capable of both
defending complex digital estates in 2025 and enabling audit, agility, and risk management for strategic advantage.

## 5. Setting up Spring Security: Enterprise Patterns (2025)

To set up Spring Security for enterprise use, follow these modern best practices:

### 1. Dependency and BOM Management

- **Maven/Gradle BOM**: Use the latest Spring Boot/Security BOM (bill of materials) to ensure version consistency:
   - Maven: `<dependencyManagement>` with `spring-boot-dependencies` and `spring-security-bom`.
   - Gradle: `io.spring.dependency-management` plugin with imported BOM.
- **Selective Starters/Modules**: Only include what you use—e.g.,
   - `spring-boot-starter-security` for core web security.
   - `spring-security-oauth2-resource-server`, `spring-security-oauth2-client` for OAuth2, JWT, SSO.
   - `spring-security-saml2-service-provider`, `spring-security-ldap` as needed.
- **React/Hybrid**: For reactive stacks, use `spring-boot-starter-webflux`,
  `spring-boot-starter-oauth2-resource-server`, etc.
- **Version Pinning**: Enforce rigorous version management to reduce supply-chain risk and maximize auditability for
  regulated environments.

### 2. Modular and Multi-Chain Filter Configuration

- **Multiple SecurityFilterChains**: Register distinct SecurityFilterChain beans for:
   - API endpoints (stateless/JWT/OAuth2)
   - Admin interfaces (session-based, stricter policies)
   - Tenants/regions/custom flows
- **Explicit Ordering and Matcher Segregation**: Carefully order and scope each chain by endpoint pattern, regulatory
  boundary, or business function.
- **Custom Providers and Beans**: Modularize UserDetailsService, AuthenticationProviders, password encoders, session
  registries, and PDP/OPA connectors. Inject via configuration, never as static or hardcoded references.
- **Policy Segregation**: Document and separate security logic for different domains/business units/tenant zones.

### 3. Secure Environment, Vault, and Secret Handling

- **Environment Variables**: All secrets, encryption keys, and sensitive config are injected via environment variables
  or secure parameters, never in code.
- **Enterprise Vaults**: Integrate with central secret managers (Spring Cloud Vault, AWS KMS, Azure Key Vault, GCP
  Secret Manager) for runtime loading and rotation. Keys can be tenant/zone-specific.
- **Local Dev vs. Production**: Use sample or throwaway secrets for local/testing, with environment-driven switching to
  production-grade secrets. Never commit or include secrets in artifacts/source.
- **Rotation Policy**: Implement rotation hooks for compliance/incident events.

### 4. Plug-and-Play Identity Provider Setup

- **OIDC/SAML/LDAP/SSO**: Enable social login, enterprise SSO, or B2B/B2C logins with simple property- or YAML-driven
  config tied to Spring Security’s Identity Provider modules. Ensure callback URLs, allowed origins, and scopes are
  explicit and documented. Prioritize federated login for compliance, auto-provisioning, and enterprise audit.
- **Provider Fallback/Failover**: Configure fallback providers in AuthenticationManager for resilient multi-provider
  architectures. Document what users/applications use each provider and under which scenarios.
- **Policy Discovery and Documentation**: Store policy/metadata for every provider, mapped to endpoint
  resource/chain/business context.

### 5. Audit, CI/CD, and Configuration Documentation

- **Audit Trails**: Log and version-control all changes to security config, especially secrets, provider URLs, and
  policy logic. Use gitops/infrastructure-as-code models for security config deployment.
- **CI/CD Hardening**: Enforce rules in pipelines to scan for hardcoded API keys/secrets before merge/deployment.
  Programmatically diff security-related config changes for compliance review.
- **Setup Documentation**: Maintain documentation of all security config decisions, vault integrations, policy
  boundaries, and approved service endpoints for compliance, privacy, and disaster response.
- **Readiness Reviews**: Schedule and document readiness reviews/testing for key security upgrades, dependency changes,
  or provider migrations.

## 6. Authentication

### Authentication Mechanisms

#### In-Memory Authentication: For Prototypes & Emergency Use Only

Enterprise-grade Spring Security deployments should treat in-memory authentication as a prototyping/testing facility—not
for production flows. All credentials are stored in memory, lacking audit, life-cycle control, or breach response. Only
use for quick demos, local admin backdoors (with strong secrets & audit logs), or fully isolated CI/CDE environments.

- **Initialization & Policy:** User definitions are hardcoded in config or YAML (never in shared code repos), and should
  use upgradable password encoders for all hashes. Always generate high-entropy random passwords for in-memory accounts.
- **Password Encoding:** Even in-memory users should use a DelegatingPasswordEncoder, ensuring password
  upgrades/rotation for every role.
- **Audit Trail:** Initialization and login/logout for in-memory users should emit distinct audit events. Lock out or
  rotate in-memory passwords after every compliance/sensitive change.

Sample pattern:

```java
@Bean
public UserDetailsService userDetailsService(PasswordEncoder encoder) {
  return new InMemoryUserDetailsManager(
    User.withUsername("admin")
        .password(encoder.encode(System.getenv("ADMIN_SECRET")))
        .roles("ADMIN")
        .build(),
    User.withUsername("support")
        .password(encoder.encode(System.getenv("SUPPORT_SECRET")))
        .roles("SUPPORT")
        .build()
  );
}
```

**Enterprise Best Practices:**

- Rotate or disable in-memory accounts for every major deployment/audit.
- Never rely on in-memory auth for customer or privileged business data.
- Emit audit warnings for regular use in production or when special admin logins succeed.

#### Customizing Login Behavior: Secure, Branded, and Auditable

Enterprise-grade login flows demand robust security, tailored user experience, and compliance-grade observability.

- **Custom Pages & Branding:** Login forms, error pages, and success redirects should reflect organizational branding,
  accessibility, and security posture.
- **Advanced Error Handling:** Handle login failures with clarity (wrong password, locked account, expired credentials,
  suspicious device). Log every failure and reason; never leak sensitive info (valid usernames, internal error traces).
- **Multi-factor Hooks:** Extend login with MFA slots (TOTP, push notification, biometrics, hardware token/webauthn)
  before allowing access. Integrate with AuthenticationManager to ensure hooks are composable via provider chain.
- **Device & Pre-Auth Checks:** Inject pre-login filters to detect device fingerprint, geo-restrictions, browser
  integrity, or custom risk scoring prior to login form rendering/processing.
- **Throttling & Rate Limiting:** Protect endpoints with brute-force detection—lockout, slow-down, or MFA challenge
  after repeated failures. Emit events to SIEM/anomaly monitoring on attack signals.
- **Redirects & UX:** Flexible redirect flows for post-login (success URL, dashboard, intended URL, device registration,
  terms acceptance) and on error (localized messages, reset flows, compliance warnings, o11y-friendly status codes).
- **Audit & Compliance:** All login attempts (success and failure) are logged with unique correlation IDs, actor,
  IP/device, timestamp, method, and reason for failures/policy decisions.
- **Accessibility & Internationalization:** All forms/buttons/labels must meet accessibility guidelines; errors, fields,
  and help UX must support relevant user locales and keyboard navigation.

Sample configuration pattern:

```java
http.formLogin(form -> form
  .loginPage("/my-branding/login")
  .defaultSuccessUrl("/dashboard")
  .failureUrl("/my-branding/login?error=true")
  .permitAll()
  // addLoginProcessingUrl("/api/auth/login") for API
);
// Add .exceptionHandling().accessDeniedPage("/error/forbidden")
// Add filters/hooks for pre-auth/device/MFA as needed
```

**Best Practices:**

- Always test login UX for mobile, accessibility, and international users.
- Log full audit trail for every login attempt (success/failure/type).
- Use custom error messages for attack scenarios (device anomaly, brute force, suspicious activity) and escalate to
  SIEM.
- Never expose stack traces or internal account info on error.
- Integrate MFA, device registration, or adaptive risk for all high-value flows.

Modern practice: Use OAuth2/OpenID for user-facing apps; form login for legacy/secure panels.

#### Authentication With Database

Production-ready systems store users/roles in relational databases using JPA or JDBC. Implement custom
UserDetailsService to retrieve user details with roles/permissions. Always store passwords with a strong encoder:

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserEntity user = userRepository.findByUsername(username);
    return User.withUsername(user.getUsername())
      .password(user.getPassword())
      .roles(user.getRoles().toArray(new String[0]))
      .build();
  }
}
```

Back with environment secrets and out-of-band credential rotation for compliance.

#### Passwordless, Biometric, and Advanced MFA: Future-Proof, Phishing-Resistant Access

2025 enterprise security best practice is passwordless-first, with MFA and biometrics layered on for critical functions
and stepped-up SSO:

- **Passwordless Authentication with WebAuthn/FIDO2:**
   - Register, enroll, and manage passkeys (hardware tokens, platform authenticators, device biometrics) via the
     WebAuthn protocol.
   - Bind device credentials to corporate MDM/inventory for managed device attestation/allow/block lists.
   - Flow: User logs in via biometric/device challenge—server verifies signature using FIDO2 keys, grants session or
     token.
   - SSO and SAML/OIDC: Propagate passwordless state and FIDO attestation as claims for federated SSO (downstream apps
     consume passkey/biometric claims).
   - Fallback: Support strong backup auth (hardware, TOTP, enterprise helpdesk) with secure re-registration and timed
     lockout/notification.
   - Audit every credential add/remove/rotate event with traceable user/device context.

- **Modern Biometric and Adaptive MFA:**
   - Integrate native OS/mobile biometrics (fingerprint, FaceID, Windows Hello) using WebAuthn-compliant authenticators
     on supported clients.
   - For high-risk transactions or suspicious device/risk signals, require step-up: re-verify with biometrics, TOTP, or
     app-based push.
   - Detect and counter replay/phishing attacks: Tie authentication to device integrity, set origin/cross-origin
     policies, and monitor for unexpected IP/device context.
   - Federate biometric/MFA status across SSO ecosystem (passkey/Biometric claim present => step-down or adapt
     conditional access polynomial).
   - All biometric/TOTP/Push responses and failures are fully auditable in SIEM.

- **Token, TOTP, and Push-based MFA:**
   - Support multiple factors: Authenticator app (TOTP), push notification (app or SMS), hardware OTP (YubiKey,
     smartcard).
   - MFA enrollment and backup/rotation flows must be user-friendly, auditable, and enable rapid revocation on
     compromise.
   - Time-limit all tokens and codes; never persist or transmit secrets via HTTP/JS; rate-limit and throttle on repeated
     failures.
   - Document fallback policy for users who lose devices. Support multiple backup factors for accessibility/compliance.

- **Usability, Testing, and Compliance:**
   - All passwordless/MFA/biometric flows must undergo regular phishing, social, and replay-resistance testing.
   - Enable user self-service to review, rotate, revoke, and manage all credentials (with audit trail and helpdesk
     escalation if regulated).
   - Ensure full accessibility and usability with biometrics (screen readers, alt flows, user consent/information
     pages).
   - All credential and event flows are integrated into SIEM/logging for forensics and compliance reporting.

**Enterprise Takeaway:**

- Make passkey/biometric flows the login default.
- MFA and biometrics should always be offered (not required for every login, but mandatory for risk/step-up or critical
  path scenarios).
- All changes, failures, and fallback to backup factor must be auditable, observable, and auto-escalated for
  fraud/attack signals.
- Combine device, biometric, push, and knowledge factors for enterprise fraud mitigation—integrated into downstream SSO
  and session lifecycle.

#### OAuth2/OpenID Connect: SSO, Multi-Provider, and Cloud-Native Identity

Modern enterprise apps rely on OAuth2/OpenID Connect for secure, federated, and auditable authentication with
third-party or organizational IdPs.

- **Multi-Provider/OIDC Flows:** Use multiple OAuth2/SSO providers (e.g., Google, Azure AD, Okta, Auth0, Keycloak,
  custom corporate IdP) with dynamic selection/config. Enterprise apps map provider to resource, tenant, or user group.
- **PKCE and Security Best Practices:** Always use PKCE (Proof Key for Code Exchange) for web and mobile flows. Enforce
  HTTPS, short-lived tokens, and custom consent pages where required. Never trust or store plaintext tokens; always
  handle tokens/JWT via secure HttpOnly/SameSite cookies.
- **Callback/Redirect Hygiene:** Only register and allow specific, audited callback URLs. Audit every login event and
  enforce correlation IDs for critical flows.
- **Claim and Authority Mapping:** Use custom JwtAuthenticationConverter or OAuth2UserService to map JWT/OIDC profile
  claims to Spring Security authorities. Apply tenant/zone/role resolution, group mapping, and enrich the security
  context with compliance-relevant claims.
- **Token Validation & Rotations:** Validate expiry, signature, issuer, and nonce for all tokens. Rotate OAuth/JWT
  secrets regularly and never hardcode public keys or secrets. Add automatic key fetching/discovery via well-known JWK
  endpoints.
- **SSO and Consent Management:** When offering SSO, require explicit consent and log every consent/policy update.
  Provide API and UI for consent revocation and audit all changes.
- **Provider Failover:** Configure multiple providers in the AuthenticationManager; document and test fallback rules (
  e.g., try Google, then Okta, then local user DB). Audit provider reachability/security events.
- **Compliance & Observability:** Emit auditable events for login, consent grant, token issuance, token refresh/revoke,
  and unsuccessful/consent-denied scenarios. Integrate with SIEM/monitoring.

Sample provider configuration pattern:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ...
            client-secret: ...
            redirect-uri: "{baseUrl}/login/oauth2/code/google"
            scope: openid,profile,email
          azure:
            client-id: ...
            client-secret: ...
        provider:
          google:
            issuer-uri: https://accounts.google.com
          azure:
            issuer-uri: https://login.microsoftonline.com/{tenant}/v2.0
```

Advanced pattern for custom converters:

```java
@Bean
public Converter<Jwt, ? extends AbstractAuthenticationToken> customJwtAuthenticationConverter() { ... }
```

**Enterprise Best Practices:**

- Normalize all external login events to internal compliance/audit schemas.
- Regularly rotate and review all OIDC secrets, config, and endpoint metadata.
- Test fallback flows between providers, and monitor access to every enabled callback/redirect URI.
- Use tenant- or region-aware JWT claim mapping.

#### LDAP, SAML2, and Directory Authentication: Enterprise Federation and Compliance

Spring Security allows for seamless integration with enterprise directories (LDAP/Active Directory) and SAML2 identity
providers—essential for SSO, HR-driven provisioning, and compliance.

- **LDAP/Active Directory:**
   - Configure LdapAuthenticationProvider with secure connection details (TLS, bind user, search base).
   - Map user attributes (username, email, displayName, roles/groups) to granted authorities for role-based access.
   - Support account state reflection (locked, expired, disabled) and provision hooks for onboarding/offboarding.
   - Enforce and audit enterprise password policy (expiry, rotation, complexity, lockout) via directory controls.
   - Configure provider failover for multi-DC or regional AD/LDAP clusters.
   - Sync HR attributes and group memberships for dynamic RBAC.
   - Secure provider credentials using environment/vault secrets and rotate regularly.
   - Emit observable events for authentication, sync, and directory change for SIEM/audit.

- **SAML2 Federated SSO:**
   - Use Spring Security’s SAML2 support to register external IdPs (Okta, Azure AD, ADFS/SAML, large SaaS providers).
   - Handle SSO assertion validation, signed SAML responses, ACS (Assertion Consumer Service) endpoint security.
   - Map SAML attributes/groups to authorities, RBAC, and extended claims.
   - Support SSO failover with secondary IdPs; log and audit all login attempts and assertion failures.
   - Use explicit metadata and secure trust anchors for each IdP (rotate regularly; never trust static metadata).
   - Integrate logout and SLO (single logout) flows with session/JWT/jti invalidation for all user devices.

- **Multi-Tenancy, Scaling, and Extension:**
   - For regulated/multi-tenant deployments, split config per domain/region and use tenant-aware directory providers.
   - Implement custom provisioners for advanced claims, HR sync, device registration, or onboarding workflows.
   - Provide fallback (emergency or local admin) flows for directory failures, with clear audit and escalation.
   - Schedule test logins/audit probes for early warning of IdP rot/delegation or expired metadata.

**Enterprise Best Practices:**

- Run nightly/continuous audit syncs to detect unexpected attribute, group, or status changes.
- Document and version directory and SAML2 IdP config (endpoint, bindings, keys, attributes, provider priorities).
- Regularly re-check SAML metadata (incl. cert expiry, bindings, endpoints) and LDAP credentials.
- Always test directory and federated provider failover in DR and regional exercises.

## 7. Authorization

### Enterprise Enforcement and Composite/Contextual Rules: Layered, Business-Driven, and Adaptive

Modern enterprise authorization enforcement is multi-layered and agile, spanning HTTP endpoints, service methods, data
repositories, and event/request buses:

- **Layered Enforcement:** Apply defense-in-depth at every architectural boundary—HTTP/endpoint, controller/service,
  repository, workflow/event. Never rely on client or frontend checks for critical enforcement.
- **Composite/Contextual Rules:** Write access rules that incorporate multiple business and risk dimensions: role,
  department, zone, geo-ip, device fingerprint, dynamic context from risk engine, business hours, customer/legal zone,
  dynamic claims, and external regulatory flags.
- **Business Domain-Driven Access:** Collaborate with domain experts and compliance to define fine-grained access—e.g.,
  “only West Coast managers can approve transactions > $10,000 from 9am–5pm PST on a corporate device with low risk.”
- **Multi-Tenant and Zone Segmentation:** Ensure all policies are tenant, customer, region, or business-area aware.
  Dynamically resolve policy boundaries by context/claim (from JWT, SAML, OIDC, DB directory attribute, or risk service
  tag).
- **Dynamic Mapping:** At authentication and authorization time, map changing claims and attributes into Spring Security
  authorities—refresh context on user/zone change, HR/IT event, or risk/incident trigger.
- **Robust Auditing and Review:** Audit/log all composite rule evaluations, privilege elevations, and risk-triggered
  allows/denials; trace every decision chain to claims, context, and policy rule/policy version. Integrate every
  enforcement with SIEM, compliance review, and anomaly monitoring.
- **Ongoing Security and Compliance:**
   - Conduct regular review (e.g., quarterly) of composite/conditional business/risk rules.
   - Automate regression testing for all layered and contextual enforcement.
   - Recertify policies with risk, IT, and compliance on changes in regulatory environment, market, or business model.
   - Surface potential policy drift and unintended privilege chains using static (IaC) and dynamic (policy simulation)
     tools.

**Enterprise Takeaway:**

- Treat enforcement as a living mesh; layer and context-drive all sensitive rules.
- Make every critical allow/deny path traceable, testable, and observable.
- Partner with domain experts and compliance in every authorization redesign.

### RBAC: Enterprise Roles, Hierarchies, and Continuous Compliance

> **Note:** _RoleHierarchyImpl is deprecated as of Spring Security 6+. Do not use it for new code. Explicit authority
aggregation during authentication is now the recommended approach._

#### Modern Approach: Explicit Authority Aggregation

For each user (or on JWT/token mapping), grant all inherited/parent roles directly as authorities. This ensures
hierarchical privileges, but keeps everything auditable, testable, and compatible across services.

**Example (UserDetailsService):**

```java
List<SimpleGrantedAuthority> authorities = new ArrayList<>();
Role userRole = emp.getRole();
if (userRole != null) {
    String name = userRole.getName().toUpperCase();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + name));
    if ("ADMIN".equals(name)) {
        authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    } else if ("MANAGER".equals(name)) {
        authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    }
} else {
    authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
}
return new User(emp.getUsername(), emp.getPassword(), authorities);
```

- This can be extended for DB, claims, SAML/OIDC mapping, etc.
- Same logic applies for JWT claims: assemble the full list of granted roles (primary + inherited) and encode in the
  user/roles claim at token creation.

**Auditing/Regulatory:**

- Role assignments and mappings should be version controlled, easy to review, and tested under all critical flows (
  onboarding, privilege escalation, and offboarding).
- Automated tests must verify enforcement for all RBAC-protected endpoints for all role combinations—automation is
  required for regulated industries.

**Multi-tenant/advanced:**

- For tenant/region-aware apps, execute authority aggregation with tenant or zone-specific inheritance trees, keeping
  all logic explicit and documented.

**Why this approach?**

- 100% observable, deterministic, and debug-friendly—no more hidden privilege inflations.
- Works in distributed, cloud, and SSO/microservices architectures.
- Future-proof and vendor-neutral: explicit mapping works the same with JWT, OIDC, SAML, or legacy RBAC.

---

### ReBAC: Dynamic Graph- and Relationship-Based Access

Relationship-Based Access Control leverages the dynamic structure of relationships in organizations, teams, resources,
or social graphs to finely control authorization—crucial for collaborative SaaS, B2B platforms, and distributed teams.

- **Graph Model and Policy Patterns:** Access decisions depend on the relationship graph—e.g., user is owner, manager in
  team, project admin, mentor/coach, parent/child. Policies may be “colleague of...,” “delegated by...,” “admin in org’s
  resource tree,” “shared-with link,” or dynamic team/billing group memberships.
- **Spring Integration:** Extend claims or principal details at login with relationship graph edges (e.g., teamId,
  orgId, relationshipType) or query the graph dynamically in custom `AuthorizationManager`/filter/endpoint conditions.
  Use external relationship graph services (OpenFGA, proprietary microservice) for graph building and traversal.
- **Sample ReBAC Rules:**
   - `@PreAuthorize("principal.owns(#doc) or principal.isTeamAdmin(#doc.teamId) or hasRole('ORG_SUPERADMIN')")`
   - `.access("@relationshipService.hasAccess(principal.id, #entity.id, 'CAN_EDIT')")`
   - Sync or inject edges for “delegated authority” and “temporary relationship” with valid-from/until.
- **Attribute and Claims Mapping:** Use JWT/SAML custom claim sets for owned, managed, delegated, or group-linked
  resource lists. On each request or context change, fetch the latest graph state as part of the security context.
- **Audit and Graph Change Tracking:** Log every relationship grant/revoke event; periodically review link or orphan
  risks (dangling relationships/links out-of-date after org/user changes); require recertification and explicit removal
  timings. Escalate privilege/ownership changes, and orphan risks, to security/SIEM.
- **Multi-Tenant and SaaS Social Graph:** For SaaS or B2B platforms, scope relationship graph by tenant/account or
  workspace and segment graph-traversal logic to ensure RBAC/ABAC boundaries hold per customer or partner org.
- **Edge Cases and Privilege Escalation:** Regularly test and monitor for link chain privilege escalation, orphan object
  references, privilege inheritance flaws, and reviewer bypass via relationship injection. Use regressive policy tests
  and visual graph tools to track privilege paths in the system.

**Enterprise Best Practices:**

- Graph relationship models should be independently audited and versioned with clear change logs.
- Enable graph traversal timeouts/limit depth to avoid performance/denial risks.
- Require revocation and link review on user, org, or resource lifecycle changes.
- Integrate all dynamic link changes and privilege escalations with SIEM or compliance/approval workflows.

### Method-level Security: Service-Layer Enforcement, Testability, and Context

- **Multi-Modal Patterns:** Use `@PreAuthorize`, `@PostAuthorize`, `@Secured`, `@RolesAllowed`, or custom annotations
  for precise business-layer enforcement. Secure service beans/methods handling privileged actions (e.g., user deletion,
  payment approval, workflow step).
- **Complex Rules & SpEL:** Leverage SpEL for dynamic, context-aware filters (e.g.,
  `@PreAuthorize("hasRole('MANAGER') and #order.amount < 10000")`), including context, principal, or argument inspection
  and delegated authorization beans for reusable logic.
- **Compositional Practice:** Combine multiple rules (role, department, device context, ABAC attributes) for tailored
  flows, and restrict method-level security to the minimal scope required for maintainability.
- **Compliance Testing:** Enforce integration tests for all business-critical security rules with test users, mock
  context, and negative case validation.
- **Audit/Test/Policy Drift:** Emit logs for sensitive method invocations, privilege elevation failures/success, and
  detected bypass/override events—integrate with CI/CD and SIEM.
- **Multi-Tenant/Context:** Use custom security evaluation context for tenant/region partitioning or sensitive business
  logic.

**Patterns:**
```java
// Classic annotation with static role
@PreAuthorize("hasRole('ADMIN')")
public void deleteAccount(Long id) { ... }

// Conditional SpEL expression
@PreAuthorize("hasRole('MANAGER') and #order.amount < 10000")
public void approvePurchase(Order order) { ... }

// JSR-250
@RolesAllowed("ROLE_MANAGER")
public void approveOrder(Long orderId) { ... }

// Custom bean for business logic
@PreAuthorize("@customAccessEvaluator.canApprove(authentication, #entity)")
public void someCriticalOp(Entity entity) { ... }
```

- Segment application logic by endpoint, business function, and tenant. Use different contexts/providers for region or
  business unit.
- Document and version method-level rules; review at every compliance checkpoint.
- Emit audit events on all calls to privileged methods, tie failures to SIEM, and surface unusual invocation patterns
  for investigation.
- Write integration tests with mock auth context covering positive, negative, and malicious cases for each sensitive
  method.

### URL-based Authorization: HTTP/Route-Focused Defense-in-Depth

- **Explicit Route Patterns:** Secure endpoints in SecurityFilterChain with `.requestMatchers()` and fine-grained access
  methods:
  `.requestMatchers("/api/admin/**").hasRole("ADMIN"), .requestMatchers("/api/{tenant}/secure/**").access("hasAuthority('TENANT_USER') and principal.tenant==#tenant")`.
- **Composition (AND/OR):** Stack multiple rules, restrict by HTTP method, zone, GeoIP, CORS policy, business function.
- **Whitelist First:** Always flip policy to allow only known, explicitly-protected endpoints—default to deny.
- **Testing:** Automated and policy-driven tests validate all expected/protected endpoints; test negative/bypass cases
  on every release.
- **Audit/Event:** All failed/missing endpoint matches and denial events are logged/monitored via SIEM.

**Patterns:**
```java
http.authorizeHttpRequests(auth -> auth
  // Explicit pattern with static roles
  .requestMatchers("/admin/**").hasRole("ADMIN")
  .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")

  // Segmented by HTTP method and business function
  .requestMatchers(HttpMethod.POST, "/api/payments/**").hasAuthority("PAYMENT_WRITE")

  // Multi-tenant/region-aware policy
  .requestMatchers("/tenant/{tenant}/**").access("hasAuthority('TENANT_USER') and principal.tenant == #tenant")

  // Fallback
  .anyRequest().denyAll()
);

// Multi-chain pattern for segmentation
@Bean
public SecurityFilterChain adminChain(HttpSecurity http) throws Exception {
  http
   .securityMatcher("/admin/**")
   .authorizeHttpRequests(...)
   .build();
}

@Bean
public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
  http
   .securityMatcher("/api/**")
   .authorizeHttpRequests(...)
   .build();
}
```

- Segment SecurityFilterChains for APIs, admin UIs, public endpoints, etc., each with its own matcher and policy.
- Use parameterized matchers (PathVariable) with tenant/zone context in `.access()` expressions.
- Test all patterns via integration and security regression suites.
- Document/recertify endpoint mappings and policies at every release; monitor for policy drift/bypass.
- Log all access failures/denials and anomalous access attempts to SIEM.

### Expression-based Authorization: Dynamic and Context-Rich Policies

- **SpEL/AccessEvaluator:** Apply SpEL or custom access beans to check advanced conditions—user context, JWT claim,
  device/HR attribute, relationship graph, business hours, or resource ownership.
- **Examples:** `@PreAuthorize("#doc.owner == authentication.name")`,
  `.access("customComponent.canView(principal, #resource)")`.
- **Dynamic Policy:** ABAC/PBAC/ReBAC attributes can all be incorporated; custom beans/services can encapsulate advanced
  conditional logic for clear policy reuse/testing.
- **Testability & Compliance:** All evaluated rules/conditions should be traceable in logs, and negative tests must be
  run on deploy.
- **Tenant/Context Scoping:** Compose expressions with tenant/region context (`authentication.tenant == #tenant`) for
  highly segmented environments.

**Patterns:**
```java
// Inline resource/context logic
@PreAuthorize("hasRole('USER') and #order.owner == authentication.name")
public Order getOrder(Long orderId) { ... }

// Use dynamic attribute
@PreAuthorize("hasAuthority('FINANCE') and principal.region == #order.region and #order.amount < 10000")

// Enriched via custom spring beans
@PreAuthorize("@customAccessEvaluator.canEdit(authentication, #entity)")
public void editEntity(Entity entity) { ... }

// URL/route-based DSL pattern
.requestMatchers("/broker/{tenant}/secure/**")
  .access("hasAuthority('TENANT_ACCESS') and principal.tenant == #tenant")
```

- Centralize shared complex expressions in bean methods, referenceable in SpEL (`@MyEvaluatorBean`).
- Document and maintain all expressions/policies as verifiable artifacts; version and review with each change.
- Provide SIEM/audit logging for denied and high-privilege allow decisions.
- Validate expressions as part of security regression testing.
- Segment expressions for multi-tenant/multi-region by dynamic attribute or argument binding.

**Enterprise Best Practices:**

- Document and audit method/URL/expression-based rules; treat all as policy artifacts requiring review/recertification.
- Use automated tests and code review bots to detect bypass/misconfiguration before release.
- Instrument all access control failures (and critical allow events) for SIEM monitoring.

### Protecting REST APIs with RBAC: Endpoint, Claims, and Enforcement Deep Dive

Modern REST APIs in the enterprise demand granular, layered RBAC to ensure defense-in-depth, compliance, and agile
review:

- **Endpoint Policy Patterns:**
   - **Static & Dynamic Role Enforcement:** Annotate controllers/methods with role checks (
     `@PreAuthorize("hasRole('ADMIN')")`), configure per-route enforcement in SecurityFilterChain (
     `.requestMatchers("/orders/**").hasAuthority("ORDERS_READ")`).
   - **Composite Context:** Use `.access()` for dynamic checks (
     `.access("hasRole('FINANCE_ADMIN') and principal.orgId == #orgId")`).
   - **Scoped Authorities:** For SaaS/multi-tenant, use authorities like `TENANT_{id}_USER`, issuing JWT claims for each
     logical tenant/context.
- **Claims-to-Authority Mapping:**
   - **JWT-to-Spring Authority Mapping:** Use JWT claims like `roles`, `scopes`, `org`, or `tenant` and a custom
     `JwtAuthenticationConverter` or mapper bean. Enrich authorities with additional claims for region, zone, project,
     or delegated rights.
   - **Multi-Tenant/SaaS:** Include `tenant_id`, `org_id`, or `workspace` in JWT; map each to Spring authorities/context
     for per-API chain enforcement:
      ```java
      @Bean
      public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
          JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
          // Map claims to authorities, add tenant/org specific prefixes
          return jwt -> {
              Collection<GrantedAuthority> authorities = authoritiesConverter.convert(jwt);
              String tenant = jwt.getClaim("tenant_id");
              // Add per-tenant/user custom logic
              return new JwtAuthenticationToken(jwt, authorities, "TENANT_" + tenant);
          };
      }
      ```
   - **RBAC Claim Normalization:** Normalize all claims to canonical `ROLE_` or `AUTHORITY_` format for consistent
     review and logging.
- **Microservices/Gateway Enforcement:**
   - Apply RBAC at API gateway or edge service using incoming JWT claims, enforcing both identity/issuer checks and
     required role for target microservice (“user must have ORDERS_WRITE for given org or workspace”).
   - Chain with PBAC/ABAC for higher-sensitivity endpoints, or for defense-in-depth on privilege escalation paths.
- **Privilege Escalation Defense:**
   - Audit APIs for endpoints granting elevated permissions, perform bypass/negative testing with downgraded/altered JWT
     or manipulated claims (ensure no privilege injection or homograph/alias issue).
   - Regularly review and simulate possible escalation/injection vectors.
- **Audit, Review, and SIEM Flows:**
   - Emit access decision event, JWT claims, and role checks on every RBAC-controlled endpoint; forward all critical
     events (denials, privilege grants) to SIEM.
   - Review access logs by endpoint, authority, resource, and risk/incident context.
- **Least Privilege & Compliance:**
   - Review every role used for API access quarterly; automate audit of APIs, authorities, and claim-to-endpoint
     mapping; surface excess privilege and stale role assignments for review/remediation.
- **Fallback & Failover:**
   - Always define explicit fallbacks/denials for unknown roles, claims, or endpoints—never allow silent escalation on
     mis-config/missing mapping.
   - In regulated SaaS, require emergency/fallback approval and audit event in logs for every bypass.
- **Example Secure RBAC Patterns:**

```java
// Controller-level with role mapping
@RestController
@RequestMapping("/api/finance")
public class FinanceApi {
  @PreAuthorize("hasRole('FINANCE_ADMIN') and authentication.claims['region'] == #region")
  @GetMapping("/balance/{region}")
  public BigDecimal getRegionBalance(@PathVariable String region) { ... }
}

// SecurityFilterChain for multi-tenant API
@Bean
public SecurityFilterChain tenantApiChain(HttpSecurity http) throws Exception {
  http
    .securityMatcher("/api/tenant/**")
    .authorizeHttpRequests(authz -> authz
      .requestMatchers("/api/tenant/{tenant}/**")
      .access("hasAuthority('TENANT_USER') and principal.tenant == #tenant")
      .anyRequest().denyAll()
    )
    .build();
}
```

- **Testing Patterns:**
   - Automate integration and unit tests for negative privilege/bypass, authority absence, and claim/role downgrade.
   - Provide explicit test users/tokens for every role/claim and verify expected outcome per endpoint and business
     context.
- **Best Practices:**
   - Normalize all JWT/scope/claim mapping for auditability and review.
   - Resegment authorities on every org/region/tenant model change; recertify access for major privilege/zone shifts.
   - Audit for unintended composite or inherited role grants in dynamic claim logic.
   - Document policy, testing, mapping, and exception/fallback schemas for every protected API.

## 8. Securing REST APIs: Complete 2025 Enterprise Security Reference

Modern REST APIs are the primary attack surface for most enterprises, requiring defense-in-depth patterns covering
authentication, authorization, input validation, and operational monitoring.

### JWT Authentication: Stateless, Rotated, and Auditable

- **Token Issuance:** On successful login (or federated SSO), issue a short-lived (≤15min) access JWT and long-lived (
  7–30d) refresh token, never transmitted in logs or query params. Store refresh tokens in secure HttpOnly, SameSite
  cookies.
- **Claims & Lifetimes:** JWT payload should include minimal, current claims (userId, roles, tenant, region, deviceId,
  exp, iat, jti), with audience/issuer fields and unique jti for revocation. Lifetime must match risk context (short
  access for public, longer/step-up for privileged flows).
- **Rotating Keys & Revocation:** Rotate JWT signing keys at least quarterly and instantly on breach. Maintain
  revocation lists (blacklist/jti cache) for logouts, password changes, incident response. Never hardcode signing
  secrets; always load via environment/vault.
- **Refresh Flows:** Refresh tokens renew access tokens and are single-use or rotate-on-use. Refresh endpoint requires
  proof of refresh token and device/metadata checks—always audit and limit refresh attempts; tie device/browser binding
  to prevent token theft.
- **Validation & Pitfalls:** Validate signature, exp, iss, aud, nbf, jti for every access. Detect token replay and
  stale/clock-skew issues. Bind tokens to device/fingerprint and restrict usage scope. Deny access and trigger SIEM
  incident on malformed/invalid tokens.
- **Audit/Monitoring:** Log all token issuance, rotation, refresh, and denial events by user/device/endpoint; surface
  unusual token reuse or expired/blacklisted token access as incidents. SIEM integration is mandatory.
- **Compliance:** Regularly review token structure, claims coverage, and operation logs. Rotate all secrets as part of
  incident playbook or regulatory response.

Sample token issuance/validation (simplified):

```java
// Issue
String token = Jwts.builder()
  .setSubject(user.getUsername())
  .setAudience("api")
  .setIssuedAt(now)
  .setExpiration(in15Minutes)
  .claim("roles", user.getRoles())
  .claim("tenant", user.getTenant())
  .setId(UUID.randomUUID().toString())
  .signWith(signingKey)
  .compact();

// Validation
Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(jwt).getBody();
if (isBlacklisted(claims.getId()) || isExpiredOrInvalid(claims)) deny();
```

### OAuth2 Resource Server/Client: Delegated Auth and Scope Separation

- **PKCE, Consent, and Scope:** For mobile/SaaS clients, enforce PKCE and explicit user consent for every delegated
  scope/role. Never trust tokens without validated signature and origin.
- **Scopes & Claims:** Always inspect scope/claim coverage on every resource access, mapping to Spring authorities via
  converters. Integrate with OIDC/SAML for cross-platform claims.
- **Client/Resource Separation:** Configure resource server and client roles distinctly, with isolated credentials,
  config, and callback URIs. Test for privilege escalation by cross-client token replay or scope overbroadness.

Sample config for resource server:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: https://auth.example.com/.well-known/jwks.json
```

### CSRF Protection and CORS Hardening

- **Stateful:** Enforce CSRF tokens on all session-bound browser endpoints. Use CSRF token in custom header (e.g.,
  X-CSRF-TOKEN) and require validation for mutating endpoints. Never expose CSRF tokens to JavaScript unless endpoint is
  locked down (see SameSite).
- **Stateless APIs (JWT):** Explicitly disable CSRF for RESTful, stateless endpoints, but maintain audit coverage/manual
  denial in endpoint logic.
- **CORS Policies:** Always define explicit allow lists for origins, HTTP methods, and headers. Preflight requests (
  OPTIONS) should only return success for trusted origins. Deny access/control exposure for all unapproved
  headers/methods/domains.

Sample CSRF + CORS config (REST + browser):

```java
http
  .csrf(csrf -> csrf
    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
  )
  .cors(cors -> cors 
    .configurationSource(corsConfigurationSource())
  );
```

### Endpoint Lockdown, Rate Limiting, and Service Mesh Patterns

- **Endpoint/HTTP Lockdown:** Deny all by default, enable explicit endpoint/role/policy mapping only. Use
  `.anyRequest().denyAll()` and chain multi-filter patterns for sensitive segments (see API gateway/microservices for
  lateral defense).
- **Rate Limiting and Abuse Detection:** Integrate API gateway/service-mesh and internal rate limiters for DDoS, bot
  attack, brute force, and sudden volume spikes. Enforce device/IP based throttling and lockout, with SIEM alerting on
  high/bursting activity.
- **API Gateway, mTLS, Microservices:** Enforce identity at the gateway/mesh edge with JWT validation, signature checks,
  mTLS device/service authentication, and role/component-based ingress policies. Ensure zone/tenant/region claims
  propagate and are revalidated downstream.

### Exception Hardening and Secure Error Handling

- **Generic Error/Exception:** Never leak internal details/stack traces/claim structures in REST error JSON. Return
  generic codes/messages, with audit code hooks and SIEM alerts for all sensitive or repeated error codes (e.g.,
  repeated 401/403 from a single IP or service).
- **Logging/SIEM Review:** Every access/denial, exception or token error event must flow to central SIEM with context,
  JWT jti, and user/device/resource/region/tenant details for incident response.

### Secure Headers, Replay, and Compliance

- **Default Secure Headers:** Set HSTS, X-Frame-Options, Content-Security-Policy, X-Content-Type-Options,
  Referrer-Policy, Permissions-Policy, and zone/tenant/region in every response using built-in Spring features.
- **Replay/Abuse Defense:** Ensure all tokens/sessions are one-use or strictly time-limited; inspect for replay/resource
  injection patterns; block and rotate on incident; support proactive session revocation.
- **Negative Testing and CI:** Run negative, edge, and exploit case testing on every API deployment. Simulate
  invalid/expired tokens, overflow/abuse, privilege mismatch, cross-tenant separation, and unauthorized escalation as a
  requirement before release.
- **Least Privilege, Compliance Rotations:** Segment all REST policies to the minimum authorized claim/role set per
  endpoint/resource. Trigger role/user claim/zone reviews on incident, business, or regulatory change.

**Enterprise Best Practices:**

- All critical REST endpoints must be explicitly covered in code, config, and regression tests.
- Regularly review endpoint/claim/policy mapping for drift, expansion, or shadow access risk.
- Integrate with DevSecOps pipelines for exploit/negative case discovery and least-privilege drift/roll-forward
  protection.

## 9. Session Management

### In-Depth Enterprise Session Management (2025)

Session management is a cornerstone of enterprise security strategy, privacy compliance, and operational resilience. For
2025, Spring Security offers modular, composable patterns supporting everything from legacy monoliths to distributed,
microservices-first architectures. Modern session management is not just about storing user state—it's about
implementing defense-in-depth, regulatory controls, scalability, and recovery mechanisms.

#### 1. Overview and Definitions

- **Session Management** is the control of user authentication/authorization state across requests, ensuring proper
  isolation, lifecycle, and enforcement.
- **Session** can be stateful (server-managed), stateless (token/JWT), or hybrid (delegated to external providers).
- **Security Context** must propagate accurately and securely across thread/request/service boundaries for every
  scenario.

#### 2. Session Creation Policies

- `STATELESS` (recommended for REST / microservices): No session, context stored in secure token/JWT. Ideal for APIs,
  scalable environments.
- `IF_REQUIRED`, `ALWAYS`, `NEVER` (legacy/browser UI): Control when/how sessions are created. Disable unnecessary
  sessions for public endpoints.
- Tune creation to business context—stateless for public APIs; stateful for admin panels and legacy flows.

#### 3. Distributed Session Stores (Enterprise-Scale)

- **Redis**: High-performance, scalable in-memory store with Spring Session. Enables horizontal scaling, failover,
  centralized expiry/invalidation, and remote logout.
- **JDBC**: Durable, shareable session persistence. Suitable for regulated environments requiring audit trails and
  multi-region recovery.
- **Spring Session**: Abstracts session management, plugging in Redis/JDBC seamlessly. Enables distributed invalidation,
  cross-node logout, and compliance hooks.
- **Configuration Example:**
   - `spring.session.store-type=redis`
   - `spring.data.redis.host=hostname`

#### 4. Session Fixation & Hijacking Defense

- **Regenerate Session IDs on Authentication:** Always migrate session (`sessionFixation().migrateSession()`) during
  login to defeat fixation attacks.
- **Invalidate on Logout:** Force session termination and cookie deletion for every logout event (
  `invalidateHttpSession(true)`).
- **Audit Suspicious Events:** Log unusual session changes and implement anomaly detection for session hijack scenarios.

#### 5. Cookie Security Practices

- **HttpOnly, Secure, SameSite** flags required for all sensitive cookies; set on every authentication/session store
  flow.
- **CSRF tokens** tied to HttpOnly and SameSite cookies, enforcing defense against cross-site attacks.
- Validate cookie attributes during compliance review and use secure, up-to-date cryptography for cookie signing.

#### 6. Timeouts, Rotation, and Inactivity Controls

- **Global Timeout**: Set via `server.servlet.session.timeout` in configuration/YAML per compliance risk profile.
- **Idle vs Max Lifetime**: Short intervals for public/exposed APIs; longer for trusted admin flows.
- **Session Rotation**: Force session expiry/rotation after privilege change/events (role change, password reset, remote
  logout).

#### 7. Concurrent Session Controls & Registry

- **Maximum Sessions Per User**: Limit to 1 for most business flows (`maximumSessions(1)`), or customize by role (admins
  may have more).
- **Session Registry**: Use session registry for tracking, admin-controlled global logout, and compliance-mandated
  revocation. Supports "logout from all devices" and event-based expiry.
- **Distributed Registry Integration**: Ensure distributed logout propagates across all services and nodes instantly (
  Redis/JDBC + event bus).

#### 8. Forced Logout, Invalidation & Breach Response

- **Forced Invalidation**: On account breach, compliance event, or privilege change, instantly force logout and
  invalidate session across all services. Sync blacklists for JWT and sessions in Redis/JDBC.
- **Logout Hooks**: Integrate with SIEM/event bus to log and trigger actions on forced logout, session expiry, denied
  access, etc.
- **API for Admin/Compliance Teams**: Expose APIs for compliance and operational users to trigger user/device logout and
  destroy sessions at any time.

#### 9. Compliance, Logging and Audit

- **Audit All Session Lifecycle Events**: Log creation, access, expiration, forced invalidation, concurrent session
  violation, and privilege-related actions. Map every session event to user/role/device/IP.
- **Retention and Immutable Logs**: Store logs in durable, tamper-evident systems; comply with privacy, GDPR, HIPAA,
  PCI, and other relevant standards.
- **Session Data Protection**: Never log raw session keys or tokens—use correlation IDs and obfuscation for forensic
  traceability.

#### 10. Cloud-Native, Microservices Patterns

- **Stateless Sessions for APIs**: Use JWT/OAuth2 with refresh token rotation, short-lived access tokens, device
  binding, and strict expiry.
- **Distributed Session for Hybrid/Legacy**: Use Redis/JDBC with Spring Session for UIs and privileged panels demanding
  coordinated session state.
- **Mutual TLS (mTLS) & Service Identity**: For service-to-service authentication, use mTLS and token replay prevention;
  never rely on session-only identity for microservices.
- **Session Propagation Across Regions/Zones**: Ensure propagation mechanisms handle partition/failover and session
  revocation across all data centers/regions.

#### 11. Disaster Recovery and Availability

- **Failover Config**: If a session store is down (Redis/JDBC), ensure fallback session handling, clear error signaling,
  and incident escalation.
- **Backup and Restore**: Regularly back up session stores; simulate restoration as part of DR testing and compliance
  attestation.
- **High Availability**: Use clustered Redis/JDBC with automated failover. Test quorum agreements and session
  propagation during region/zone failover.

#### 12. Testing, Automation, & Monitoring

- **Negative/Exploit-Case Testing**: Automate tests for session fixation, hijacking, overflow, concurrent login, stale
  session access, etc. Ensure session expiration and forced logout flows work as designed.
- **Observability & SIEM Integration**: Use event hooks for real-time monitoring, anomaly detection, and incident
  response.
- **Continuous Review**: Revisit session configs and event triggers after every release, penetration test, regulatory
  change, and incident.

---

This complete set of enterprise session management patterns positions every Spring Security deployment for resilience,
compliance, scalability, and auditability. Modern Spring Security implementations in 2025 should treat session
management as a dynamic, defense-in-depth mesh—ready to meet the demands of distributed, regulated, and high-risk
environments.

## 10. Encryption

### In-Depth Enterprise Encryption Strategy (2025)

Encryption is a non-negotiable foundation for enterprise resilience, data protection, regulatory compliance, and
incident containment. Spring Security in 2025 leverages robust, upgradable patterns for cryptography at rest and in
transit, spanning passwords, secrets, payloads, tokens, and persistent data. Every layer is architected for
defense-in-depth, rotation, audit, and automated recovery.

#### 1. Symmetric Encryption: AES-GCM as Standard

- **Industry baseline:** All symmetric application encryption uses AES-256 GCM (Galois/Counter Mode, authenticated).
- **Spring Security Crypto:** Use `Encryptors.stronger` or `Encryptors.delux` (256-bit AES-GCM) for file, property, or
  payload encryption.
   - Example:
     ```java
     String salt = KeyGenerators.string().generateKey(); // random 8+ byte, hex salt
     BytesEncryptor encryptor = Encryptors.stronger("password", salt);
     byte[] ciphertext = encryptor.encrypt(plaintext);
     byte[] decrypted = encryptor.decrypt(ciphertext);
     ```
- **Best practices:**
   - Always use a truly random IV per message.
   - Salt every key derivation using a random, hex-encoded salt (≥8 bytes).
   - Authenticate data (integrity and confidentiality).
   - Rotate keys and salts periodically and automate via CI/CD or vault triggers.

#### 2. Asymmetric Encryption: RSA-OAEP and Hybrid Flows

- **RSA-OAEP** is standard for encrypting secrets/keys (not bulk data). Always prefer OAEP over PKCS#1.5.
- Minimum key size: 3072 bits (2048 bits legacy-only).
   - Example Java/JCE:
     ```java
     Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
     cipher.init(Cipher.ENCRYPT_MODE, publicKey);
     byte[] encrypted = cipher.doFinal(symmetricKeyBytes);
     ```
- **Use case:** Hybrid encryption—RSA encrypts symmetric keys exchanged in microservices, JWT/JWE payloads, cloud API
  keys.

#### 3. Password Hashing: Key-Derivation, Never Encryption

- **Never encrypt passwords. Hash them with a modern KDF:**
   - `Argon2` (preferred), `PBKDF2`, `BCrypt`, `SCrypt`—all supported by Spring Security.
   - Store salt separately, or alongside hashes; pepper managed in secure vault only.
   - Example:
     ```java
     PasswordEncoder encoder = new Argon2PasswordEncoder();
     String hash = encoder.encode("password");
     boolean match = encoder.matches("password", hash);
     ```
- **Upgrade existing hashes:** Use DelegatingPasswordEncoder to support multiple algorithms with migration path.
- **Audit:** No plaintext secrets, hashes never logged or exposed, enforce regular credential/hashing upgrades.

#### 4. Property & File Encryption (Config/Data at Rest)

- **Environment/property secrets:** Encrypt with Spring Cloud Vault, HashiCorp Vault, AWS KMS, Azure, or GCP secret
  managers.
   - Spring Boot supports `{cipher}ENCRYPTED_VALUE` for encrypted values in property files with plugin decryption.
- **File/Payload Data at Rest:** Use AES-GCM (as above) with strong environment/secret store-based password and salt.
- **No hardcoded secrets:** All encryption keys, passwords, salts are loaded dynamically per environment from secure
  vault APIs.

#### 5. Token and API Payload Encryption

- **JWE (JSON Web Encryption):** Use for encrypting JWTs or token payloads (especially in microservices, SaaS, and
  high-risk flows).
   - Leverage RSA-OAEP for key encryption, AES-GCM for payload encryption.
   - Always sign tokens using JWS before encrypting.
- **Do not use static/shared symmetric keys for inter-service secrets.**
- **Sample Library:** jjwt, nimbus-jose-jwt for Spring APIs.

#### 6. Key Management, Rotation, and Vaulting

- **Automatic key and salt rotation:** Automate using vault APIs and CI/CD. Rotate immediately upon incident, employee
  exit, system re-key, or at regulatory intervals.
- **Key origin:** All application keys/IVs/salts originate from hardware vault (HSM) or enterprise-grade software
  vault (HashiCorp, AWS KMS, Azure Key Vault). No keys ever persisted in source, build, or config, only referenced via
  API.
- **Access/rotation policies:** Least-privilege, dual-control access to rotate or export keys. Document full provenance
  of every key event.

#### 7. Cloud-Native Microservices Encryption Patterns

- **Service-to-service encryption:** Prefer mTLS (TLS 1.3+ with cert rotation) for transport; pair with payload
  encryption for sensitive payloads or cross-org boundaries.
- **Tenant-aware keying:** Each tenant/region has distinct keys/certificates—rotate regionally; never share secrets
  across regions or tenants.
- **Distributed Key Propagation:** Ensure services fetch key/cert updates on rotation, and support live reload.

#### 8. Hardware Vault, HSM, and Audit-Grade Compliance

- **HSM for root and critical encryption keys** in regulated sectors (finance/health/critical infra). Integrate Spring
  with hardware vault providers for key import/export/cipher operations.
- **Log and audit every encrypt/decrypt/rotate event.** Retain immutable audit log per key and credential event.
- **Compliance:** Implement regular key material review, attestation, and purge schedules mapped to compliance regimes (
  GDPR, PCI, SOX, HIPAA).

#### 9. Exploit Mitigation and Observability

- **Detect and alert on key misuse, decryption failures, hash downgrade, and tampering.**
- **Implement negative and penetration testing:** Validate all boundaries for cryptographic downgrade, IV/salt reuse,
  privilege escalation, or vault/cloud misconfig.

#### 10. Example: Modern AES-GCM Encryption in Spring (2025)

```java
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

String password = System.getenv("ENCRYPTION_PASSWORD");
String salt = KeyGenerators.string().generateKey();
BytesEncryptor encryptor = Encryptors.stronger(password, salt);
byte[] plaintext = "Secret Data".getBytes(StandardCharsets.UTF_8);
byte[] ciphertext = encryptor.encrypt(plaintext);
// Store ciphertext, NEVER password or salt in codebase/config
```

------

This enterprise encryption model ensures resilience to modern threats, streamlines key lifecycle management, complies
with global standards, and sets concrete patterns for every regulated enterprise in 2025 and beyond.
