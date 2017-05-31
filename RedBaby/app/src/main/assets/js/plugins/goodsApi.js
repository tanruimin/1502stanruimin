snapp.define("com.snapp.goods",
    function (require, exports, module) {

        exec = require('snapp/exec');
        utils = require('snapp/utils');
        goodsversion = require('snapp/version');

        module.exports.goToProductDetail = function (productCode, shopCode, promotionType) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > goodsversion.version_33) {
                exec(null, null, "Goods", "goToProductDetail", [productCode, shopCode, promotionType]);
            } else {
                window.client.goToProductDetail(productCode, shopCode, promotionType);
            }
        }
        module.exports.goBackFreeNessPay = function (status) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > goodsversion.version_33) {
                exec(null, null, "Goods", "goBackFreeNessPay", [status]);
            } else {
                window.client.goBackFreeNessPay(status);
            }
        }
        
        module.exports.goToProductDetailTreaty = function ( productCode,shopCode,
             promotionType,buyTypeCode,treatyTypeCode) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > goodsversion.version_33) {
                exec(null, null, "Goods", "goToProductDetailTreaty", [productCode, shopCode,promotionType, buyTypeCode, treatyTypeCode]);
            } 
        }

        module.exports.goToProductDetailSrc = function (productCode, shopCode, promotionType,wapSrc) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > goodsversion.version_33) {
                exec(null, null, "Goods", "goToProductDetailSrc", [productCode, shopCode, promotionType ,wapSrc]);
            } else {
                window.client.goToProductDetailSrc(productCode, shopCode, promotionType,wapSrc);
            }
        }

         module.exports.goToProductDetailTreatySrc = function ( productCode,shopCode,
                     promotionType,buyTypeCode,treatyTypeCode,wapSrc) {
            var version = utils.getVersion(window.navigator.userAgent);
                if (version > goodsversion.version_33) {
                    exec(null, null, "Goods", "goToProductDetailTreatySrc", [productCode, shopCode,promotionType, buyTypeCode, treatyTypeCode,wapSrc]);
                }
         }
    });
