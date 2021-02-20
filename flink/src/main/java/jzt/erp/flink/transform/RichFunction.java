package jzt.erp.flink.transform;

import jzt.erp.flink.beans.SensorReading;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import scala.Tuple2;

/**
 * @author panjj
 * @create 2021/2/20 - 9:20
 * }
 */
public class RichFunction {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);//核心数
        DataStream<String> source = env.readTextFile("C:\\Users\\panjj\\IdeaProjects\\jzt\\flink\\src\\main\\resources\\sensor.txt");

        //转对象
        SingleOutputStreamOperator<SensorReading> map2 = source.map(line -> {
            String[] fields = line.split(",");
            SensorReading info = new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            return info;
        });
        //map2.print("map2");

        SingleOutputStreamOperator<Tuple2<SensorReading, Number>> map = map2.map(new RichMapFunction<SensorReading, Tuple2<SensorReading, Number>>() {
            @Override
            public Tuple2<SensorReading, Number> map(SensorReading sensorReading) throws Exception {
                return new Tuple2<>(sensorReading, getRuntimeContext().getIndexOfThisSubtask());
            }

            @Override
            public void open(Configuration parameters) throws Exception {
                System.out.println("open");
            }

            @Override
            public void close() throws Exception {
                System.out.println("close");
            }

        });

        map.print("RichMapFunction");

        env.execute();



    }
}