package jzt.erp.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author panjj
 * @create 2021/2/12 - 16:57
 */

//jzt.erp.flink.StreamWordCount
public class StreamWordCount {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //StreamExecutionEnvironment.createLocalEnvironment();

        // 获取集群执行环境
        StreamExecutionEnvironment env2 = StreamExecutionEnvironment.createRemoteEnvironment("localhost",6123);

        //设置并行度
        //env.setParallelism(8);
        //env.disableOperatorChaining();//所有步骤,不参与到任务链的合并中了

        //从文件读取,流处理
        //String path ="C:\\Users\\panjj\\IdeaProjects\\jzt\\flink\\src\\main\\resources\\Hello.txt";
        //DataStreamSource<String> dataStream = env.readTextFile(path);


        //https://www.bilibili.com/video/BV1qy4y1q728?p=9
        /*   设置启动参数--host localhost --port 7777    */
        ParameterTool param = ParameterTool.fromArgs(args);
        String hostname = param.get("host");
        int port = param.getInt("port");


        //1 linux   敲 nc -lk 7777
        //2 启动本服务
        //3 在Linux 敲 hello world
        //4 在输出可以看效果
        DataStream<String> dataStream = env.socketTextStream(hostname,port);

        DataStream<Tuple2<String, Integer>> resultstream = dataStream.flatMap(new flinkmain.myFlatmapper())
                .keyBy(0)
                .sum(1).setParallelism(2).slotSharingGroup("red")
                //.shuffle()//重分区
                //.disableChaining()//不参与到任务链的合并中了
                //.startNewChain()//后面又可以合并了
                ;

        resultstream.print("streamname").setParallelism(1);

        //执行流任务
        env.execute();
    }
}
