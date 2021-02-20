package jzt.erp.flink.Sink;

import jzt.erp.flink.beans.SensorReading;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducerBase;
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * @author panjj
 * @create 2021/2/20 - 10:51
 * }
 */
public class sinKafka {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);//核心数
        //DataStream<String> source = env.readTextFile("C:\\Users\\panjj\\IdeaProjects\\jzt\\flink\\src\\main\\resources\\sensor.txt");

        Properties pro = new Properties();
        pro.setProperty("bootstrap.servers","10.4.10.54:9092");
        pro.setProperty("group.id", "pjj_consumer_test1");
        FlinkKafkaConsumer011<String> consumer = new FlinkKafkaConsumer011<>(
                "pjjTestTopic", new org.apache.flink.streaming.util.serialization.SimpleStringSchema(), pro);
        //设置只读取最新数据
        consumer.setStartFromLatest();  //这里不设好像读不到数据
        //添加kafka为数据源
        DataStream<String> source = env.addSource(consumer);


        //转对象
        SingleOutputStreamOperator<String> datastream = source.map(line -> {
            String[] fields = line.split(",");
            SensorReading info = new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
            return info.toString();
        });
        datastream.print();


        //datastream.addSink(new FlinkKafkaProducer011<SensorReading>("10.4.10.54:9092","pjjTestTopic",new SimpleStringSchema())   );
        DataStreamSink<String> pjjTestTopic = datastream.addSink(new FlinkKafkaProducer011<String>("10.4.10.54:9092", "pjjFlinkAddTestTopic", new SimpleStringSchema()));

        env.execute();


    }
}
