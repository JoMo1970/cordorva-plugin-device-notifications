var exec = require('cordova/exec');

//this cordova function interacts with the native app
exports.sendNotification= function(arg0, success, error) {
    exec(success, error, "MobileDeviceNotifications", "InvokeDeviceNotification", [arg0]);
};

/*exports.coolMethod= function(arg0, success, error) {
    exec(success, error, "MobileSSLPinningUtility", "coolMethod", [arg0]);
};*/
