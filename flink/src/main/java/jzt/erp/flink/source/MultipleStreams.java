package jzt.erp.flink.source;

import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author panjj
 * @create 2021/2/19 - 10:41
 * }
 */
public class MultipleStreams {
    public static void main(String[] args)  throws  Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //env.setParallelism(8);默认核心数

        //1从集合读取数据
        DataStream<String> source = env.readTextFile("C:\\Users\\panjj\\IdeaProjects\\jzt\\flink\\src\\main\\resources\\sensor.txt");

        /*
        source.map()
        source.split(new OutputSelector<String>() {
            @Override
            public Iterable<String> select(String s) {
                return null;
            }
        })
        */


        source.print("TestFile");

        env.execute("my jobname");
    }
}
