define([
    '/widget/ui/form-handle/form-handle.js',
    '/widget/ui/utils/utils.js',
    "/widget/ui/dialog/dialog.js"
],function(formHandle,Utils,Dialog){
    var $mod = $('.introduction-manage');
    var $form = $mod.find('.form');
    var form = new formHandle({form:$form,uploadType:'ajax',is_check:false});

    form.on('ajax-data',function(data,cb){
        console.log(data)
        var _data = {
            id:data.data['id'],
            content:data.data['content'],
            econtent:data.data['econtent']
        }
        cb(_data);
    });

    //成功回调
    form.on('ajax-success',function(ret){
        Dialog.tip("提交成功");
    })

    //错误回调
    form.on('ajax-error',function(ret){
        Dialog.tip(ret.error_message);
    })

})