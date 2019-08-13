#! /bin/sh

export CATALINA_OPTS="$CATALINA_OPTS -Dspring.profiles.active=$PROFILE"
export CATALINA_OPTS="$CATALINA_OPTS -Daws.region=$AWS_REGION"
export CATALINA_OPTS="$CATALINA_OPTS -Djms.queue.name=$JMS_QUEUENAME"
export CATALINA_OPTS="$CATALINA_OPTS -Ddatabase.serverName=$DB_URL"
export CATALINA_OPTS="$CATALINA_OPTS -Ddatabase.username=$DB_USERNAME"
export CATALINA_OPTS="$CATALINA_OPTS -Ddatabase.password=$DB_PASSWORD"
export CATALINA_OPTS="$CATALINA_OPTS -Djwt.expiration=86400"
export CATALINA_OPTS="$CATALINA_OPTS -Djwt.secret=$JWT_SECRET"
