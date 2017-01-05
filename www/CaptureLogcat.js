var exec = require('cordova/exec');

exports.capture = function(arg0, success, error) {
    exec(success, error, "CaptureLogcat", "capture", [arg0]);
};
