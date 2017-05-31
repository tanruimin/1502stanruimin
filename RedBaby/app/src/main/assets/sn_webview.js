if(typeof _snapmLogEnable != "boolean") _snapmLogEnable=true;
var _snapm_log = function (msg) {if (_snapmLogEnable && typeof snJsBridge != "undefined" && typeof(snJsBridge.logDebug) == "function")snJsBridge.logDebug(msg);};var quitWebView = function () {};
setTimeout(function () {
    /* _snWebviewKey js注入时生成 */
    if (typeof _snWebviewKey == "undefined") {
        _snapm_log("error _snWebviewKey is undefined");
        return;
    }
    var needResData=true, hasCollectCount=0;

    /**
     * 采集资源timing 数据
     * @param timeOut true 开启采集延迟控制
     * @returns {boolean}
     */
    function resTimingData(timeOut) {
        _snapm_log("start to collect resTiming data...");
        var maxTime = new Date().getTime() + 1000;// 最大执行时间，大于该时间时 不继续循环采集资源
        try {
            var hasFunction = "getEntriesByType" in window.performance;
            if (!hasFunction) {
                _snapm_log("Not support getEntriesByType function");
                return false;
            }
            var pageUrl = window.location.href, navigationStart = window.performance.timing.navigationStart,
                resourceList = window.performance.getEntriesByType("resource");
            if(hasCollectCount < resourceList.length){
                var resData = [];
                for (var i = hasCollectCount; i < resourceList.length; i++) {
                    var e = resourceList[i];
                    var data = [navigationStart, e.name.replace(/\|/g, "%7c"),e.entryType,
                        e.initiatorType, parseInt(e.duration), parseInt(e.startTime),
                        parseInt(e.redirectStart), parseInt(e.redirectEnd), parseInt(e.fetchStart),
                        parseInt(e.domainLookupStart), parseInt(e.domainLookupEnd),parseInt(e.connectStart),
                        parseInt(e.connectEnd), parseInt(e.secureConnectionStart),parseInt(e.requestStart),
                        parseInt(e.responseStart), parseInt(e.responseEnd)];
                    resData.push(data.join("|"));

                    if (typeof timeOut != "undefined" && timeOut && (new Date().getTime() > maxTime)) {
                        _snapm_log("for operation execute too long, break directly");
                        break;
                    }
                }
                if (typeof snJsBridge != "undefined" && typeof(snJsBridge.onPushResData) == "function")
                    snJsBridge.onPushResData(_snWebviewKey, pageUrl, resData.join("$^$"));
                hasCollectCount=resourceList.length;
            }
            return true;
        } catch (e) {
            _snapm_log("collect resTiming occur an error:" + e.message);
        }
    }

    /**
     * 收集页面定时数据，Navigation Timing
     * @returns {boolean} true 采集成功
     */
    function pageTimingData() {
        try {
            _snapm_log("start to collect pageTiming ...");
            var t = window.performance.timing;

            var _firstTime = t.domContentLoadedEventEnd;
            /* _sn_firstscreenTime 通过imageload.js 文件生成 需判空 */
            if (typeof _sn_firstscreenTime != "undefined" && !isNaN(_sn_firstscreenTime)) {
                _firstTime = _sn_firstscreenTime;
            }

            if (t.navigationStart == 0 || t.domContentLoadedEventStart == 0 || t.domContentLoadedEventEnd == 0
                || t.loadEventStart == 0 || t.loadEventEnd == 0) {
                _snapm_log("some data is 0" + ", navigationStart: " + t.navigationStart + ", domContentLoadedEventStart: " + t.domContentLoadedEventStart
                    + ", domContentLoadedEventEnd: " + t.domContentLoadedEventEnd + ", loadEventStart: " + t.loadEventStart + ", loadEventEnd: " + t.loadEventEnd);
                return false;
            }

            var data = [t.navigationStart, t.unloadEventStart, t.unloadEventEnd, t.redirectStart,
                t.redirectEnd, t.fetchStart, t.domainLookupStart, t.domainLookupEnd,
                t.connectStart, t.connectEnd, t.secureConnectionStart, t.requestStart,
                t.responseStart, t.responseEnd, t.domLoading, t.domInteractive,
                t.domContentLoadedEventStart, t.domContentLoadedEventEnd, t.domComplete,
                t.loadEventStart, t.loadEventEnd, _firstTime];
            if (typeof snJsBridge != "undefined" && typeof(snJsBridge.onPushPageData) == "function")
                snJsBridge.onPushPageData(_snWebviewKey, window.location.href, data.join("|"));
            var fullLoadTime = t.loadEventEnd-t.navigationStart;

            /* 页面加载时间小于阈值，则不采集资源 */
            if(typeof _sn_webviewLatency !="undefined" && !isNaN(_sn_webviewLatency) && fullLoadTime<=_sn_webviewLatency){
                needResData=false;
                _snapm_log("not collect resource data..." + ", fullLoadTime: " +fullLoadTime + ", _sn_webviewLatency: " +_sn_webviewLatency);
            }
            return true;
        } catch (e) {
            _snapm_log("collect pageTiming occur an error: " + e.message);
        }
        return false;
    }

    /**
     * 收集/监视ajax的请求数据
     */
    function monitorAjax() {
        var oldOpen = XMLHttpRequest.prototype.open;
        var oldSend = XMLHttpRequest.prototype.send;
        XMLHttpRequest.prototype.open = function (method, uri, async, user, pass) {
            this._method = method, this._url = uri;
            oldOpen.call(this, method, uri, async, user, pass)
        };
        XMLHttpRequest.prototype.send = function (vData) {
            var object = this, method = this._method, vURL = this._url;

            function stateChange() {
                if (4 == object.readyState) {
                    var endTime = +new Date, sendbytes = 0;
                    try {
                        if (typeof vData === "string") {
                            sendbytes = vData.length;
                        }
                    } catch (e) {
                        _snapm_log("get sendbytes occur an error," + e.toString());
                    }
                    var result = vURL.replace(/\|/g, "%7c")+"|"+method+"|"+sendbytes+"|"+startTime+""+"|"+endTime+""+"|"+object.status+"|"+object.responseText.length;
                    if (typeof snJsBridge != "undefined" && typeof(snJsBridge.onPushAjaxData) == "function")
                        snJsBridge.onPushAjaxData(_snWebviewKey, window.location.href, result);
                }
            }

            var startTime = +new Date;
            this.addEventListener("readystatechange", stateChange, false);
            oldSend.call(this, vData)
        };
    }

    /**
     * 收集js执行错误时的异常信息
     */
    function monitorJsError() {
        _snapm_log("monitorJsError");
        function errorCatch(errorEvent) {
            var filename = "", lineno = -1, colno = -1, errorMsg = "", allStackMsg = "";
            if (typeof (errorEvent.filename) != "undefined") {
                filename = errorEvent.filename;
            }
            if (typeof (errorEvent.lineno) != "undefined") {
                lineno = errorEvent.lineno;
            }
            if (typeof (errorEvent.colno) != "undefined") {
                colno = errorEvent.colno;
            }
            if (typeof (errorEvent.message) != "undefined") {
                errorMsg = errorEvent.message;
            }
            if (typeof (errorEvent.error) != "undefined" && typeof (errorEvent.error.stack) != "undefined") {
                allStackMsg = errorEvent.error.stack;
            }
            var href = document.location.href;
            if (typeof snJsBridge != "undefined" && typeof(snJsBridge.onCollectJsError) == "function")
                snJsBridge.onCollectJsError(_snWebviewKey, filename, errorMsg, lineno, colno, href, allStackMsg);
        }
        window.addEventListener("error", errorCatch, false);
    }

    /**
     * url 有效性检测
     * @param url
     * @returns {boolean}
     */
    function checkURLValid(url) {
        return !!(typeof url == 'string' && url.constructor == String &&
            (url.toLowerCase().indexOf("http://") == 0 || url.toLowerCase().indexOf("https://") == 0));
    }

    /*检验url有效性*/
    if (!checkURLValid(window.location.href)) {
        _snapm_log('invalid url, window.location.href: ' + window.location.href);
        return;
    }
    /* 添加JsError监控 */
    setTimeout(monitorJsError,0);
    /*检验是否支持performance.timing*/
    if (!(typeof performance != "undefined" && typeof performance.timing !="undefined")) {
        _snapm_log("html5 feature not support performance timing");
        return;
    }
    var delay = 10;
    if(performance.timing.loadEventEnd==0){
        _snapm_log("timing.loadEventEnd=0, delay 500ms");
        delay = 500;
    }
    setTimeout(function(){
        /**
         * 先采集 PageTiming，
         * 若没有采集到该数据,则其他数据也不采集
         */
        if (pageTimingData()) {
            /* 函数预执行*/
            monitorAjax();
            if(needResData){
                /* 资源采集定时器 */
                resTimingData();
            }
            var hasSaved = false; /* 防止重复保存 */
            quitWebView = function () {
                if (!hasSaved) {
                    hasSaved = true;
                    _snapm_log('enter beforeunload collection start.');
                    /* 测试使用 */ var startTime = new Date().getTime();
                    if(needResData){
                        resTimingData(true);
                    }
                    if (typeof snJsBridge != "undefined" && typeof(snJsBridge.onSaveResult) == "function")
                        snJsBridge.onSaveResult(_snWebviewKey);
                    /* 测试使用 */ _snapm_log('beforeunload collection spend time: ' + (new Date().getTime() - startTime));
                }
            };
            /**
             * 添加页面跳转或退出监听
             * 此时采集剩余未采集的资源
             */
            if (window.addEventListener)
                window.addEventListener('beforeunload', quitWebView);
        }
    },delay);
},10);