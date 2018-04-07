//实现与ios的通信

(function () {

    function connectWebViewJavascriptBridge(callback) {
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge);
        } else {
            document.addEventListener('WebViewJavascriptBridgeReady', function () {
                callback(WebViewJavascriptBridge);
            }, false);
        }
    }

    connectWebViewJavascriptBridge(function (bridge) {

        bridge.registerHandler('back', function (data, responseCallback) {
            var responseData = {'function': 'back'};
            responseCallback(responseData);
        });

        bridge.init(function (message, responseCallback) {
            var data = {'Javascript Responds': 'Wee!'};
            responseCallback(data);
        });

        if (!window[PageData.project]) {
            window[PageData.project] = {};
        }

        addNormalMethod("login");  //财小仙中是吊起app登录，真融宝中是通知app登录
        addNormalMethod("gotoLogin");  //真融宝吊起app登录
        addNormalMethod("setPreBackFunc"); //设置当点击返回按钮时客户端需要调用的函数
        addNormalMethod("back");

        function addNormalMethod(method, outParams) {
            outParams = outParams || {'call': method} || "";
            addMethod(method, function (innerParams) {
                var params = innerParams ? JSON.parse(innerParams) : outParams;
                bridge.callHandler(method, params, function (response) {

                });
            });
        }
    });

    //添加方法
    function addMethod(method, func, rewrite) {
        if (!rewrite && window[PageData.project][method] && typeof window[PageData.project][method] == "function") {
            return false;
        }
        window[PageData.project][method] = func;
    }

})();

