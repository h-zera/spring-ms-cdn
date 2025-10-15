# Etapa 1: preparar dependencias
FROM maven:3.9.3-amazoncorretto-17 AS deps
WORKDIR /workspace

# Copiar solo los archivos necesarios para resolver dependencias
COPY pom.xml .
COPY modulo-boot/pom.xml ./modulo-boot/

# Descargar todas las dependencias (offline)
RUN --mount=type=cache,target=/root/.m2 mvn -B dependency:go-offline

---

# Etapa 2: compilar la app
FROM deps AS build
WORKDIR /workspace

# Copiar código completo del módulo boot
COPY modulo-boot/src ./modulo-boot/src

# Construir jar del módulo boot
RUN mvn -B -pl modulo-boot clean package -DskipTests

# Extraer layers de Spring Boot (para cache y optimización)
RUN java -Djarmode=layertools -jar modulo-boot/target/modulo-boot-*.jar extract --destination target/extracted

---

# Etapa 3: imagen final
FROM eclipse-temurin:17-jre-alpine AS final
WORKDIR /app

# Copiar layers cacheadas
COPY --from=build /workspace/target/extracted/dependencies/ ./dependencies/
COPY --from=build /workspace/target/extracted/spring-boot-loader/ ./spring-boot-loader/
COPY --from=build /workspace/target/extracted/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=build /workspace/target/extracted/application/ ./application/

# Configuración
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-Xms256m -Xmx512m"

# Ejecutar la app
ENTRYPOINT ["java", "-Dspring.profiles.active=postgres", "org.springframework.boot.loader.launch.JarLauncher"]
