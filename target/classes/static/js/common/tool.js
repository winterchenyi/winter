define(function(require, exports, module){
	var $ = jQuery = require("jquery");
	var tool={};
	var httptool={};
	var cast = require("common/cast");
	
	require("common/es6-promise").polyfill();
	var axios = require("common/axios");
	
	module.exports=tool;

	//判断为空
	tool.isEmpty=function(value){
		if(value==null){
			return true;
		}
		var type=Object.prototype.toString(value).slice(8,-1);
		switch(type) {
	        case 'String':
	            return !$.trim(value);
	        case 'Array':
	            return !value.length;
	        case 'Object':
	            return $.isEmptyObject(value);
	        default:
	            return false;
	    }
	};
	
	//判断是否是对象
	tool.isObject=function(value){
		if(value==null){
			return false;
		}
		var type=Object.prototype.toString(value).slice(8,-1);
		if(type == "Object"){
			return true;
		}else{
			return false;
		}
	};
	//分页对象
	tool.page = function(options){
	    var page = new Object();
	    if(!tool.isEmpty(options)){
	        page = options;
	    }else{
	        page.currentPage = 1;//当前页
	        page.pageSize = 15;//每页条数
	        page.pageTotal = 1;//总页数
	    }
	    return page;
	};
	
	//获取url参数
	tool.getUrlParams=function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		if (r!=null) return unescape(r[2]); return null; //返回参数值
	};

	/**
	 * 用当前事件 + 设置的缓存时长 得到 缓存数据的最后有效时间;
	 * 在包装对象中加入 end 属性为缓存最后的有效时间；
	 * @param key
	 * @param value
	 * @param exporttime 有效期
	 * @param nowtime 存放时间
	 */
	tool.setStorage = function(key, value, exptime,nowtime){
		var item;
		if(exptime != null && exptime != '' && typeof exptime != undefined){
			if(nowtime == null || nowtime == '' && typeof nowtime == undefined){
				nowtime = new Date().getTime();
			}
			item = {
				data: value,
				end:  nowtime + exptime
			};
		}else{
			item = {
				data: value
			};
		}
//		localStorage.setItem(key, JSON.stringify(item));
		sessionStorage.setItem(key, JSON.stringify(item));
	};

	/**
	 * 获取缓存数据时根据当前时间和缓存最后有效时间比较，如果过期则删除缓存返回null;
	 * @param key
	 * @returns {null}
	 */
	tool.getStorage = function(key){
		var item = sessionStorage.getItem(key);
		if(!item) return null;
		item = JSON.parse(item);
		if(item.end != null && item.end != '' && typeof item.end != undefined){
			if(new Date().getTime() > item.end){
				item = null;
				sessionStorage.removeItem(key);
			}else{
				item = item.data;
			}
		}else{
			item =  item.data;
		}
		return item;
	};

	/**
	 * 清除缓存;
	 * @param key
	 */
	tool.removeStorage = function(key){
		sessionStorage.removeItem(key);
	};


	/**
	 * 构建访问参数实体
	 * @param token 认证标示
	 * @param body 具体业务参数
	 * @param page 分页对象
	 * @param sign 签名
	 */
	httptool.requestBody = function(body,page,sign){
		var requestBody= new Object();
		requestBody.rnd = Math.ceil(Math.random()*10000);
		requestBody.timestamp = new Date().getTime();
		requestBody.appId = cast.appId;


		if(!tool.isEmpty(page)){
			requestBody.page = page;
		}

		if(!tool.isEmpty(sign)){
			requestBody.sign = sign;
		}

		if(!tool.isEmpty(body)){
			requestBody.body = body;
		}
		return requestBody;
	};
	
	
//
//	/**
//	 * 获取签名
//	 */
//	httptool.getSign = function(requestBody){
//		
//		var tempRequestBody = new Object();
//		
//		
//		tempRequestBody.rnd = requestBody.rnd;
//		tempRequestBody.timestamp = requestBody.timestamp;
//		tempRequestBody.appId = requestBody.appId;
//		tempRequestBody.page = requestBody.page;
//		tempRequestBody.sign = requestBody.sign;
//		tempRequestBody.body = requestBody.body;
//
//
//		var params = "";
//		$.each(tempRequestBody.body,function(item,value){
//			params += item +"=" + value + "|";
//		});
//		var body = {
//			"signParams" : params
//		}
//		tempRequestBody.body = body;
//
//		var sign = "";
//		console.log("请求sign参数 = " + JSON.stringify(tempRequestBody));
//		var config = {
//			async: false,
//			token: tool.getStorage("token"),
//			params: JSON.stringify(tempRequestBody),
//			url: "/basis/sign",
//			onSuccess: function (data) {
//				console.log("获取新sign = " + JSON.stringify(data));
//				if (data.code == cast.returnState().SUCCESS) {
//					sign = data.data.sign;
//				}else if (data.code == cast.returnState().TOKENEXPIRATION) {
//					//当请求签名时 token过期的情况
//					httptool.getRefreshToken();
//					sign = httptool.getSign(requestBody);
//				}else{
//					return false;
//				}
//			}
//		}
//		tool.baseAjax(config);
//		return sign;
//	}
//
//	/**
//	 * 刷新token
//	 */
//	httptool.getRefreshToken = function() {
//		var requestBody = httptool.requestBody();
//		var refreshToken = tool.getStorage("refreshToken");
//		tool.removeStorage("token");
//		tool.setStorage("token", refreshToken);
////		requestBody.body = {refreshToken:refreshToken};
//		var config = {
//			async: false,
////			token: refreshToken,
////			params: JSON.stringify(requestBody),
//			url: "/basis/refreshToken",
//			onSuccess: function (data) {
//				if (data.code == cast.returnState().SUCCESS) {
//					var newToken = data.data.token;
//					tool.removeStorage("token");
//					tool.setStorage("token", newToken);
//					console.log("token================" + newToken);
//				}else if(data.code == cast.returnState().TOKENEXPIRATION){
//					window.location.href = cast.apiUrl + "/login";
//					return false;
//				}else{
//					tool.setStorage("pcError",JSON.stringify(data))
//					console.log("错误跳转到错误页面500");
//				}
//			}
//		}
//		console.log("请求刷新token="+ config.url + "||求情数据："+JSON.stringify(requestBody));
//		tool.baseAjax(config);
//	}
//
//
//	/**
//	 * 基础的jquery ajax方法
//	 * @param config
//   */
//	tool.baseAjax = function (config) {
//		
//		var flgasync = config.async;
//		if(flgasync == false){
//			flgasync = false;
//		}else{
//			flgasync = true;
//		}
//		jQuery.support.cors = true;
//		$.ajax({
//			headers    : {
//				"token" : tool.getStorage("token")
//			},
//			url        : cast.apiUrl + config.url,
//			method     : config.method || 'post',
//			async      : flgasync,
//			contentType: "application/json",
//			timeout    : 300000000,  
//			crossDomain: true, 
//			dataType   : 'json',
//			data       : config.params || null,
//			success    : config.onSuccess,
//			error      : function(XMLHttpRequest, textStatus, errorThrown){
//				
//			console.dir(XMLHttpRequest)
//////				判断404和500
////				if(textStatus=="error"||textStatus=="parsererror"){
//////					tool.setStorage("pcError",JSON.stringify(data))
//////					var reqUrl=window.location.href;
////					window.location.href="../../500.html";
////				}else if(textStatus=="timeout"){
//////					tool.setStorage("pcError",JSON.stringify(data))
//////					var reqUrl=window.location.href;
////					msg="请求超时，请检查网络！"
////					window.location.href="../../500.html";
////				}else{
//////					tool.setStorage("pcError",JSON.stringify(data))
////					console.log(textStatus);
////					return false;
////				}
//				
//			}
//		})
//	}
//
//	/**
//	 * 统一的求情服务器ajax方法
//	 * @param config
//	 * var config = {
//	 * 				(必填)url: '/login'  请求地址
//	 * 				params: {name：'1234567890',pwd:'12321313'}  业务请求参数
//	 * 				page: page 分页对象
//	 * 				sign ： false|true  是否需要签名
//	 * 				reqSuccess: function 业务成功处理方法
//	 * 				reqError：function 业务失败处理方法
//	 * 			}
//	 * 专注于业务的成功或则失败处理
//	 * 对于其他错误 比如 未登陆  权限  系统异常等无需处理
//	 * @returns {boolean}
//   */
//	tool.apiReq = function(config){
//		console.log("tool.apiReq ==> config 初始值 ："+ config);
//		var _body = config.params;
//		if(typeof _body != "object" && !tool.isEmpty(_body)){
//			config.params = JSON.parse(_body);
//		}
//		
//		var _page = config.page;
//		if(typeof _page != "object" && !tool.isEmpty(_page)){
//			config.page = JSON.parse(_page);
//		}
//		
//		var requestBody= httptool.requestBody(config.params,config.page);
//		if(config.sign){
//			var signtemp = httptool.getSign(requestBody);
//			if(tool.isEmpty(signtemp)){    
//				return false;
//			}
//			requestBody.sign = signtemp;
////			requestBody.body = config.body;
//			console.log("getSign = " + JSON.stringify(requestBody));
//		}
//		
//		var sendConfig = config;
//		console.log("url="+ sendConfig.url + "||请求数据："+JSON.stringify(requestBody));
//		sendConfig.params = JSON.stringify(requestBody);
////		sendConfig.token = tool.getStorage("token");
//		sendConfig.onSuccess = function (data) {
//			console.log("url="+ sendConfig.url + "||返回数据："+JSON.stringify(data));
//			var code = data.code;
//			var state = cast.returnState();
//			if(state.SUCCESS == code){
//				if(config.reqSuccess){
//					config.reqSuccess(data);
//				}else{
//					tool.setStorage("pcError1",JSON.stringify(data))
//				}
//			}else if(state.FAIL == code) {
//				if(config.reqError){
//					config.reqError(data);
//				}else{
//					tool.setStorage("pcError2",JSON.stringify(data))
//				}
//			}else if(state.TOKENEXPIRATION == code){
//				//当签名时不过期 具体业务时过期  在服务器端 设置 如果是需要签名的方法出现过期自动延后30秒
//				//必须保证 签名通过的方法 不会出现token过期的情况 
//				//所以当出现需要签名的方法时 不在请求获取token
//				if(tool.isEmpty(config.sign)){
//					httptool.getRefreshToken();
//					tool.apiReq(config);
//				}else{
//					tool.setStorage("pcError3",JSON.stringify(data))
//					return false;
//				}
//			}else if(state.REPEATOPTS == code){
//				tool.setStorage("pcError4",JSON.stringify(data))
//				console.log("重复提交");
//				return false;
//			}else if(state.ERROR==code){
//				tool.setStorage("pcError5",JSON.stringify(data))
////				window.location.href="../../500.html";
//			}else if(code==state.ILLEGAL){
//				tool.setStorage("pcError6",JSON.stringify(data))
//				window.location.href="../../401.html";
//			}else{
//				tool.setStorage("pcError7",JSON.stringify(data))
//				window.location.href="../../500.html";
//			}
//		};
//		tool.baseAjax(sendConfig);
//	};


	/**
	 * 初始化axios 实例
	 */
	var baseAxios = axios.create({
	    baseURL:cast.apiUrl,
	    timeout:30000,
	    headers:{"token" : tool.getStorage("token"),"Content-Type":"application/json"}
	});
	
	/**
	 * 添加请求前置拦截  拦截token有效性
	 * 如果token剩余时间为2分钟则刷新token
	 */
	baseAxios.interceptors.request.use(function(config){
	    
//	    console.log("请求前置拦截" + JSON.stringify(config));
	    
	    var refreshTokenUrl = cast.apiUrl + "/basis/refreshToken";
	    var loginInUrl = cast.apiUrl + "/userinfo/login";
	    var loginOutUrl = cast.apiUrl + "/userinfo/out";
	    
	    //请求路径是刷token  和登录  则不拦截
	    if(config.url == refreshTokenUrl || config.url == loginInUrl || config.url == loginOutUrl){
	    	return config;
	    }
	    
	    var refreshTokenTime = tool.getStorage("refreshTokenTime");
	    var nowTime = new Date().getTime();
	    console.log("当前时间=" + nowTime + "||token刷新时间：" + refreshTokenTime + "||时间差值=" + (nowTime - refreshTokenTime) + "||设定差值="+  cast.tokentime)
	    if(nowTime - refreshTokenTime < cast.tokentime){
	    	return config;
	    }
	    
	    var requestBody= httptool.requestBody();
		var refreshToken = tool.getStorage("refreshToken");
		tool.setStorage("token", refreshToken);
	    baseAxios.post("/basis/refreshToken",requestBody).then(function(response){
//		    console.log(response);
		    var data = response.data;
		    if (data.code == cast.returnState().SUCCESS) {
				var newToken = data.data.token;
//				tool.removeStorage("token");
				tool.setStorage("token", newToken);
				tool.setStorage("refreshTokenTime", new Date().getTime());
//				console.log("refreshTokenTime= " + refreshTokenTime);
//				console.log("token================" + newToken);
			}
		});
	    return config;
	},function(error){
	    //当出现请求错误是做一些事
		httptool.resError(error);
	    return Promise.reject(error);
	});
	
	/**
	 * 请求后置拦截
	 * 处理后台返回的数据 并做统一处理
	 * 对http 状态处理
	 * 对统一异常处理
	 */
	baseAxios.interceptors.response.use(function(response){
	    var data = response.data;
	    console.log("后置拦截")
	    console.dir(data);
	    tool.setStorage("globalResponseData", data);
		//服务器返回的http状态码
		var httpState = response.status;
		//业务字典
		var state = cast.returnState();
		//服务器返回的业务码
		var code = data.code;
		if(state.SUCCESS == code || state.FAIL==code){
			return response;
		}else if(state.REPEATOPTS == code){
			console.log("请勿重复提交");
		}else if(state.TOKENEXPIRATION == code || state.INVALIDTOKEN == code){
			window.location.href= httptool.webRootUrl() + "/login.html";
		}else if(state.INVALIDSIGNATURE == code || state.TOKENLOSE == code || state.ILLEGAL == code ){
			window.location.href= httptool.webRootUrl() + "/401.html";
		}else{
//			window.location.href= httptool.webRootUrl() + "/500.html";
		}
	    return response;
	},function(error){
	    //对返回的错误进行一些处理
	    httptool.resError(error);
	    return Promise.reject(error);   
	});
	
	/**
	 * 统一的求情服务器ajax方法
	 * @param config
	 * var config = {
	 * 				(必填)url: '/login'  请求地址
	 * 				params: {name：'1234567890',pwd:'12321313'}  业务请求参数
	 * 				page: page 分页对象
	 * 				sign ： false|true  是否需要签名
	 * 				reqSuccess: function 业务成功处理方法
	 * 				reqError：function 业务失败处理方法
	 * 			}
	 * 专注于业务的成功或则失败处理
	 * 对于其他错误 比如 未登陆  权限  系统异常等无需处理
	 * @returns {boolean}
     */
	tool.apiReq = function(userConfig){
		
		
//		console.log("tool.apiReq ==> userConfig 初始值 ："+ userConfig);
		var _body = userConfig.params;
		if(typeof _body != "object" && !tool.isEmpty(_body)){
			userConfig.params = JSON.parse(_body);
		}
		
		var _page = userConfig.page;
		if(typeof _page != "object" && !tool.isEmpty(_page)){
			userConfig.page = JSON.parse(_page);
		}
		
		var requestBody= httptool.requestBody(userConfig.params,userConfig.page);
		
		if(userConfig.sign){
			
			var tempRequestBody = new Object();
			tempRequestBody.rnd = requestBody.rnd;
			tempRequestBody.timestamp = requestBody.timestamp;
			tempRequestBody.appId = requestBody.appId;
			tempRequestBody.page = requestBody.page;
			tempRequestBody.sign = requestBody.sign;
			tempRequestBody.body = requestBody.body;
	
			var params = "";
			$.each(tempRequestBody.body,function(item,value){
				if(value != undefined){
					params += item +"=" + value + "|";	
				}
			});
			var body = {
				"signParams" : params
			}
			tempRequestBody.body = body;
	
			var sign = "";
			baseAxios.post("/basis/sign",tempRequestBody).then(function(response){
//			    console.log(response);
			    var data = response.data;
				if (data.code == cast.returnState().SUCCESS) {
					requestBody.sign = data.data.sign;
//					console.log("获取新sign = " + requestBody.sign);
					
					baseAxios.post(userConfig.url,requestBody).then(function(response){
					    httptool.resFunction(response,userConfig);
					}).catch(function (error) {
					    httptool.resError(error);
					    return Promise.reject(error);   
				  	});
				}
			}).catch(function (error) {
			    httptool.resError(error);
			    return Promise.reject(error);   
		  	});
		}else{
			baseAxios.post(userConfig.url,requestBody).then(function(response){
			    httptool.resFunction(response,userConfig);
			}).catch(function (error) {
			    httptool.resError(error);
			    return Promise.reject(error);   
		  	});
		}
	}
	
	/**
	 * 同意异常处理
	 * @param {Object} response
	 * @param {Object} userConfig
	 */
	httptool.resError = function(error){
		if (error.response) {
	    	  console.log("==================333333====================");
		      // 请求已发出，但服务器响应的状态码不在 2xx 范围内
		      console.log(error.response.data);
		      console.log(error.response.status);
		      console.log(error.response.headers);
	    } else {
	    	console.log("==================4444444====================");
	      // Something happened in setting up the request that triggered an Error
	      console.log('Error', error.message);
	    }
	    console.log("==================5555555====================");
	    console.dir(error);
	    console.dir(error.config);
	    
	    var globalResponseData = {
	    	msg:error.message,
	    	code:"E99999"
	    };
	    tool.setStorage("globalResponseData", globalResponseData);
//	    window.location.href = httptool.webRootUrl() + "/500.html";
	    
	}
	
	/**
	 * 后台返回编码与前端自定义方法处理
	 * @param {Object} response
	 * @param {Object} userConfig
	 */
	httptool.resFunction = function(response,userConfig){
//		console.log(response);
	    var data = response.data;
		if (data.code == cast.returnState().SUCCESS && userConfig.reqSuccess) {
			userConfig.reqSuccess(data);
		}else if (data.code == cast.returnState().FAIL && userConfig.reqError) {
			userConfig.reqError(data);
		}
	}
	
	httptool.webRootUrl = function(){
		var pathName = window.location.pathname.substring(1);
	    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
	    if (webName == "") {
	    	return window.location.protocol + '//' + window.location.host;
	    }else {
	    	return window.location.protocol + '//' + window.location.host + '/' + webName;
	    }
	}
});