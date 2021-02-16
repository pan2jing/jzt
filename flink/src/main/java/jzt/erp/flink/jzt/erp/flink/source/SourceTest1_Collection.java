package jzt.erp.flink.jzt.erp.flink.source;

import jzt.erp.flink.beans.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

/**
 * @author panjj
 * @create 2021/2/16 - 17:07
 * }
 */
public class SourceTest1_Collection {
    public static void main(String[] args)  throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //env.setParallelism(8);默认核心数

        //1从集合读取数据
        DataStream<SensorReading> source = env.fromCollection(Arrays.asList(
                new SensorReading("sensor_1", 1547718199L, 35.8),
                new SensorReading("sensor_6", 1547718201L, 15.4),
                new SensorReading("sensor_7", 1547718202L, 6.7),
                new SensorReading("sensor_10", 1547718205L, 38.1)

        ));

        DataStreamSource<Integer> integerDataStreamSource = env.fromElements(1, 2, 3);
        source.print("Arraylist Stream");
        integerDataStreamSource.print("integerDataStreamSource");

        env.execute("my jobname");
    }
}


