snapp.define("com.snapp.location",
    function (require, exports, module) {

        exec = require('snapp/exec');
        utils = require('snapp/utils');
        locationversion = require('snapp/version');

        module.exports.getGeoPosition = function (successCallback) {
            var version = utils.getVersion(window.navigator.userAgent);
            if (version > locationversion.version_33) {
                exec(successCallback, null, "Location", "getGeoPosition", []);
            } else {
                window.client.getGeoPosition("location");
            }
        }
    });
