define(function () {
    /**
     * 前端校验小组件
     */
    var Checker = {

        /**
         * 统一检查方法
         * @param options
         * @returns {boolean}
         * 调用示例：
         * Checker.check({
     *   $phone: $phone,
     *   $idName: $idName,
     *   $idNumber: $idNumber,
     *   $phoneCode: $phoneCode,
     *   $bankNumber: $bankNumber,
     *   $loginPassword: $loginPassword,
     *   $reLoginPassword: $reLoginPassword,
     *   $tradePassword: $tradePassword,
     *   $captcha: $captcha,
     *   $chineseCode: $chineseCode
     * })
         */
        check: function (options) {
            options = options || {};
            var $tip = options.$tip || $("");
            var key;
            var $input;
            var checkCall;
            var value;
            var result;
            $tip.css("visibility", "hidden");
            for (key in options) {
                if (options.hasOwnProperty(key) && key != "$tip" && key != "$check" && key.substring(1) in Checker) {
                    checkCall = Checker[key.substring(1)];
                    $input = options[key];
                    value = $.trim($input.val());
                    if (key == "$reLoginPassword") {
                        result = checkCall(value, $.trim(options["$loginPassword"].val()));
                    } else {
                        result = checkCall(value);
                    }
                    if (!result.passed) {
                        $tip.text(result.message).css("visibility", "visible");
                        return false;
                    }
                }
            }
            return true;
        },

        //手机号的校验
        phone: function (phone) {
            if (!phone) {
                return formatResult("手机号码不能为空", 1);
            } else if (!/^1\d{10}$/.test(phone)) {
                return formatResult("手机号码输入不正确", 2);
            }
            return formatResult();
        },

        //姓名的校验
        idName: function (idName) {
            if (!idName) {
                return formatResult("姓名不能为空", 1);
            } else if (!/[^u4e00-u9fa5]/.test(idName) || idName.length < 2 || idName.length > 30) {
                return formatResult("姓名输入不正确", 2);
            }
            return formatResult();
        },

        //身份证号的校验
        idNumber: function (idNumber) {
            if (!idNumber) {
                return formatResult("身份证号不能为空", 1);
            } else if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idNumber)) {
                return formatResult("身份证号输入不正确", 2);
            }
            return formatResult();
        },

        //手机验证码的校验
        phoneCode: function (phoneCode) {
            if (!phoneCode) {
                return formatResult("手机验证码不能为空", 1);
            } else if (!/^\d{6}$/.test(phoneCode)) {
                return formatResult("手机验证码必须为6位整数", 2);
            }
            return formatResult();
        },

        //银行卡号校验
        bankNumber: function (bankNumber) {
            if (!bankNumber) {
                return formatResult("银行卡号不能为空", 1);
            } else if (!/^\d{15,20}$/.test(bankNumber)) {
                return formatResult("银行卡号输入不正确", 2);
            }
            return formatResult();
        },

        //登录密码校验
        loginPassword: function (password) {
            if (password.length < 6 || password.length > 16) {
                return formatResult("密码长度要求在6-16位", 1);
            }
            return formatResult();
        },

        //第二次输入登录密码的校验
        reLoginPassword: function (rePassword, password) {
            if (rePassword != password) {
                return formatResult("两次密码输入不一致", 1);
            }
            return formatResult();
        },

        //交易密码的校验
        tradePassword: function (tradePassword, notSixDigit) {
            if (!tradePassword) {
                return formatResult("交易密码不能为空", 1);
            } else if (!notSixDigit && !/^\d{6}$/.test(tradePassword)) {
                return formatResult("交易密码必须为6位正整数", 2);
            }
            return formatResult();
        },

        //图形验证码的校验
        captcha: function (code) {
            if (!code) {
                return formatResult("图形验证码不能为空", 1);
            } else if (!/^[0-9a-zA-Z]{4}$/.test(code)) {
                return formatResult("图形验证不正确", 2);
            }
            return formatResult();
        },

        //注册、忘记密码用到的中文点选码的校验
        chineseCode: function (code) {
            if (!/^[0-9a-zA-Z]{32}$/.test(code)) {
                return formatResult("验证码不正确", 1);
            }
            return formatResult();
        }
    };


    //输出结果的格式化
    //type：0为校验成功，1一般为空，2一般为格式不正确
    function formatResult(errorMessage, errorNo) {
        return {
            type: errorNo || (errorMessage ? 1 : 0),
            message: errorMessage || "",
            passed: !(errorNo || errorMessage) //是否通过校验
        };
    }

    return Checker;
});