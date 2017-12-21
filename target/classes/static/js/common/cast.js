/**
 * 常量
 */
define(function(require, exports, module){
   var cast = {};
   
   module.exports = cast;
    
   //api请求地址
   cast.apiUrl = "http://127.0.0.1:8088";

   /**
    * token有效期
    * 2分钟
    * @type {string}
    */
   cast.tokentime = 20*1000;
   /**
    *
    * 登录页面密码输入4次锁定账户1个小时
    *
    */
   cast.loginErrorTime=60*60*1000;

   /**
    * 服务器返回状态
    * @returns {Object}
    */
   cast.returnState = function(){
      var state = new Object();
      state.SUCCESS = "0000"; //"成功"
      state.FAIL = "1001"; //业务失败"
      state.ROLELOGIN = "1002"; //用户未登陆"
      state.RELNAME = "1003"; //用户未实名认证"
      state.INVALIDTOKEN = "2000"; //无效token"
      state.TOKENEXPIRATION = "2001"; //token过期"
      state.INVALIDSIGNATURE = "2002"; //签名无效"
      state.TOKENLOSE = "2003"; //token丢失"
      state.PARAMETERMISSING = "3000"; //参数丢失"
      state.PARAMETERTYPEMISMATCHING = "3001"; //参数格式错误"
      state.REPEATOPTS = "9000"; //重复提交"
      state.ERROR = "9999"; //系统错误"
      state.ILLEGAL = "500"; //非法请求"
      return state;
   }
});
