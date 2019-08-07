FROM tomcat:9.0.20-jre8

EXPOSE 8080

ARG PROFILE=dev
ARG AWS_REGION=us-east-1

ENV PROFILE=${PROFILE}
ENV AWS_REGION=${AWS_REGION}
ENV DB_URL=localhost:5432/easy-planner
ENV DB_PASSWORD=password
ENV DB_USERNAME=admin

# set environment

RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY ./*.war /usr/local/tomcat/webapps/ROOT.war
COPY ./setenv.sh /usr/local/tomcat/bin/setenv.sh