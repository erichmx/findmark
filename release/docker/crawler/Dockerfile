FROM tomcat:8.5.13-jre8
VOLUME ["/usr/local/tomcat/webapps/crawler"]
VOLUME ["/gradle"]
VOLUME ["/data"]
MAINTAINER Erich von Hauske <erich@erich.mx>

# Set customizable env vars defaults.
# Set Grails version (default: 3.2.3; min: 3.0.0; max: 3.2.3).
ENV GRAILS_VERSION 3.2.3

# Install Grails
WORKDIR /usr/lib/jvm
RUN wget https://github.com/grails/grails-core/releases/download/v$GRAILS_VERSION/grails-$GRAILS_VERSION.zip && \
    unzip grails-$GRAILS_VERSION.zip && \
    rm -rf grails-$GRAILS_VERSION.zip && \
    ln -s grails-$GRAILS_VERSION grails

# Setup Grails path.
ENV GRAILS_HOME /usr/lib/jvm/grails
ENV PATH $GRAILS_HOME/bin:$PATH

ENV REFRESHED_AT 2017-04-13 2158

ENV GRADLE_USER_HOME /gradle
# ENV GRADLE_BUILD_DIR build-dk

# Setup grails profiles
# RUN wget https://codeload.github.com/grails/grails-profile-repository/zip/master && \
#     unzip master && \
#     mv grails-profile-repository-master/profiles/ $GRAILS_HOME && \
#     rm -rf master && \
#     rm -rf grails-profile-repository-master

# Create App Directory
# RUN mkdir $CATALINA_HOME/webapps/crawler

# Set Workdir
WORKDIR $CATALINA_HOME

ENV BM_ENV DEV

# Set Default Behavior
COPY release/docker/crawler/docker-entrypoint$BM_ENV.sh /docker-entrypoint.sh
ENTRYPOINT ["sh","/docker-entrypoint.sh"]
