define([
    '/widget/ui/form-handle/form-handle.js',
    '/widget/ui/utils/utils.js',
    "/widget/ui/dialog/dialog.js"
],function(formHandle,Utils,Dialog){
    var $mod = $('.page-admin-login');
    var $form = $mod.find('.form');
    var form = new formHandle({form:$form,uploadType:'ajax',is_check:false});
    var $btnWrap = $mod.find('.btn-wrap');

    // form.on('submit-data',function(data){
    //     //用户名
    //     var username = Utils.aesEncrypt(data['username_unencrypt']);
    //     form.data('form').find('input[name=username_unencrypt]').val('').prop('name','');
    //     form.data('form').find('input[name=username]').val(username);
    //     //密码
    //     var password = Utils.aesEncrypt(data['password_unencrypt']);
    //     form.data('form').find('input[name=password_unencrypt]').val('').prop('name','');
    //     form.data('form').find('input[name=password]').val(password);
    //     setTimeout(function(){
    //         form.data('form').off('submit').submit();
    //     },50)
    // });

    form.on('ajax-data',function(data,cb){
        console.log(data)
        var _data = {
            username:data.data['username'],
            password:data.data['password']
        }
        cb(_data);
    });

    //成功回调
    form.on('ajax-success',function(ret){
        location.href =ret.data.redirectURL;
    })

    //错误回调
    form.on('ajax-error',function(ret){
        Dialog.tip(ret.error_message);
    })

    $btnWrap.css('display','block');
})