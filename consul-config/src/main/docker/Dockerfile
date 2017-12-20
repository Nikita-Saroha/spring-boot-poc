FROM java
LABEL maintainer Mayuresh Krishna <mayureshkrishna@gmail.com>
EXPOSE 8080
ADD /newrelic.yml /newrelic.yml
RUN       wget -q "http://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic.jar" -O /newrelic.jar
ADD /consul-config-1.0.0.jar /app.jar
ENTRYPOINT ["java","-javaagent:newrelic.jar","-jar","/app.jar"]