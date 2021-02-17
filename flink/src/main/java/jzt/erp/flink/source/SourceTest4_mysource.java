package jzt.erp.flink.source;

import jzt.erp.flink.beans.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.HashMap;
import java.util.Random;

/**
 * @author panjj
 * @create 2021/2/17 - 19:20
 * }
 */
public class SourceTest4_mysource {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<SensorReading> stream = env.addSource( new MySource());

        stream.print();

        env.execute();

    }


    public static class MySource implements SourceFunction<SensorReading> {
        private boolean isrun = true;

        @Override
        public void run(SourceContext<SensorReading> sourceContext) throws Exception {
            Random random = new Random();
            HashMap<String, Double> map = new HashMap<>();
            for (int i = 0; i < 10; i++) {
                map.put("sensor_" + (i + 1), random.nextGaussian() * 20);
            }
            while (isrun) {
                for (String key : map.keySet()) {
                    Double newwd = map.get(key) + random.nextGaussian();
                    map.put(key, newwd);
                    sourceContext.collect(new SensorReading(key, System.currentTimeMillis(), newwd));
                }
                Thread.sleep(1000L);
            }
        }

        @Override
        public void cancel() {
            isrun = false;
        }
    }
}

