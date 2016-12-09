package org.tw.spike.hystrix.utils;

import com.codahale.metrics.MetricRegistry;
import com.netflix.hystrix.contrib.codahalemetricspublisher.HystrixCodaHaleMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MetricsPublishingConfiguration {

    @Bean
    HystrixMetricsPublisher hystrixMetricsPublisher(MetricRegistry metricRegistry) {
        HystrixCodaHaleMetricsPublisher publisher = new HystrixCodaHaleMetricsPublisher(metricRegistry);
        HystrixPlugins.getInstance().registerMetricsPublisher(publisher);
        return publisher;
    }

    @Bean
    public MongoMetricsReporter mongoMetricsReporter(MetricRegistry metricRegistry, MongoTemplate mongoTemplate ){
        MongoMetricsReporter mongoMetricsReporter = MongoMetricsReporter.forRegistry(metricRegistry)
                                                                        .convertRatesTo(TimeUnit.SECONDS)
                                                                        .convertDurationsTo(TimeUnit.MILLISECONDS)
                                                                        .build(mongoTemplate);
        mongoMetricsReporter.start(10, TimeUnit.SECONDS);
        return mongoMetricsReporter;
    }
}