# Spring-Security
spring security

The basic contracts in Spring Security architecture.
- **Authentication** means identifying the user (who you are). That can be performed by user credentials, fingerprints etc.
- An HTTP request is intercepted by the Authentication filter.
- Authentication filer forwards the request to the Authentication manager.
- The authentication manager delegates request to a specific authentication provider.
- There can be multiple authentication providers. Authenticating users by username and password is called basic authentication.For that spring provide UserDetailService which is an interface. After authenticating user spring provides a securityContex object through which we can access property of logged in user e.g username, authorities etc.
- Initial configuration is provided by the BasicAuthenticationFilter class provide by spring security.  