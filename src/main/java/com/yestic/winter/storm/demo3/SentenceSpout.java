//package com.yestic.winter.storm.demo3;
//import java.util.Map;
//import org.apache.storm.spout.SpoutOutputCollector;
//import org.apache.storm.task.TopologyContext;
//import org.apache.storm.topology.OutputFieldsDeclarer;
//import org.apache.storm.topology.base.BaseRichSpout;
//import org.apache.storm.tuple.Fields;
//import org.apache.storm.tuple.Values;
//import org.apache.storm.utils.Utils;
//
///**
// * Spout读取文本，然后发送到第一个bolt对文本进行切割，
// * 然后在对切割好单词把相同的单词发送给第二个bolt同一个task来统计，
// * 这些过程可以利用多台服务器帮我们完成。
// * 组件有spout、bolt、Stream groupings（shuffleGrouping、fieldsGrouping）、Topology
// *
// * 创建spout数据源
// * Created by chenyi on 2018/1/10
// */
//@SuppressWarnings("serial")
//public class SentenceSpout extends  BaseRichSpout {
//
//    private SpoutOutputCollector collector;
//    private String[] sentences = {
//            "the cow jumped over the moon",
//            "an apple a day keeps the doctor away",
//            "four score and seven years ago",
//            "snow white and the seven dwarfs",
//            "i am at two with nature" };
//
//    private int index = 0;
//
//    @Override
//    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        //定义输出字段描述
//        declarer.declare(new Fields("word"));
//    }
//
//    // 初始化，只执行1次
//    public void open(Map config, TopologyContext context,SpoutOutputCollector collector) {
//        this.collector = collector;
//    }
//
//
//    //不停的被调用
//    public void nextTuple() {
//        if(sentences.length<index){
//            return;
//        }
//        //发送字符串
//        this.collector.emit(new Values(sentences[index]));
//        index++;
//        Utils.sleep(1);
//    }
//}
