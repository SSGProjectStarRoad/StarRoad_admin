# Java 런타임을 포함하는 기본 이미지로 시작
FROM openjdk:17-jdk-alpine

# 메인테이너 정보 추가
LABEL maintainer="your-email@example.com"

# /tmp로 가리키는 볼륨 추가
VOLUME /tmp

# 이 컨테이너 외부에 8080 포트를 사용 가능하게 함
EXPOSE 8080

# 애플리케이션의 jar 파일
ARG JAR_FILE=build/libs/starroad-admin.jar

# 애플리케이션의 jar 파일을 컨테이너에 추가
ADD ${JAR_FILE} starroad-admin.jar

# jar 파일 실행
ENTRYPOINT ["java","-jar","/starroad-admin.jar"]
