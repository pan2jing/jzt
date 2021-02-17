package jzt.erp.flink.source;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * @author panjj
 * @create 2021/2/16 - 19:30
 * }
 */
/**
 * Desc: 使用flink对指定窗口内的数据进行实时统计，最终把结果打印出来
 * 从kafka读取数据的wordcount    输入格式为   以空格分隔
 */
public class SourceTest3_Kafka {
    public static void main(String[] args)  throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 非常关键，一定要设置启动检查点！！
        //env.enableCheckpointing(5000);
        //env.setParallelism(8);默认核心数
        //1从kafka读取数据
        Properties pro = new Properties();
        pro.setProperty("bootstrap.servers","localhost:9092");
        //pro.setProperty("group.id", "1");

         //  ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic sensor

        FlinkKafkaConsumer011<String> consumer = new FlinkKafkaConsumer011<>(
                "sensor", new SimpleStringSchema(), pro);
        //设置只读取最新数据
        consumer.setStartFromLatest();  //这里不设好像读不到数据

        //添加kafka为数据源
        DataStream<String> source = env.addSource(consumer);

        DataStream<Tuple2<String, Integer>> counts = source.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = s.toLowerCase().split("\\s+");
                for (String token : tokens) {
                    if (token.length() > 0) {
                        collector.collect(new Tuple2<String, Integer>(token, 1));
                    }
                }
            }
        }).keyBy(0)//分组
                //求和
                .sum(1);
        counts.print("TestKafka");

        env.execute("my jobname");
    }
}
