package jzt.erp.flink.transform;

import jzt.erp.flink.beans.SensorReading;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author panjj
 * @create 2021/2/17 - 20:36
 * }
 */
public class RollingAggregation {
    public static void main(String[] args)  throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);//默认核心数
        DataStream<String> source = env.readTextFile("C:\\Users\\panjj\\IdeaProjects\\jzt\\flink\\src\\main\\resources\\sensor.txt");

        SingleOutputStreamOperator<SensorReading> map = source.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] fields = s.split(",");
                SensorReading info = new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
                return info;
            }
        });
        /*
        SingleOutputStreamOperator<SensorReading> map2 = source.map(line -> {
            String[] fields = line.split(",");
            SensorReading info = new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            return info;
        });
        map2.print("map2");
        */




        //KeyedStream<SensorReading, Tuple> keyby = map.keyBy(0);  //位置只能返回，tuple
        KeyedStream<SensorReading, Tuple> keyedStream = map.keyBy("id");
        //KeyedStream<SensorReading, String> keyedStream = map.keyBy(date -> date.getId());
        //KeyedStream<SensorReading, String> keyedStream = map.keyBy(SensorReading::getId);

        //SingleOutputStreamOperator<SensorReading> temperature = keyedStream.max("temperature");

        //当前温度值最大的记录，显示成当前行的信息,按最大值所在的行来的
        SingleOutputStreamOperator<SensorReading> temperature = keyedStream.maxBy("temperature");

        //当前第一条的非max列，会保留第一条，只把最大值那一天，更新成最大值，其它列保持不变
        //SingleOutputStreamOperator<SensorReading> temperature = keyedStream.max("temperature");
        temperature.print();







        env.execute("my jobname");
    }
}
