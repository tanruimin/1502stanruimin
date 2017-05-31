snapp.define("com.snapp.shopcart",
    function (require, exports, module) {

        exec = require('snapp/exec');
        utils = require('snapp/utils');
        version = require('snapp/version');

        module.exports.addCartRequest = function (callback, productId, quantity, supplierCode, special, promotionId) {
            var result = utils.getVersion(window.navigator.userAgent);
            if (result > version.version_33) {
                exec(callback, null, "Shopcart", "addCartRequest", [productId, quantity, supplierCode, special, promotionId]);
            } else {
                window.client.addCartRequest(productId + "", quantity, supplierCode, special, promotionId);
            }
        }

        module.exports.redirectShopCart = function (from) {
            var result = utils.getVersion(window.navigator.userAgent);
            if (result > version.version_33) {
                exec(null, null, "Shopcart", "redirectShopCart", [from]);
            } else {
                window.client.redirectShopCart();
            }
        }

        module.exports.enBackRefresh = function () {
            var result = utils.getVersion(window.navigator.userAgent);
            if (result > version.version_33) {
                exec(null, null, "Shopcart", "enBackRefresh", []);
            } else {
                window.client.enBackRefresh();
            }
        }

        module.exports.gotoCloudCart2V2 = function (cart2No, pType, cType) {
            var result = utils.getVersion(window.navigator.userAgent);
            if (result > version.version_33) {
                exec(null, null, "Shopcart", "gotoCloudCart2V2", [cart2No, pType, cType]);
            } else {
                window.client.gotoCloudCart2V2(cart2No, pType, cType);
            }
        }
    });
