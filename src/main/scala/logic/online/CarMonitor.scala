package logic.online

import com.alibaba.fastjson.JSONObject
import logic.traits.Monitor

object CarMonitor extends Monitor{

  /**
    * 对京牌车辆监控
    * @param json
    * @return
    */
  override def execMonitor(json: JSONObject): Boolean = {
    if(json.getString("car_num").contains("京")){
      true
    }else{
      false
    }
  }
}
