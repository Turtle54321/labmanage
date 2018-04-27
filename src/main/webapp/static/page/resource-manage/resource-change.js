define([
    '/widget/ui/form-handle/form-handle.js',
    '/widget/ui/utils/utils.js',
    "/widget/ui/dialog/dialog.js"
],function(formHandle,Utils,Dialog){
    layui.use('form', function(){
        var form = layui.form;

    });

    var $mod = $('.resource-change');
    // var $form = $mod.find('.form');
    // var form = new formHandle({form:$form,uploadType:'ajax',is_check:false});
    // var $headImgUpload = $mod.find('#head_img_upload');
    // $('.note').hide();


    // 文件上传
    layui.use('upload', function(){
        var $ = layui.jquery
            ,upload = layui.upload;
        upload.render({
            elem: '#resource_upload'
            ,url: '/resource-change.do'
            ,auto: false
            ,accept: 'file'
            //,multiple: true
            ,bindAction: '#submit'
            ,data: {
                resourceId: function(){
                    return $('#resource_id').val();
                },
                name: function(){
                    return $('#name').val();
                },
                ename: function(){
                    return $('#ename').val();
                }
            }

            ,done: function(res){
                if(res.data&&ret.error_no==0){
                    Dialog.tip("提交成功");
                    setTimeout(function(){//两秒后跳转
                        location.href = "/resource-manage.htm?whichPage=1&perCount=8";
                    },2000);

                }else{
                    Dialog.tip(ret.error_message);
                }

            }
            ,error: function(){
                Dialog.tip("网络出错，请稍后再试");
            }
        });



    });
})