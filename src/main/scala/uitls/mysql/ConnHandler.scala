package uitls.mysql

import java.sql.{DriverManager, PreparedStatement}

import com.mysql.jdbc.Driver


/**
  * 这是一个MySQL 数据库的处理器
  * 主要负责：
  * 1。 数据库连接
  * 2。 数据插入操作
  */
object ConnHandler {
  val conn = DriverManager.getConnection("jdbc:mysql://server01:3306/cara", "root", "root")


  /**
    * 直接提供sql 执行
    *
    * @param sql
    */
  def insertResult(sql: String): Unit = {
    if (sql != null) {
      conn.prepareStatement(sql).executeUpdate()
    }
  }

  /*使用示例
   var conn = new ConnHandler()
    conn.setSql_Ps("")

    val result = conn.insertResult()
    print(result)
   */
}
