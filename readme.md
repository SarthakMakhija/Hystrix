Spring Boot projects based on Hystrix. Hystrix is a fault and latency tolerant library for distributed systems.

HystrixCloudConfig is a spring-cloud configuration server project for storing properties for various clients which can connect to this server for reading properties. Currently properties are located under /Users/sarthakm/hystrix/repo defined in application.properties within this project.

HystrixSpikeCore is a spring-boot project which uses Hystrix for making calls to external dependencies (dependencies are defined in HystrixSpikeExternalService project).
HystrixSpikeCore talks to HystrixCloudConfig for loading all the properties relevant to this project. HystrixSpikeCore also defines <b>"bootstrap.properties"</b> containing
<ul>
<li>spring.application.name=hystrix-command, defining the name of the application</li> <li>spring.cloud.config.uri=http://localhost:8888 defining the URL of spring-cloud configuration server</li>
</ul>

HystrixSpikeExternalService is a spring-boot application which defines certain REST services for returning dummy GeoLocation and Weather information.

HystrixSpikeCore has a dependency on MongoDB. Please ensure either - <br />
    1. Mongo is up and running <br />
    2. Change the following properties in application.properties if needed, <br />
        <ul><li>
        spring.data.mongodb.host=localhost </li>
        <li>spring.data.mongodb.port=27017 </li>
        <li>spring.data.mongodb.database=hystrix_metrics_db </li></ul>
    3.  Or, comment out mongoMetricsReporter() in MetricsPublishingConfiguration <br />

All these project have gradle wrapper. In order to run any project execute the below mentioned command - <br/>
      ./gradlew bootRun
