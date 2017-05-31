snapp.define("com.snapp.selectCity",
    function (require, exports, module) {

        exec = require('snapp/exec');
        utils = require('snapp/utils');
        versionCode = require('snapp/version');

        //����ѡ�����ҳ��
        module.exports.selectCity = function (type, isStore, callback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_32) {
                exec(callback, null, "Cities", "selectCity", [type, isStore]);
            }
        }

        //��ȡ��ǰ����
        module.exports.getCityInfo = function (callback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_32) {
                exec(callback, null, "Cities", "getCityInfo", []);
            } else {
                window.client.getCityInfo();
            }
        }

        //���ĵ�ǰ����
        module.exports.changeCity = function (cityCode, cityName) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_32) {
                exec(null, null, "Cities", "changeCity", [cityCode, cityName]);
            } else {
                window.client.changeCity(cityCode, cityName);
            }
        }

    });
