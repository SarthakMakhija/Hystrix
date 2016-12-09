Spring Boot project based on Hystrix. Hystrix is a network fault and latency tolerant library for distributed systems.

HystrixCloudConfig is a spring cloud configuration server project for storing properties for various clients which connect to this server. Currently properties are located under /Users/sarthakm/hystrix/repo.

HystrixSpikeCore is a spring boot project which uses Hystrix for making calls to external dependencies (dependencies are defined in HystrixSpikeExternalService project). HystrixSpikeCore talks to HystrixCloudConfig for loading all the properties relevant to this project. HystrixSpikeCore also defines "bootstrap.properties" containing spring.application.name=hystrix-command, defining the name of the application and spring.cloud.config.uri=http://localhost:8888 defining the URL of spring cloud configuration server.

HystrixSpikeExternalService is a spring boot application which defines certain REST services for returning dummy GeoLocation and Weather information.

All these project have gradle wrapper. In order to run any project execute the below mentioned command -
  ./gradlew bootRun

HystrixSpikeCore has a dependency on MongoDB. Please ensure either - <br />
    1. Mongo is up and running <br />
    2. Change the following properties if needed, <br />
        <ul><li>
        spring.data.mongodb.host=localhost <br />
        spring.data.mongodb.port=27017 <br />
        spring.data.mongodb.database=hystrix_metrics_db <br />
    3.  Or, comment out mongoMetricsReporter in MetricsPublishingConfiguration <br />
        </li></ul>
