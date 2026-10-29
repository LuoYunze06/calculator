# ===== Stage 1: compile the calculator with Maven =====
# FROM: official Maven image that already contains OpenJDK 17, used only in the build stage.
FROM maven:3.8-openjdk-17 AS build

# WORKDIR: every following build command runs inside /app.
WORKDIR /app

# COPY: copy pom.xml first so Maven dependency layers can be cached.
COPY pom.xml .

# COPY: copy the Java source tree into the build image.
COPY src ./src

# RUN: package the shaded jar, then jlink a tiny custom JRE from the JDK in this image.
# This avoids pulling a separate runtime image such as the retired openjdk:17-jre-slim tag.
RUN mvn -B package -DskipTests \
    && jlink --add-modules java.base \
        --strip-debug --no-man-pages --no-header-files --compress=2 \
        --output /jre

# ===== Stage 2: tiny runtime image =====
# FROM: empty scratch image; only the custom JRE, required glibc libraries and the jar are copied in.
FROM scratch

# COPY: shared libraries required by the custom JRE (Oracle Linux 8 / glibc).
COPY --from=build /lib64/ld-linux-x86-64.so.2 /lib64/ld-linux-x86-64.so.2
COPY --from=build /lib64/libc.so.6 /lib64/libc.so.6
COPY --from=build /lib64/libdl.so.2 /lib64/libdl.so.2
COPY --from=build /lib64/libpthread.so.0 /lib64/libpthread.so.0
COPY --from=build /lib64/libm.so.6 /lib64/libm.so.6
COPY --from=build /lib64/librt.so.1 /lib64/librt.so.1
COPY --from=build /lib64/libz.so.1 /lib64/libz.so.1

# COPY: the compact JRE produced by jlink in the build stage.
COPY --from=build /jre /opt/jre

# WORKDIR: runtime working directory that holds the application jar.
WORKDIR /app

# COPY: take only the packaged jar from the build stage, not the source or Maven cache.
COPY --from=build /app/target/calculator-1.0.jar calculator.jar

# ENTRYPOINT: always launch the calculator when the container starts.
ENTRYPOINT ["/opt/jre/bin/java", "-jar", "/app/calculator.jar"]

# CMD: default arguments for "docker run calculator:v1"; override them to calculate other expressions.
CMD ["1", "+", "1"]
