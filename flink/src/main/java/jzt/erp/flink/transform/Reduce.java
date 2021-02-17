package jzt.erp.flink.transform;

import jzt.erp.flink.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author panjj
 * @create 2021/2/17 - 21:51
 * }
 */
public class Reduce {
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
        KeyedStream<SensorReading, Tuple> keyedStream = map.keyBy("id");

        /*SingleOutputStreamOperator<SensorReading> temperature = keyedStream.reduce(new ReduceFunction<SensorReading>() {
            @Override
            public SensorReading reduce(SensorReading oldvalue, SensorReading currvalue) throws Exception {
                return new SensorReading(currvalue.getId(),currvalue.getTimestamp(), Math.max(oldvalue.getTemperature(), currvalue.getTemperature()));
            }
        });

        --对上面的另一种写法
        SingleOutputStreamOperator<SensorReading> temperature = keyedStream.reduce((oldvalue,currvalue) ->{
            return new SensorReading(currvalue.getId(),currvalue.getTimestamp(), Math.max(oldvalue.getTemperature(), currvalue.getTemperature()));
        });*/

        SingleOutputStreamOperator<SensorReading> temperature = keyedStream.reduce((oldvalue,currvalue) ->{
            return new SensorReading(currvalue.getId(),currvalue.getTimestamp(),  (oldvalue.getTemperature()+currvalue.getTemperature()));
        });
        temperature.print();

        env.execute("my jobname");
    }
}

