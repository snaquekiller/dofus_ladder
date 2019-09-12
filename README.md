# Dofus Scrapping

# Documentation :
- https://www.ibm.com/developerworks/xml/tutorials/x-epubtut/index.html
- https://react-bootstrap.github.io/getting-started/introduction/
- https://v4-alpha.getbootstrap.com/components/forms/
- https://www.baeldung.com/get-user-in-spring-security
- https://dzone.com/articles/secure-spring-rest-with-spring-security-and-oauth2
- [Logout](https://www.baeldung.com/logout-spring-security-oauth?fbclid=IwAR0ID1EWVlXvr0GJTh7KHotNEhK_bKwQ_MchYeeggABdST6B5TfJXVec96E)
- [Forgot Password](https://www.baeldung.com/spring-security-registration-i-forgot-my-password)

# Creation of password 
Jshell idea: 
```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
System.out.println(new BCryptPasswordEncoder().encode("test"));
```


# Structure :

- *Dofus-data* : **(library)** it's service contain only schema of database. and the persitent service.
- *Dofus Service* :**(library)** it's contains all sql request and service that can be use by different service ( like mail...)
- *Dofus-api* :**(software)** it's only for the web site and all what need to be run.