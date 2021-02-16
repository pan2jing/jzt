package jzt.erp.flink.jzt.erp.flink.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.util.Properties;

/**
 * @author panjj
 * @create 2021/2/16 - 19:30
 * }
 */
public class SourceTest3_Kafka {
    public static void main(String[] args)  throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //env.setParallelism(8);默认核心数

        //1从kafka读取数据

        Properties pro = new Properties();
        pro.setProperty("bootstrap.servers","localhost:9092");

        DataStream<String> source = env.addSource
                (new FlinkKafkaConsumer011<String>("sensor",new SimpleStringSchema(),pro));

        source.print("TestFile");

        env.execute("my jobname");
    }
}
