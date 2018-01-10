//package com.yestic.winter.storm.demo3;
//import java.util.HashMap;
//import java.util.Map;
//import org.apache.storm.task.TopologyContext;
//import org.apache.storm.topology.BasicOutputCollector;
//import org.apache.storm.topology.OutputFieldsDeclarer;
//import org.apache.storm.topology.base.BaseBasicBolt;
//import org.apache.storm.tuple.Fields;
//import org.apache.storm.tuple.Tuple;
//import org.apache.storm.tuple.Values;
//
///**
// * 对单词进行统计bolt
// * Created by chenyi on 2018/1/10
// */
//@SuppressWarnings("serial")
//public class WordCountBolt extends BaseBasicBolt {
//
//    private  Map<String, Long> counts = null;
//
//
//    @SuppressWarnings("rawtypes")
//    @Override
//    public void prepare(Map stormConf, TopologyContext context) {
//        this.counts = new HashMap<String, Long>();
//    }
//
//    @Override
//    public void cleanup() {
//        //拓扑结束执行
//        for (String key : counts.keySet()) {
//            System.out.println(key + " : " + this.counts.get(key));
//        }
//    }
//
//    @Override
//    public void execute(Tuple input, BasicOutputCollector collector) {
//        String word = input.getString(0);
//        Long count = this.counts.get(word);
//        if (count == null) {
//            count = 0L;
//        }
//        count++;
//        this.counts.put(word, count);
//        System.out.println("----");
//        System.out.println(word +"	"+count);
//        // 发送单词和计数（分别对应字段word和count）
//        collector.emit(new Values(word, count));
//    }
//
//    @Override
//    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        // 定义两个字段word和count
//        declarer.declare(new Fields("word","count"));
//    }
//
//}
//
