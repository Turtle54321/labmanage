define(function () {
    var $mod = $(".mod-system-mask");
    var $message = $mod.find("p");
    var isBindEvent = false;
    var loadingClass = "mod-system-mask-loading";
    var SystemMask = {

        //绑定事件
        bindEvent: function () {
            if (!isBindEvent) {
                isBindEvent = true;
                $mod.on("click", function (e) {
                    if ($mod.data("canClose")) {
                        SystemMask.hide();
                    }
                });
            }
        },

        //显示
        show: function (messgae, canClose) {
            messgae = messgae || "加载中...";
            $message.html(messgae);
            if (canClose) {
                SystemMask.bindEvent();
            }
            if (canClose) {
                $mod.data("canClose", true);
            } else {
                $mod.data("canClose", false);
            }
            $mod.show();
        },

        //显示Loading效果
        showLoading: function () {
            $mod.addClass(loadingClass);
            SystemMask.show("", true);
        },

        //隐藏
        hide: function () {
            $mod.removeClass(loadingClass);
            $mod.hide();
        },

        //是否处在加载中
        isLoading: function () {
            return $mod.hasClass(loadingClass);
        }
    };
    return SystemMask;
});