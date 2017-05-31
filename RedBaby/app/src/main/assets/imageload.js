if(typeof _snapmLogEnable != "boolean") _snapmLogEnable=true;
var _snapm_log=function(msg) {if (_snapmLogEnable && typeof snJsBridge != "undefined" && typeof(snJsBridge.logDebug) == "function")snJsBridge.logDebug(msg);};
var _sn_firstscreenTime;
function _snapmFirstClass(){
    /**
     * url 有效性检测
     * @param url
     * @returns {boolean}
     */
    function checkURLValid(url) {
        return !!(typeof url == 'string' && url.constructor == String &&
            (url.toLowerCase().indexOf("http://") == 0 || url.toLowerCase().indexOf("https://") == 0));
    }
    if (!checkURLValid(window.location.href)) {
        _snapm_log('invalid url, window.location.href: ' + window.location.href);
        return;
    }
    // 给图片标签添加载入（load）监听事件
    function _sn_images_load() {
        _snapm_log('images_load');
        var imgs = document.getElementsByTagName("img");
        var fsItems = [];
        _sn_firstscreenTime = +new Date;
        var loadEvent = function() {
            if (this.removeEventListener) {
                this.removeEventListener("load", loadEvent, false);
            }
            fsItems.push({
                img : this,
                time : +new Date
            });
        }
        var addLoadBeforeTime = +new Date;
        for (var i = 0; i < imgs.length; i++) {
            (function() {
                var img = imgs[i];
                if (typeof img.src != "undefined" && img.src.length != 0) {
                    _snapm_log('img.src: ' + img.src);
                    if (img.addEventListener) {
                        !img.complete && img.addEventListener("load", loadEvent, false);
                    }
                }
            })();
        }
        _snapm_log('images addEventListener executeJs spend time = '+ (+new Date - addLoadBeforeTime));
        // 计算首屏时间时使用
        function getOffsetTop(elem) {
            var top = 0;
            top = window.pageYOffset ? window.pageYOffset
                : document.documentElement.scrollTop;
            try {
                top += elem.getBoundingClientRect().top;
            } catch (e) {

            } finally {
                return top;
            }
        }
        // 比较首屏的各图片加载时间，得到首屏时间
        function calculateFirstScreenTime() {
            var sh = document.documentElement.clientHeight;
            for (var i = 0; i < fsItems.length; i++) {
                var item = fsItems[i], img = item['img'], time = item['time'];

                // 计算首屏时间
                var top = getOffsetTop(img);
                if (top > 0 && top < sh) {
                    _sn_firstscreenTime = time > _sn_firstscreenTime ? time : _sn_firstscreenTime;
                }
            }
        }
        window.addEventListener('load', function() {
            _snapm_log("fullLoaded: " + (+new Date));
            calculateFirstScreenTime();
        });
    }

    window.addEventListener('DOMContentLoaded', function() {
        _sn_images_load();
    });
}
setTimeout(_snapmFirstClass,0);