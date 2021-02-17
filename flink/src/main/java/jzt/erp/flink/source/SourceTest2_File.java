package jzt.erp.flink.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author panjj
 * @create 2021/2/16 - 19:20
 * }
 */
public class SourceTest2_File {
    public static void main(String[] args)  throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //env.setParallelism(8);默认核心数

        //1从集合读取数据
        DataStream<String> source = env.readTextFile("C:\\Users\\panjj\\IdeaProjects\\jzt\\flink\\src\\main\\resources\\sensor.txt");

        source.print("TestFile");

        env.execute("my jobname");
    }
}
