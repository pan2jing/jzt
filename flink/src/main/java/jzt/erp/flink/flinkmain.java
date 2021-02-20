package jzt.erp.flink;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import scala.annotation.meta.param;

import java.lang.reflect.Parameter;

/**
 * @author panjj
 * @create 2021/2/8 - 8:11
 *
 */
public class flinkmain {
    public static void main(String[] args) throws Exception{
        System.out.println("start:");
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //从文件读取,批处理
        String path ="C:\\Users\\panjj\\IdeaProjects\\jztflink\\flink\\src\\main\\resources\\Hello.txt";
        DataSource<String> dataSource = env.readTextFile(path);
        DataSet<Tuple2<String, Integer>> sum = dataSource.flatMap(new myFlatmapper())
                .groupBy(0)
                .sum(1);
        sum.print();


        StreamExecutionEnvironment streamenv = StreamExecutionEnvironment.getExecutionEnvironment();
        //streamenv.setParallelism(4);

        //https://www.bilibili.com/video/BV1qy4y1q728?p=9
        //ParameterTool param = ParameterTool.fromArgs(args);
        //String p1 = param.get("host");
        //Integer p2 = param.getInt("7777");


        //1 linux   敲 nc -lk 7777
        //2 启动本服务
        //3 在Linux 敲 hello world
        //4 在输出可以看效果
        /*DataStream<String> dataStream = streamenv.socketTextStream("localhost",7777);
        DataStream<Tuple2<String, Integer>> result = dataStream.flatMap(new myFlatmapper())
                .keyBy(0)
                .sum(1);
        result.print();
        streamenv.execute();
        */

    }
    public static class myFlatmapper implements  FlatMapFunction<String, Tuple2<String,Integer>>
    {

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            String[] worlds =s.split(" ");
            for (String t:worlds) {
                collector.collect(new Tuple2<>(t,1));
            }
        }
    }

}

