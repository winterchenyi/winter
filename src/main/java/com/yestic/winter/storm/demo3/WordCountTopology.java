//package com.yestic.winter.storm.demo3;
//import org.apache.storm.Config;
//import org.apache.storm.LocalCluster;
//import org.apache.storm.StormSubmitter;
//import org.apache.storm.topology.TopologyBuilder;
//import org.apache.storm.tuple.Fields;
//
///**
// * 创建Topology拓扑
// * 通过Stream groupings把spouts和Bolts串联起来组成了流数据处理，并设置spout和bolt处理的并行数。
// * 拓扑运行模式：本地模式和分布式模式。
// * Created by chenyi on 2018/1/10
// */
//public class WordCountTopology {
//    public static void main(String[] args) throws Exception {
//
//        TopologyBuilder builder = new TopologyBuilder();
//        builder.setSpout("spout", new SentenceSpout(), 1);
//        builder.setBolt("split", new SplitSentenceBolt(), 2).shuffleGrouping("spout");
//        builder.setBolt("count", new WordCountBolt(), 2).fieldsGrouping("split", new Fields("word"));
//
//        Config conf = new Config();
//        conf.setDebug(false);
//
//        /*if (args != null && args.length > 0) {
//            // 集群模式
//            conf.setNumWorkers(2);
//            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
//        } else {
//            // 本地模式
//            LocalCluster cluster = new LocalCluster();
//            cluster.submitTopology("word-count", conf, builder.createTopology());
//            Thread.sleep(10000);
//            cluster.shutdown();
//        }*/
//        // 本地模式
//        conf.setMaxTaskParallelism(3);
//        LocalCluster cluster = new LocalCluster();
//        cluster.submitTopology("word-count", conf, builder.createTopology());
//    }
//}
