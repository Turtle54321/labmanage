//表单处理公共模块：验证，ajax提交
define([
    "/widget/ui/dialog/dialog.js",
    "/widget/ui/base/base.js"
],function(Dialog,createUi){
    //返回类
    return createUi({
        _data:{
            //form标签
            form:'',
            uploadType:'normal',
            normalRules:{
                //不为空
                'require':/.+/i,
                //数字
                'number':/^[0-9]+$/i,
                //汉字
                'zh-cn':/^[\u4e00-\u9fa5]+$/i,
                //汉字50
                'zh-cn50':/^[\u4e00-\u9fa5]{1,50}$/i
            },
            escape_tag:true,
            is_check:true
        },
        //初始化
        _init:function(){
            //formel
            var $form = this.data('form');
            //url
            var action = $form.attr('action');
            //method
            var method = $form.attr('method');
            //enctype
            var enctype = $form.attr('enctype');

            this._action = action;
            this._method = method;
            this._enctype = enctype;

            //获取验证规则
            this.getRulesArray();
            //截获提交动作
            this.changeSubmit();
            console.log($form,$form.serializeArray(),action,method,enctype)
        },
        //截获提交动作
        changeSubmit:function(){
            //formel
            var _this = this;
            var $form = this.data('form');
            $form.on('submit',function(e){
                var check;
                console.log(e)
                //暂停
                e.preventDefault();
                check = _this.data('is_check') ? _this.checkData() : { ispassed:true };
                //未通过验证
                if( !check.ispassed ){
                    Dialog.tip(check.error_msg);
                }else{
                    _this.submitForm();
                }
                console.log(_this.checkData());
            })
        },
        //提交
        submitForm:function(){
            var submitType = this.data('uploadType');
            var $form = this.data('form');
            var _this = this;
            var data;
            //方式选择
            if( submitType == 'normal'){
                //表单提交
                $form.off('submit').submit();
            }else if(submitType == 'ajax'){
                //ajax
                data = this.getData();

                $.ajax({
                    type:this._method,
                    dataType:'json',
                    url:this._action,
                    data:data,
                    contentType:this.isFormData() ? 'multipart/form-data;':'application/x-www-form-urlencoded',
                    processData:this.isFormData() ? false : true,
                    success:function(ret){
                        if( ret && ret.error_no == 0){
                            _this.trigger('ajax-success',ret);
                        }else{
                            // Dialog.tip(ret.error_message);
                            _this.trigger('ajax-error',ret);
                        }
                    },
                    fail:function(e){
                        _this.trigger('ajax-fail',e);
                    }
                })
            }else{
                data = this.getData();
                this.trigger('submit-data',data);
            }
        },
        //是否为formData方式
        isFormData:function(){
            return /form\-data/i.test(this._enctype);
        },
        //过滤标签
        filterTag:function(str){
            return str.replace('&','&amp;').replace('<','&lt;').replace('>','&gt;').replace('\'','&#39;').replace('"','&quot;')
        },
        //获取数据
        getData:function(){
            var data;
            var $form = this.data('form');
            //是否有文件
            if( this.isFormData()){
                data = new FormData($form[0]);
            }else{
                // data = $form.serialize();
                data = {};
                $form.serializeArray().forEach(function(item){
                    data[item.name] = item.value;
                })
            }
            //触发事件
            this.trigger('ajax-data',{
                type:this.isFormData() ? 'form-data' :'normal',
                data:data
            },callbackCb);
            //回调
            function callbackCb(_data){
                data = _data;
            }
            return data;
        },
        //check
        checkData:function(){
            var ispassed = true;
            var error_msg = '';
            var normalRules = this.data('normalRules');
            //获取data
            var data = this.getSerializeArray();
            data = this.mergeRules2Serialize(data);
            
            //触发事件
            this.trigger('check-data',data,callbackCb);
            //回调
            function callbackCb(pass,msg){
                ispassed = pass;
                error_msg = msg;
            }
            //检查开始
            data.some(function(item,index){
                //是否有已经不通过的
                if( !ispassed ) return true;
                //循环rules
                for( var k = 0; k < item.rules.length;k++){
                    var reg;
                    if(!item.rules[k]) continue;
                    //如果rules在normalRules里
                    if( normalRules.hasOwnProperty(item.rules[k]) ){
                        reg = normalRules[item.rules[k]];
                    }else{
                        reg = new RegExp(item.rules[k],'i');

                    }
                    //验证不通过
                    if( !reg.test(item.value)){
                        ispassed = false;
                        error_msg = item.label + (item.rules[k] == 'require' ? '不能为空' : '格式不正确');
                        break;
                    }
                }
            });
            return {
                ispassed:ispassed,
                error_msg:error_msg
            }
        },
        //获取验证数据
        getRulesArray:function(){
            //formel
            var $form = this.data('form');
            this._rulesArray = [].slice.call($form.find('input[type!=submit],select,textarea'),0).map(function(el,index){
                var name = $(el).attr('name');
                var rule = $(el).parents('.input-wrap').data('rule') || $(el).data('rule');
                var label = $(el).siblings('label').text();
                rule = rule ? rule.split(/(?!\{\d+),(?!\d+\})/) : [];
                return {
                    name:name,
                    label:label,
                    rules:rule.map(function(rule){
                        //是否为正则
                        if( /^\/(.*)\/i?g?$/.test(rule)){
                            return /^\/(.*)\/i?g?$/.exec(rule)[1]
                        }else{
                            return rule
                        }
                    })
                }
            })
            console.log(this._rulesArray)
        },
        //获取数据
        getSerializeArray:function(){
            return this.data('form').serializeArray();
        },
        //merge rules 到SerializeArray
        mergeRules2Serialize:function(serialize){
            var _this = this;
            //合并rules到数据
            return serialize.map(function(item,index){
                var name = item.name;
                //如果顺序相同
                console.log(_this._rulesArray[index].name,name);
                if( _this._rulesArray[index].name == name){
                    return $.extend({},item,_this._rulesArray[index]);
                }else{
                    //不同去寻找相同name的rule
                    return $.extend({},item,_this._rulesArray.filter(function(rule){
                        return item.name == name
                    })[0]);
                }
            })
        }
    });
});