(function JsAddJavascriptInterface_() {
	if (window.snJsBridge == undefined) {
		window.snJsBridge = {
			onPushPageData : function(arg0, arg1, arg2) {
				prompt("{tag:\"snJsBridge\",mothedName:\"onPushPageData\",webviewKey:\""+arg0+"\",pageUrl:\""+arg1+"\",timingData:\""+arg2+"\"}");
			},
			
			onPushResData : function(arg0, arg1, arg2) {
				prompt("{tag:\"snJsBridge\",mothedName:\"onPushResData\",webviewKey:"+arg0+",pageUrl:\""+arg1+"\",timingData:\""+arg2+"\"}");
			},
			onPushAjaxData : function(arg0, arg1, arg2) {
				prompt("{tag:\"snJsBridge\",mothedName:\"onPushAjaxData\",webviewKey:"+arg0+",pageUrl:\""+arg1+"\",timingData:\""+arg2+"\"}");
			},
			onSaveResult : function(arg0) {
				prompt("{tag:\"snJsBridge\",mothedName:\"onSaveResult\",webviewKey:\""+arg0+"\"}")
			},
			onCollectJsError : function(arg0, arg1, arg2, arg3, arg4, arg5,
					arg6) {
				prompt("{tag:\"snJsBridge\",mothedName:\"onCollectJsError\",webviewKey:\""+arg0+"\",filename:\""+arg1+"\",msg:\""+arg2+"\",lineno:\""+arg2+"\",colno:\""+arg2+"\",href:\""+arg2+"\",msg:\""+stacks+"\"}");
			},
			logDebug : function(arg0) {
				prompt("{tag:\"snJsBridge\",mothedName:\"logDebug\",debug:\""+arg0+"\"}");
			}

		}
	}
	;
})();