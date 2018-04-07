if(PageData.isMobile){
    $.fn.on = function(event, selector, data, callback, one) {
        if(event == "click"){
            return _on.call(this,'tap', selector, data, callback, one);
        }else{
            return _on.call(this,event, selector, data, callback, one);
        }
    };
    $.fn.trigger = function(event, args){
        if(event == "click"){
            return _trigger.call(this,'click', args);
        }else{
            return _trigger.call(this,event, args);
        }
    };
    $.fn.off = function(event, selector, callback){
        if(event == "click"){
            return _off.call(this,'click', selector, callback);
        }else{
            return _off.call(this,event, selector, callback);
        }
    }
}
$.ajaxSettings.timeout = 15000;