define(function () {
    var Utils = {

        //解析URL中的query
        getUrlParams: function (url) {
            var args = {};
            var query = "";
            var questionIndex;
            var pairs;
            var pos;
            var key;
            var value;
            if (url) {
                questionIndex = url.indexOf("?");
                if (questionIndex >= 0) {
                    query = url.substring(questionIndex + 1);
                }
            } else {
                query = location.search.substring(1);
            }
            pairs = query.split("&");
            for (var i = 0; i < pairs.length; i++) {
                pos = pairs[i].indexOf('=');
                if (pos == -1) continue;
                key = pairs[i].substring(0, pos);
                value = pairs[i].substring(pos + 1);
                value = decodeURIComponent(value);
                args[key] = value;
            }
            return args;
        },

        //将时间戳转化为指定的时间格式字符串，详情可参考主站的date-util.js
        dateFormat: function (timestamp, format) {
            var date = new Date(+timestamp);
            var o = {
                "M+": date.getMonth() + 1, //月份
                "d+": date.getDate(), //日
                "h+": date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, //小时
                "H+": date.getHours(), //小时
                "m+": date.getMinutes(), //分
                "s+": date.getSeconds(), //秒
                "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                "S": date.getMilliseconds() //毫秒
            };
            var week = {
                "0": "/u65e5",
                "1": "/u4e00",
                "2": "/u4e8c",
                "3": "/u4e09",
                "4": "/u56db",
                "5": "/u4e94",
                "6": "/u516d"
            };
            format = format || "yyyy-MM-dd HH:mm:ss";
            if (/(y+)/.test(format)) {
                format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            }
            if (/(E+)/.test(format)) {
                format = format.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[date.getDay() + ""]);
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(format)) {
                    format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                }
            }
            return format;
        },

        //将格式化的时间转化为时间戳（目前仅支持"yyyy-MM-dd"和"yyyy-MM-dd HH:mm:ss"）
        toTimeStamp: function (timeString) {
            var timeStamp = +new Date(timeString); //直接使用可能存在兼容问题
            var date = new Date();
            var matchResult;
            var year = 1970;
            var month = 1;
            var day = 1;
            var hours = 0;
            var minutes = 0;
            var seconds = 0;
            var i;
            if (typeof timeStamp == "number" && !isNaN(timeStamp)) {
                return timeStamp;
            }
            matchResult = timeString.match(/^(\d{4})-(\d{1,2})-(\d{1,2})( (\d{1,2}):(\d{1,2}):(\d{1,2}))?$/);
            for (i = 0; i < matchResult.length; i++) {
                switch (i) {
                    case 1:
                        year = matchResult[i];
                        break;
                    case 2:
                        month = matchResult[i];
                        break;
                    case 3:
                        day = matchResult[i];
                        break;
                    case 5:
                        hours = matchResult[i];
                        break;
                    case 6:
                        minutes = matchResult[i];
                        break;
                    case 7:
                        seconds = matchResult[i];
                        break;
                }
            }
            date.setFullYear(year, month - 1, day);
            date.setHours(hours);
            date.setMinutes(minutes);
            date.setSeconds(seconds);
            date.setMilliseconds(0);
            return date.getTime();
        },

        //对称加密（依赖aes.js）
        aesEncrypt: function (text, key) {
            if (!window.CryptoJS) {
                return "";
            }
            key = CryptoJS.enc.Utf8.parse('helloworld123456');
            var iv = CryptoJS.enc.Utf8.parse("0000000000000000");
            var encrypted = CryptoJS.AES.encrypt(text, key, {iv: iv});
            return encrypted.ciphertext.toString(CryptoJS.enc.Base64);
        },

        //非对称加密
        rsaEncrypt: function (publicKey, text) {
            if (typeof window.JSEncrypt != "function") {
                return "";
            }
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(publicKey);
            return encrypt.encrypt(text);
        },

        //对text-security的输入框处理
        initTextSecurity: function ($input) {
            if (!$input.css("text-security") && !$input.css("-webkit-text-security")) {
                $input.prop("type", "password");
            }
        }

    };
    return Utils;
});