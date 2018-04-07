define([
    "/widget/ui/dialog/dialog.js"
], function (Dialog) {
    var PhoneCode = function (options) {
        var defaultOptions = {
            $container: $(""),
            url: PageData.basePath + "/common/getInnerCode.do", //@ToDo
            text: "获取短信验证码",
            phone: "",
            key: "",
            type: 1,
            smsType: '',
            platformNo: '',
            success: function () { //发送成功的回调函数

            }
        };
        this.options = $.extend(defaultOptions, options);
        this.init();
    };

    PhoneCode.prototype = {

        constructor: PhoneCode,

        //初始化
        init: function () {
            var that = this;
            that.options.$btn = $('<a class="ui-btn-phone-code">' + that.options.text + '</a>');
            that.options.$container.append(that.options.$btn);
            that.options.$btn.data("type", that.options.type);
            that.bindEvent();
        },

        //事件绑定
        bindEvent: function () {
            var that = this;
            that.options.$btn.on("click", function () {
                var $btn = $(this);
                if ($btn.hasClass("ui-btn-disabled")) {
                    return false;
                }
                that.sendRequest();
            });
        },

        //发送请求
        sendRequest: function (type) {
            var that = this;
            var $btn = that.options.$btn;
            var originText = $btn.text();
            $btn.addClass("ui-btn-disabled");
            $btn.text("获取中...");
            $.ajax({
                url: that.options.url,
                type: "post",
                data: {
                    mobile: that.options.phone,
                    type: type || $btn.data("type"),
                    key: that.options.key,
                    smsType: that.options.smsType,
                    platformNo: that.options.platformNo
                },
                dataType: "json"
            }).done(function (ret) {
                if (ret && ret.error_no == 0) {
                    that.options.success(ret);
                    that.countDown(60);
                } else {
                    Dialog.tip(ret.error_message || "获取验证码失败，请稍后重试");
                    $btn.removeClass("ui-btn-disabled");
                    $btn.text(originText);
                }
            }).fail(function () {
                Dialog.tip("获取验证码失败，请稍后重试");
                $btn.removeClass("ui-btn-disabled");
                $btn.text(originText);
            });
        },

        //倒计时
        countDown: function (countdown) {
            var that = this;
            var $btn = that.options.$btn;
            if (countdown <= 0) {
                $btn.text("未收到,语音获取");
                //$btn.text("重新获取");
                $btn.removeClass("ui-btn-disabled");
                $btn.data("type", 2);
                // $btn.data("type", 1);
                return false;
            }
            $btn.text(countdown + "秒后重新获取");
            setTimeout(function () {
                that.countDown(countdown - 1);
            }, 1000);
        }
    };

    return PhoneCode;
});