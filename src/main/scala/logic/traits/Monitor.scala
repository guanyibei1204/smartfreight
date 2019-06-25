package logic.traits

import com.alibaba.fastjson.JSONObject

/**
  * 实时监控逻辑
  */
trait Monitor extends Serializable {

  def execMonitor(json:JSONObject):Boolean
}
