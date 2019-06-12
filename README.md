# Dofus Scrapping

# Documentation :

- https://www.ibm.com/developerworks/xml/tutorials/x-epubtut/index.html
- https://react-bootstrap.github.io/getting-started/introduction/
- https://v4-alpha.getbootstrap.com/components/forms/
- https://www.baeldung.com/get-user-in-spring-security
- https://dzone.com/articles/secure-spring-rest-with-spring-security-and-oauth2

# Creation of password 
Jshell idea: 
```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
System.out.println(new BCryptPasswordEncoder().encode("test"));
```