FROM openjdk:17
# 프로젝트를 복사해서 도커 파일 안에서 빌드를 치고 그 후에 jar 파일을 실행 시킨다.
#RUN ["mkdir","project"]
#COPY . ./project
#RUN ["chmod","744","./project/gradlew"]
#RUN ["./project/gradlew","clean","build","--no- debug"]

COPY build/libs/*T.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]