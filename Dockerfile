FROM xldevops/jdk17-lts:latest

VOLUME /tmp

# 외부에 8082 포트를 사용 가능하게 함
EXPOSE 8082


COPY build/libs/*-0.0.1-SNAPSHOT.jar /app/starraod-admin-app.jar

# jar 파일 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]