snapp.define("com.snapp.pay",
    function (require, exports, module) {
        exec = require('snapp/exec');
        utils = require('snapp/utils');
        version = require('snapp/version');

        module.exports.openSDK = function (jsonStr) {
            var result = utils.getVersion(window.navigator.userAgent);
            if (result > version.version_33) {
                exec(null, null, "Pay", "openSDK", [jsonStr]);
            } else {
                window.YifubaoJSBridge.openSDK(jsonStr);
            }
        }

        module.exports.preparePayWithSDK = function (orderId, orderPrice, prepareType, date, productCode) {
            var result = utils.getVersion(window.navigator.userAgent);
            if (result > version.version_33) {
                exec(null, null, "Pay", "preparePayWithSDK", [orderId, orderPrice, prepareType, date, productCode]);
            } else {
                window.YifubaoJSBridge.preparePayWithSDK(orderId, orderPrice, prepareType, date, productCode);
            }
        }

        module.exports.coffeePayWithSDK = function (orderId, orderPrice, prouductCodes) {
            var result = utils.getVersion(window.navigator.userAgent);
            if (result > version.version_33) {
                exec(null, null, "Pay", "coffeePayWithSDK", [orderId, orderPrice, prouductCodes]);
            } else {
                window.YifubaoJSBridge.coffeePayWithSDK(orderId, orderPrice, prouductCodes);
            }
        }

        module.exports.seckillPayWithSDK = function (orderId, orderPrice, prouductCode) {
            var result = utils.getVersion(window.navigator.userAgent);
            if (result > version.version_33) {
                exec(null, null, "Pay", "seckillPayWithSDK", [orderId, orderPrice, prouductCode]);
            } else {
                window.YifubaoJSBridge.seckillPayWithSDK(orderId, orderPrice, prouductCode);
            }
        }
    });
