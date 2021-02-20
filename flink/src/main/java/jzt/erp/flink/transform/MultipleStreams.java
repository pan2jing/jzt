package jzt.erp.flink.transform;

import jzt.erp.flink.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

import java.util.Collection;
import java.util.Collections;

/**
 * @author panjj
 * @create 2021/2/19 - 15:52
 * }
 */
public class MultipleStreams {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //env.setParallelism(8);默认核心数

        //1从集合读取数据
        DataStream<String> source = env.readTextFile("C:\\Users\\panjj\\IdeaProjects\\jzt\\flink\\src\\main\\resources\\sensor.txt");

        SingleOutputStreamOperator<SensorReading> map = source.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] fields = s.split(",");
                return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            }
        });

        //分解流
        SplitStream<SensorReading> split = map.split(new OutputSelector<SensorReading>() {
            @Override
            public Iterable<String> select(SensorReading sensorReading) {
                return sensorReading.getTemperature() > 30 ? Collections.singleton("hight") : Collections.singleton("low");
            }
        });
        DataStream<SensorReading> hight = split.select("hight");
        //hight.print("hight");
        DataStream<SensorReading> low =split.select("low");
        //low.print("low");
        DataStream<SensorReading> all =split.select("hight","low");
        //all.print("all");


        //构建异源流
        SingleOutputStreamOperator<Tuple2<String, Double>> type2 = hight.map(new MapFunction<SensorReading, Tuple2<String, Double>>() {
            @Override
            public Tuple2<String, Double> map(SensorReading sensorReading) throws Exception {
                return new Tuple2<>(sensorReading.getId(), sensorReading.getTemperature());
            }
        });
        //合并流
        ConnectedStreams<Tuple2<String, Double>, SensorReading> connect = type2.connect(low);
        //处理异源流
        SingleOutputStreamOperator<Object> map1 = connect.map(new CoMapFunction<Tuple2<String, Double>, SensorReading, Object>() {
            @Override
            public Object map1(Tuple2<String, Double> value) throws Exception {
                return new Tuple3<>(value.f0, value.f1, "high waring");
            }
            @Override
            public Object map2(SensorReading sensorReading) throws Exception {
                return new Tuple3(sensorReading.getId(), sensorReading.getTemperature(), "low");
            }
        });
        map1.print("异源流");

        //合并同源流
        DataStream<SensorReading> union = hight.union(all);
        union.print("同源流");

        env.execute("my jobname");
    }
}
