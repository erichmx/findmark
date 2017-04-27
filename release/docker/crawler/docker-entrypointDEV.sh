echo Starting Grails DEV server.

cd $CATALINA_HOME/webapps/crawler

ls -l
printenv

echo $GRADLE_BUILD_DIR
grails run-app
