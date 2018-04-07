define(function(){
    /**
     *
     * 所有ui的基类
     * 如果需要通用的方法，可以在这里添加
     */
    var Base = function(options){
        options = options?options:{};
        this._data = $.extend({},this._data,options);
        this._callbacks = [];
        var date = new Date();
        this.gid = 'ui_'+(date.getTime())+"_"+(Math.floor(Math.random() * ( 1000000 + 1)));
        this._create && this._create();
        this._init && this._init();

    };

    Base.prototype = {
        /**
         * 把set & get的方法统一，并且暴露给外部
         * @param key
         * @param value
         * @returns {*}
         */
        data : function(key,value){
            var _data = this._data;
            if(key instanceof  Object){
                for(var name in key){
                    if(key.hasOwnProperty(name)) _data[name] = key[name];
                }
                return _data;
            }else if(!!key){
                return value !== undefined ? _data[key] = value : _data[key];
            }else{
                return _data;
            }
        },

        /**
         * 唯一id，目前采用自增的方式
         */
        getGid:function(){
            return this.gid;
        },


        _set : function(key,value){
            var me = this;
            if(key instanceof  Object){
                for(var name in key){
                    if(key.hasOwnProperty(name)) me._data[name] = key[name];
                }
            }else{
                this._data[key] = value;
            }
        },

        _get : function(key){
            if(key in this._data){
                return this._data[key];
            }else{
                return false;
            }
        },

        /**
         * 忽略大小写
         * @param key
         * @private
         */
        _caseGet:function(key){
            for(var name in this._data){
                if(this._data.hasOwnProperty(name) && key.toLowerCase() == name.toLowerCase()){
                    return this._data[name];
                }
            }
            return false;
        },

        /**
         * 事件预留容器
         * @type {{}}
         * @private
         */
        _callbacks : [],

        /**
         * 绑定事件
         * @param    {String}      ev          事件名
         * @param    {Function}    callback    事件处理函数
         * @param    {Object}      context     上下文对象
         */
        bind : function(ev,callback,context){
            ev = ev.toLowerCase();
            var callbacks = this._callbacks,
                list = callbacks[ev] || (callbacks[ev] = []);
            list.push([callback,context]);
            return this;
        },

        /**
         * 与bind保持类似的方法
         */
        on:function(){
            return this.bind.apply(this,arguments);
        },

        /**
         * 注销事件
         * @param    {String}      ev          事件名  为空，清除所有的事件
         * @param    {Function}    callback    事件处理函数
         */
        off : function(ev,callback){
            var callbacks;
            if(!ev){
                this._callbacks = {};
            }else if(callbacks = this._callbacks){
                ev = ev.toLowerCase();
                if(!callback){
                    callbacks[ev] = [];
                }else{
                    var list = callbacks[ev];
                    if(!list)return this;
                    for(var i=0, len=list.length;i<len;i++){
                        if(list[i] && callback === list[i][0]){
                            list[i] = null;
                            break;
                        }
                    }
                }
            }
            return this;
        },

        /**
         * 触发事件
         * @param    {String}      ev             事件名
         * @param    {All}         arguments      需要传递的参数
         */
        trigger : function(ev){
            var type = ev.toLowerCase(),
                handler = this._caseGet('on' + type),
                args = Array.prototype.slice.call(arguments).slice(1),
                callbacks = this._callbacks,
                list,callback;

            handler && handler.apply(this, args);
            if(!type){
                return this;
            }else if(list = callbacks[type]){
                for(var i=0;i<list.length;i++){
                    callback = list[i];
                    callback[0].apply(callback[1] || this, args);
                }
                return this;
            }
            return this;
        },

        /**
         *
         * @returns {*}
         */
        widget:function(){
            return this.data("$root");
        },

        /**
         * todo 很重要
         * dom && 事件销毁
         * @returns {*}
         */
        destroy:function(){
            return this;
        },

        /**
         * 实现面向对象的super方法
         * @param fun
         */
        superCall:function(fun){

        }
    };


    var createUi = function(proto){
        // 采用object.create方式 拥抱新特性^_^
        var o = function(options){
            if( proto._data ){
                this._data = proto._data;
            }
            Base.call(this,options);
        }
        o.prototype.constructor = o;
        o.prototype = Object.create(Base.prototype);
        for(var name in proto){
            if(proto.hasOwnProperty(name) && name != '_data'){
                o.prototype[name] = proto[name];
            }
        }
        return o
    };

    return createUi
})

