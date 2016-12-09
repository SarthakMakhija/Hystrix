package org.tw.spike.hystrix.utils;

import com.codahale.metrics.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

public class MongoMetricsReporter extends ScheduledReporter {

    private final Clock  clock;
    private final String prefix;
    private final MongoTemplate mongoTemplate;

    public static MongoMetricsReporter.Builder forRegistry(MetricRegistry registry) {
        return new MongoMetricsReporter.Builder(registry);
    }

    private MongoMetricsReporter(MetricRegistry registry,
                                 Clock clock,
                                 String prefix,
                                 TimeUnit rateUnit,
                                 TimeUnit durationUnit,
                                 MetricFilter filter,
                                 MongoTemplate mongoTemplate) {
        super(registry, "mongo-reporter", filter, rateUnit, durationUnit);

        this.mongoTemplate = mongoTemplate;
        this.clock = clock;
        this.prefix = prefix;
    }

    public static class Builder {
        private final MetricRegistry registry;
        private Clock clock;
        private String prefix;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private MetricFilter filter;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.clock = Clock.defaultClock();
            this.prefix = null;
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.filter = MetricFilter.ALL;
        }

        public MongoMetricsReporter.Builder withClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public MongoMetricsReporter.Builder prefixedWith(String prefix) {
            this.prefix = prefix;
            return this;
        }


        public MongoMetricsReporter.Builder convertRatesTo(TimeUnit rateUnit) {
            this.rateUnit = rateUnit;
            return this;
        }

        public MongoMetricsReporter.Builder convertDurationsTo(TimeUnit durationUnit) {
            this.durationUnit = durationUnit;
            return this;
        }

        public MongoMetricsReporter.Builder filter(MetricFilter filter) {
            this.filter = filter;
            return this;
        }

        public MongoMetricsReporter build(MongoTemplate mongoTemplate) {
            return new MongoMetricsReporter(registry, clock, prefix, rateUnit, durationUnit, filter, mongoTemplate);
        }
    }

    @Document
    private static class Metrics {

        @Id
        private String id;

        private String name;
        private String value;
        private long timestamp;

        public Metrics(String name, String value, long timestamp) {
            this.name = name;
            this.value = value;
            this.timestamp = timestamp;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    @Override
    public void report(SortedMap<String, Gauge> gauges,
                       SortedMap<String, Counter> counters,
                       SortedMap<String, Histogram> histograms,
                       SortedMap<String, Meter> meters,
                       SortedMap<String, Timer> timers) {

        {
            final long timestamp = clock.getTime() / 1000;

            try {
                for (Map.Entry<String, Gauge> entry : gauges.entrySet()) {
                    reportGauge(entry.getKey(), entry.getValue(), timestamp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    private void reportGauge(String name, Gauge gauge, long timestamp) throws IOException {
        final String value = format(gauge.getValue());
        if (value != null) {
            mongoTemplate.save(new Metrics(prefix(name), value, timestamp), "metrics");
        }
    }

    private String format(Object o) {
        if (o instanceof Float) {
            return format(((Float) o).doubleValue());
        } else if (o instanceof Double) {
            return format(((Double) o).doubleValue());
        } else if (o instanceof Byte) {
            return format(((Byte) o).longValue());
        } else if (o instanceof Short) {
            return format(((Short) o).longValue());
        } else if (o instanceof Integer) {
            return format(((Integer) o).longValue());
        } else if (o instanceof Long) {
            return format(((Long) o).longValue());
        }
        return null;
    }

    private String prefix(String... components) {
        return MetricRegistry.name(prefix, components);
    }

    private String format(long n) {
        return Long.toString(n);
    }

    private String format(double v) {
        return String.format(Locale.US, "%2.2f", v);
    }
}