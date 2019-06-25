package logic.online

import com.alibaba.fastjson.JSONObject
import logic.traits.Monitor

/**
  * 这是一个用户监控逻辑对象， 继承监控接口
  */
object UserMonitor extends Monitor{

  override def execMonitor(json: JSONObject): Boolean = {
    //对女性用户监控
    if(json.getString("user_sex").equals("女")){
      true
    }else{
      false
    }
  }
}
