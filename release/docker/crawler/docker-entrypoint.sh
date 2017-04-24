echo Starting Tomcat.

cd $CATALINA_HOME
rm -R webapps/crawler
exec catalina.sh run
