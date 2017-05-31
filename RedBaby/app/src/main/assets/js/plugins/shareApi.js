snapp.define("com.snapp.share",
    function (require, exports, module) {

        exec = require('snapp/exec');
        utils = require('snapp/utils');

        module.exports.saveShareInfo = function (shareInfoStr) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > 87) {
                exec(null, null, "Share", "saveShareInfo", [shareInfoStr]);
            } else {
                window.client.saveShareInfo(shareInfoStr);
            }
        }
        module.exports.callNativeShare = function (title, content, targetUrl, iconUrl, shareWays, callback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > 87) {
                exec(callback, null, "Share", "callNativeShare", [title, content, targetUrl, iconUrl, shareWays]);
            } else {
                window.client.callNativeShare(title, content, targetUrl, iconUrl, shareWays);
            }
        }
    });
