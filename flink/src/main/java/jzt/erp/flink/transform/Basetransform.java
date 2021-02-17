package jzt.erp.flink.transform;

import jzt.erp.flink.beans.SensorReading;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author panjj
 * @create 2021/2/17 - 19:53
 * }
 */
public class Basetransform {
    public static void main(String[] args)  throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //env.setParallelism(8);默认核心数

        //1从文件读取数据
        DataStream<String> source = env.readTextFile("C:\\Users\\panjj\\IdeaProjects\\jzt\\flink\\src\\main\\resources\\sensor.txt");

        //一条数据返回一条数据
        SingleOutputStreamOperator<Integer> map = source.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String s) throws Exception {
                return s.length();
            }
        });
        map.print("map");

        //一条数据返回一条或多条数据
        SingleOutputStreamOperator<Object> flatmap = source.flatMap(new FlatMapFunction<String, Object>() {
            @Override
            public void flatMap(String s, Collector<Object> collector) throws Exception {
               for(String substr : s.split(","))
                {
                    collector.collect(substr);
                }
            }
        });
        flatmap.print("flatmap");

        //返回为true的数据
        SingleOutputStreamOperator<String> filter = source.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String s) throws Exception {

                return s.startsWith("sensor_1");
            }
        });
        filter.print("filter");

        env.execute("my jobname");
    }
}
