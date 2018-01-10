//package com.yestic.winter.storm.demo3;
//import org.apache.storm.topology.BasicOutputCollector;
//import org.apache.storm.topology.OutputFieldsDeclarer;
//import org.apache.storm.topology.base.BaseBasicBolt;
//import org.apache.storm.tuple.Fields;
//import org.apache.storm.tuple.Tuple;
//import org.apache.storm.tuple.Values;
//
//import java.util.StringTokenizer;
//
///**
// * 定义个Bolt，用于将句子切分为单词
// * Created by chenyi on 2018/1/10
// */
//@SuppressWarnings("serial")
//public class SplitSentenceBolt extends BaseBasicBolt {
//
//    @Override
//    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        //定义了传到下一个bolt的字段描述
//        declarer.declare(new Fields("word"));
//    }
//
//    @Override
//    public void execute(Tuple input, BasicOutputCollector collector) {
//        String sentence = input.getString(0);
//        StringTokenizer iter = new StringTokenizer(sentence);
//        while(iter.hasMoreElements()){
//            collector.emit(new Values(iter.nextToken()));
//        }
//    }
//}
