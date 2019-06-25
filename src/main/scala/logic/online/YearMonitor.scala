package logic.online

import com.alibaba.fastjson.JSONObject
import logic.traits.Monitor


/**
  * 对司机年龄监控
  */
object YearMonitor extends Monitor{

  /**
    * 对司机年龄大于60的记录
    * @param json
    * @return
    */
  override def execMonitor(json: JSONObject): Boolean = {
    if(json.getString("owner_age").toInt>=60){
      true
    }else{
      false
    }
  }
}
