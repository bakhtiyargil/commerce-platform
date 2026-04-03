FROM eclipse-temurin:21-jre
RUN addgroup --system javauser && \
    adduser --system --shell /bin/false javauser && \
    adduser javauser javauser
WORKDIR /app
ENV TZ Asia/Baku
COPY build/libs/*.jar app.jar
RUN chown -R javauser:javauser /app
USER javauser
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-jar", "./app.jar" ]
