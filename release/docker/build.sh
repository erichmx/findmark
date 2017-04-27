docker-compose -p findmark build
sudo docker tag erichmx/findmark-nginx erichmx/findmark-nginx
sudo docker push erichmx/findmark-nginx
sudo docker tag erichmx/findmark-crawler erichmx/findmark-crawler
sudo docker push erichmx/findmark-crawler
