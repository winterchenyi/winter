package com.yestic.winter.storm.demo2;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by chenyi on 2018/1/10
 */
public class WordCountTopolopgyAllInJava {
    // 定义一个喷头，用于产生数据。该类继承自BaseRichSpout
    public static class RandomSentenceSpout extends BaseRichSpout {
        private SpoutOutputCollector _collector;
        private int index;
        private String[] sentences = {
                "A B C D E F",
                "G H I J A B C D",
                "W X Y Z" };

        @Override
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector){
            _collector = collector;
        }

        @Override
        public void nextTuple(){

            if(sentences.length <= index){
                return;
            }

            // 睡眠一段时间后再产生一个数据
            Utils.sleep(100);

            // 发射该句子给Bolt
            _collector.emit(new Values(sentences[index]));
            index++;

        }

        // 确认函数
        @Override
        public void ack(Object id){
        }

        // 处理失败的时候调用
        @Override
        public void fail(Object id){
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer){
            // 定义一个字段word
            declarer.declare(new Fields("word"));
        }
    }

    // 定义个Bolt，用于将句子切分为单词
    public static class SplitSentence extends BaseBasicBolt{
        @Override
        public void execute(Tuple tuple, BasicOutputCollector collector){
            // 接收到一个句子
            String sentence = tuple.getString(0);
            // 把句子切割为单词
            StringTokenizer iter = new StringTokenizer(sentence);
            // 发送每一个单词
            while(iter.hasMoreElements()){
                collector.emit(new Values(iter.nextToken()));
            }
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer){
            // 定义一个字段
            declarer.declare(new Fields("word"));
        }
    }

    // 定义一个Bolt，用于单词计数
    public static class WordCount extends BaseBasicBolt {
        Map<String, Integer> counts = new HashMap<>();

        @Override
        public void execute(Tuple tuple, BasicOutputCollector collector){
            // 接收一个单词
            String word = tuple.getString(0);
            // 获取该单词对应的计数
            Integer count = counts.get(word);
            if(count == null)
                count = 0;
            // 计数增加
            count++;
            // 将单词和对应的计数加入map中
            counts.put(word,count);
            System.out.println("hello word!");
            System.out.println(word +"	"+count);
            // 发送单词和计数（分别对应字段word和count）
            collector.emit(new Values(word, count));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer){
            // 定义两个字段word和count
            declarer.declare(new Fields("word","count"));
        }
    }
    public static void main(String[] args) throws Exception
    {
        // 创建一个拓扑
        TopologyBuilder builder = new TopologyBuilder();
        // 设置Spout，这个Spout的名字叫做"Spout"，设置并行度为5
        builder.setSpout("spout", new RandomSentenceSpout(), 5);
        // 设置slot——“split”，并行度为8，它的数据来源是spout的
        builder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");
        // 设置slot——“count”,你并行度为12，它的数据来源是split的word字段
        builder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", new Fields("word"));

        Config conf = new Config();
        conf.setDebug(false);

        //if(args != null && args.length > 0){
        //if(false){
        //	conf.setNumWorkers(3);
        //	StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        //}else{
        conf.setMaxTaskParallelism(3);

        // 本地集群
        LocalCluster cluster = new LocalCluster();

        // 提交拓扑（该拓扑的名字叫word-count）
        cluster.submitTopology("word-count", conf, builder.createTopology() );

        Thread.sleep(10000);
        //	cluster.shutdown();
        //}
    }
}
