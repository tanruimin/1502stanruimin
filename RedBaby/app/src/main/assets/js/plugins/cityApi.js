snapp.define("com.snapp.selectCity",
    function (require, exports, module) {

        exec = require('snapp/exec');
        utils = require('snapp/utils');
        versionCode = require('snapp/version');

        //弹出选择城市页面
        module.exports.selectCity = function (type, isStore, callback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_32) {
                exec(callback, null, "Cities", "selectCity", [type, isStore]);
            }
        }

        //获取当前城市
        module.exports.getCityInfo = function (callback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_32) {
                exec(callback, null, "Cities", "getCityInfo", []);
            } else {
                window.client.getCityInfo();
            }
        }

        //更改当前城市
        module.exports.changeCity = function (cityCode, cityName) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_32) {
                exec(null, null, "Cities", "changeCity", [cityCode, cityName]);
            } else {
                window.client.changeCity(cityCode, cityName);
            }
        }

    });
