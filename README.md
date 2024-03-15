# Dockerize a Spring Boot application using Jib

This is an example of how to easily build a a Spring Boot application with AD authentication using OIDC auth code flow. It also uses Jib and workload identity federation approach to push the Docker image.

- https://learn.microsoft.com/en-us/entra/identity-platform/v2-oauth2-auth-code-flow
- https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-boot-starter-java-app-with-azure-active-directory

**Build Info**
![Build Status](https://github.com/apurvchandra/gcp-spring-auth/actions/workflows/actions.yaml/badge.svg)

**Maven:**
```shell
./mvnw compile jib:build -Dimage=<your image, eg. gcr.io/my-project/spring-boot-jib>
```

**Gradle:**
```shell
./gradlew jib --image=<your image, eg. gcr.io/my-project/spring-boot-jib>
```

## Deploying to Kubernetes using `kubectl`
```shell
IMAGE=<your image, eg. gcr.io/my-project/spring-boot-jib>

./mvnw compile jib:build -Dimage=$IMAGE

kubectl run spring-boot-jib --image=$IMAGE --port=8080 --restart=Never

# Wait until pod is running
kubectl port-forward spring-boot-jib 8080
```
```shell
curl localhost:8080
> Greetings from Spring Boot and Jib!
```

\* If you are using Gradle, use `./gradlew jib --image=$IMAGE` instead of the `./mvnw` command

