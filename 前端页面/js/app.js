window.app={
	/**
	 * 后端的服务地址
	 */
	serverUrl:'http://192.168.31.7:9001',
	
	/**
	 * 图片服务器的url地址
	 */
	imgServerUrl:'http://47.95.7.231:9002',
	
	/**
	 * 判断字符是否为空
	 * 
	 * @param {Object} str
	 */
	isNotNull:function(str){
		if(str!=null&&str!=""&&str!=undefined){
			return true;
		}
		return false;
	},
	//h5+的弹窗
	showToast : function(msg){
		plus.nativeUI.toast(msg,{verticalAlign:"center"})
	},
	/**
	 * 保存数据到全局变量
	 * @param {Object} user
	 */
	setUserGlobalInfo:function(user){
		var userInfoStr = JSON.stringify(user);
		plus.storage.setItem("userInfo",userInfoStr);
	},
	/**
	 * 获取全局变量
	 * @param {Object} user
	 */
	getUserGlobalInfo:function(){
		var userInfoStr = plus.storage.getItem("userInfo");
		return JSON.parse(userInfoStr);
	}
	
	
}
