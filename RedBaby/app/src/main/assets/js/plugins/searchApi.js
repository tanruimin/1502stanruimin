snapp.define("com.snapp.search",
    function (require, exports, module) {

        exec = require('snapp/exec');
        utils = require('snapp/utils');
        versionCode = require('snapp/version');

        module.exports.goToSearchResultWithKeyword = function (Keyword) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > versionCode.version_33) {
                exec(null, null, "Search", "goToSearchResultWithKeyword", [Keyword]);
            } else {
                window.client.goToSearchResultWithKeyword(Keyword);
            }
        }

        module.exports.getSearchFunData = function (successCallback) {
                     var version = utils.getVersion(window.navigator.userAgent);
                     if (version > versionCode.version_33) {
                         exec(successCallback, null, "Search", "getSearchFunData", []);
                     } else {
                          var searchFunInfo = window.client.getSearchFunData();
                          successCallback(searchFunInfo);
                     }
                }

    });
