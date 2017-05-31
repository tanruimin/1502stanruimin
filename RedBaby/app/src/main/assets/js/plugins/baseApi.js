snapp.define("com.snapp.base",
    function (require, exports, module) {

        exec = require('snapp/exec');
        utils = require('snapp/utils');
        versionCode = require('snapp/version');

        module.exports.getClientInfo = function (successCallback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(successCallback, null, "BaseApi", "getClientInfo", []);
            } else {
                var clientInfo = window.client.getClientInfo();
                successCallback(clientInfo);
            }
        }
        module.exports.getIdentifier = function (successCallback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(successCallback, null, "BaseApi", "getIdentifier", []);
            } else {
                var identifier = window.client.getIdentifier();
                successCallback(identifier);
            }
        }
        module.exports.showTip = function (message) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(null, null, "BaseApi", "showTip", [message]);
            } else {
                window.client.showTip(message);
            }
        }
        module.exports.enableLoading = function (message) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(null, null, "BaseApi", "enableLoading", [message]);
            } else {
                window.client.enableLoading(message);
            }
        }
        module.exports.copyToClipBoard = function (message) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(null, null, "BaseApi", "copyToClipBoard", [message]);
            } else {
                window.client.copyToClipBoard(message);
            }
        }
        module.exports.pageRouter = function (message) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(null, null, "BaseApi", "pageRouter", [message]);
            } else {
                window.client.pageRouter(message);
            }
        }
        module.exports.scanCode = function (successCallback, isForResult) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(successCallback, null, "BaseApi", "scanCode", [isForResult]);
            }
        }

        //add by dannyzhuang at 150715 begin
        module.exports.gotoCustom = function (shopcode, shopName) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(null, null, "BaseApi", "gotoCustom", [shopcode, shopName]);
            } else {
                window.client.gotoCustom(shopcode, shopName);
            }
        }
        //add by dannyzhuang at 150715 end

        module.exports.closeWapPage = function () {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(null, null, "BaseApi", "closeWapPage", []);
            } else {
                window.client.closeWapPage();
            }
        }

        module.exports.showRightButtons = function (buttons) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(null, null, "BaseApi", "showRightButtons", [buttons]);
            } else {
                window.client.showRightButtons(buttons);
            }
        }

        module.exports.setSATitle = function (spcode, ptype) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(null, null, "BaseApi", "setSATitle", [spcode, ptype]);
            } else {
                window.client.setSATitle(spcode, ptype);
            }
        }

        module.exports.shaking = function (callback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_32) {
                exec(null, null, "BaseApi", "stopShaking", []);
                exec(callback, null, "BaseApi", "shaking", []);
            }
        }
        module.exports.stopShaking = function () {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_32) {
                exec(null, null, "BaseApi", "stopShaking", []);
            }
        }
        module.exports.optPictures = function (picServerUrl, callback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(callback, null, "BaseApi", "optPictures", [picServerUrl]);
            } else {
                window.client.openImageChooser(picServerUrl);
            }
        }
    });
