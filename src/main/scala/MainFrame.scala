import lib.Libs
import spark.streaming.StreamConsumer
import uitls.hive.HiveHandler
import uitls.mysql.MysqlFieldsHandler

import scala.io.StdIn

object MainFrame {
  /**
    *
    *  主方法入口：--------离线实时业务选择----------------->选择具体业务---->匹配该业务的需要参数和逻辑|
    *                |                                                       Libs.OFF_CONF_MAP
    *                |                                                     业务参数如下：
    *                |                                                             1. 将分析结果插入MySQL的对应结果表的表名（tableName）
    *                |                                                             2. 从hive中获取的数据的列名
    *                |                                                             3. 执行的查询：HQL
    *                实时业务                                                          |
    *                 |                                                               |
    *                 |                                               将1\2 参数给MySQLFieldHandler处理字段
    *          获取具体实时业务参数信息：                                 将3 参数 给HiveHandler读取分析数据并将结果插入MySQL
    *          1. MySQL结果收集的表名                                               |
    *          2. 对json数据处理字段名                                              |
    *          3. 消费的topic名                                                   |
    *          4. 监控的业务逻辑                                                    |
    *            |                                                                 |
    *            |                                                                 |
    *            |                                                                 |
    *          1\2参数由MysqlFieldHandler处理                                        |
    *          4.参数 由MysqlFieldHandler配置监控逻辑                                 |
    *          3.参数和MySQLFieldHandler 由streamConsumer消费并执行业务逻辑            |
    *          结果插入MySQL                                                       |
    *             |                                                               |
    *             ————————————————————————————————————————————————————————————————
    *                |
    *                结束
    *
    *
    * @param args
    */
  def main(args:Array[String]): Unit ={
    print("业务如下：\n"+
      "\t1. 离线业务\n" +
      "\t2. 实时业务\n\n" +
      "请输入业务编号： ")
    val scan = StdIn.readInt()
    scan match{
      case 1=> offFun()
      case 2=> onlineFun()
      case _ => print("输入错误！！！")
    }

//    StreamConsumer.toConsumer("car_table",ProvinceSelect)

    //离线业务
//    val olc = new OrderLocalCount()
//    olc.setTable("order_local_table",Seq("address","count"))
//
//    HiveHandler.readHive2Mysql(Libs.OFF_SQL_LIST("local_count"),olc)

//    ConnHandler.insertResult("insert into test.peter value('hans',34)")


  }

  /**
    * 离线需求入口
    *
    */
  def offFun(): Unit = {
    print("离线业务如下：\n" +
      "\t1. 查询每个地区订单数量并排序\n" +
      "\t2. 查询人口流向每个地区的数量并排序\n" +
      "\t3. 查询每种货物类型数量并排序\n" +
      "\t4. 查询每个地区订单成交总金额排名\n" +
      "\t5. 查询每个地区外地车主人数，并排名\n\n" +
      "请输入： ")
    val scan = StdIn.readInt()


    if (scan > 0 && scan < 6) {

      val conf = Libs.OFF_CONFIG_MAP(scan)

      //conf._1 是MySQL结果表的表名， conf._2 是HQL查询结果的字段名
      //conf._3 是HQL语句 ， MySQLFieldHandler是对查询结果处理并生产MySQL的插入语句的工具对象
      //HiveHandler 执行HQL语句，并将结果插入MySQL（最终调用的ConnHandler的插入数据）
      MysqlFieldsHandler.setTable(conf._1,conf._2)
      HiveHandler.readHive2Mysql(conf._3, MysqlFieldsHandler)
    }
  }


  /**
    * 实时需求入口
    */
  def onlineFun(): Unit ={
    print("实时业务：'\n" +
      "\t1. 监控失败的订单\n" +
      "\t2. 监控金额过7万的订单\n" +
      "\t3. 监控京牌车辆信息\n" +
      "\t4. 监控女性用户使用信息\n" +
      "\t5. 实时监控超过60岁工作车主\n\n" +
      "请选择： ")
    val scan = StdIn.readInt()

    if (scan>0 && scan <6){
      val conf = Libs.ON_CONFIG_MAP(scan)


      //conf._1 是MySQL结果表的表名， conf._2 是HQL查询结果的字段名
      //conf._3 是消费的topic名  ，  conf._4 是监控逻辑
      // MySQLFieldHandler是对查询结果处理并生产MySQL的插入语句的工具对象,同时可以设置监控业务逻辑
      MysqlFieldsHandler.setTable(conf._1,conf._2)

      //StreamConsumer 执行消费，并将结果插入MySQL（最终调用的ConnHandler的插入数据）
      MysqlFieldsHandler.setMonitor(conf._4)
      StreamConsumer.toConsumer(conf._3,MysqlFieldsHandler)
    }
  }

}
