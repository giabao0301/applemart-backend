![UIT](https://camo.githubusercontent.com/fe8ccf76dbe56d6e4e677f6414e9a21e98d9801b823963821c56e66d42614af3/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f66726f6d2d554954253230564e5548434d2d626c75653f7374796c653d666f722d7468652d6261646765266c696e6b3d68747470732533412532462532467777772e7569742e6564752e766e253246)

Link to Frontend repo: [Applemart Frontend](https://github.com/giabao0301/applemart-frontend)

# AppleMart
**Contributors**

 - Trinh Gia Bao - 21521866@gm.uit.edu.vn - [Github](https://github.com/giabao0301) - [Facebook](https://www.facebook.com/gia.bao.377840/)
### Description
-   Applemart is an ecommerce system utilizing microservices architecture, using NextJS, TailwindCSS for frontend.
-   I use SpringBoot to build services, Spring Cloud to build gateway, Resilience4j to handle error, Kafka to sync between services and Zipkin for tracing.
-   The database I choose for this project is MySQL and Redis for cart service.
-   The order of running the services is: Service Registry -> Auth Service -> Gateway Service, after running in this order, the remaining services can run in any order.
-   Run the docker compose: docker-compose -f docker-compose.infra.yml up

![Application Architecture](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/architecture.png?raw=true)
<div align="center">
	<i>Application Architecture</i>
</div>


![General Usecase Diagram](https://raw.githubusercontent.com/giabao0301/applemart-backend/refs/heads/main/app-resources/usecase.png)

	_General Usecase Diagram_


### Technologies and Framework
[](https://github.com/giabao0301/applemart-backend#technologies-and-framework)
-   Back-end
    -   Java 17
    -   Spring Boot 3: Authorization Server (OAuth 2)
    -   Spring Cloud Gateway, Open Feign, Stream ...
    -   Elastic stack: Elasticsearch, Logstash, Kibana, Filebeat
    -   Grafana stack: Prometheus, Grafana
    -   Zipkin
    -   Redis
-   Front-end
    -   NextJS 14
    -   ReactJS
    -   TailwindCSS
    -   NextUI
    -   ShadcnUI

### Demo

[](https://github.com/giabao0301/applemart-backend#demo)

Some of the pictures of this Application

-   Front-end:
  
![Login page](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/Picture1.png?raw=true)
<div align="center">
	<i>Login page</i>
</div>


![Home page 1](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/Picture11.png?raw=true)
<div align="center">
	<i>Home page 1</i>
</div>


![Home page 2](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/Picture12.png?raw=true)
<div align="center">
	<i>Home page 2</i>
</div>


![Product detail page](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/Picture3.png?raw=true)
<div align="center">
	<i>Product detail page</i>
</div>


![Admin product page](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/Picture5.png?raw=true)
<div align="center">
	<i>Admin product page</i>
</div>


-   Back-end:
  
![Eureka Server ](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/eureka-server.png?raw=true)
<div align="center">
	<i>Eureka Server</i>
</div>


![Prometheus screen](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/prometheus.png?raw=true)
<div align="center">
	<i>Prometheus screen</i>
</div>


![Grafana screen](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/grafana.png?raw=true)
<div align="center"
	<i>Grafana screen</i>
</div>


![Zipkin screen](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/zipkin1.png?raw=true)
<div align="center">
	<i>Zipkin screen</i>
</div>


![Zipkin detail screen](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/zipkin2.png?raw=true)

_Zipkin Detail screen_


![Zipkin Graph screen](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/zipkin3.png?raw=true)
<div align="center">
	<i>Zipkin Graph screen</i>
</div>


![ELK Stack screen](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/ElasticIndexManagement.png?raw=true)
<div align="center">
	<i>ELK Stack screen</i>
</div>


![ELK Stack screen](https://github.com/giabao0301/applemart-backend/blob/main/app-resources/ELKStack.png?raw=true)
<div align="center">
	<i>ELK Stack screen</i>
</div>

