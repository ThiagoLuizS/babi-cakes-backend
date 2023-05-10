FROM adoptopenjdk/openjdk11:alpine

# App Opções
ENV APP_NAME="babi-cakes-backend-0.0.1-SNAPSHOT" \
    APP_HOME=/$APP_NAME \
    JAVA_OPTIONS=""

WORKDIR /api

# Criando rotina para HealthCheck do container
HEALTHCHECK --start-period=15s --interval=1m --timeout=15s --retries=5 \
    CMD `[[ $(wget -qO- localhost:8080/${APP_NAME}/actuator/health | grep -o -E '"status":"[^"]*"' | awk -F\: '{print $2}') = "\"UP\"" ]] || exit 1`

# Copia FatJar da Aplicação
COPY ./build/libs/${APP_NAME}.jar ${APP_NAME}.jar

CMD java \
    -Duser.timezone=America/Fortaleza \
    -Duser.language=pt \
    -Duser.region=BR \
    -Duser.country=BR \
    -Xms512m \
    -Xmx4096m \
    -XX:+PreserveFramePointer \
    -jar $APP_NAME.jar