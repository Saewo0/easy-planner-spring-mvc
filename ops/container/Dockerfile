FROM tomcat:9.0.20-jre8

EXPOSE 8080

ENV PROFILE=dev
ENV AWS_REGION=${AWS_REGION}
ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
ENV DB_URL=localhost:5432/easy-planner
ENV DB_USERNAME=admin
ENV DB_PASSWORD=password
ENV JMS_QUEUENAME=easy-planner
ENV JWT_EXPIRATION=86400
ENV JWT_SECRET=fxxk

# set environment

RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY ./*.war /usr/local/tomcat/webapps/ROOT.war
COPY ./setenv.sh /usr/local/tomcat/bin/setenv.sh