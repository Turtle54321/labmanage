define([
    '/widget/ui/form-handle/form-handle.js',
    '/widget/ui/utils/utils.js',
    "/widget/ui/dialog/dialog.js"
],function(formHandle,Utils,Dialog){
    console.log("c")
    layui.use('form', function(){
        var form = layui.form;
        form.on('select(select)', function(data){
            var id = data.value;
            if (id == 1){
                //是教师则显示备注输入框
                $('.note').show();
                $('#graduate').hide();
            }
            else{
                $('.note').hide();
                $('#graduate').show();
            }
        });
        form.verify({
            my_url:function(value, item) { //value：表单的值、item：表单的DOM对象
                // /(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/
                if (value.length > 0){
                    if (!/(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/.test(value)){
                        return '链接格式不正确';
                    }
                }
            }
        });
    });


    var E = window.wangEditor;
    var editorZH = new E('#editorZH');
    var editorEN = new E('#editorEN');

    var $content = $('#content');
    var $econtent = $('#econtent');
    editorZH.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $content.val(html);
    };
    editorEN.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $econtent.val(html);
    };
    editorZH.create();
    editorEN.create();
    // 初始化 textarea 的值
    $content.val(editorZH.txt.html());
    $econtent.val(editorEN.txt.html());

    var $mod = $('.member-change');
    var $form = $mod.find('.form');
    var form = new formHandle({form:$form,uploadType:'ajax',is_check:false});
    var $headImgUpload = $mod.find('#head_img_upload');
    // $('.note').hide();

    var init = function () {
        initDatePicker();
        bindEvent();
    }();

    form.on('ajax-data',function(data,cb){
        console.log(data)
        var _data = {
            id:data.data['id'],
            name:data.data['name'],
            ename:data.data['ename'],
            headUrl:data.data['headUrl'],
            status:data.data['status'],
            note:data.data['note'],
            enote:data.data['enote'],
            content:data.data['content'],
            econtent:data.data['econtent'],
            contentUrl:data.data['contentUrl'],
            enterTime:data.data['enterTime'],
            graduateTime:data.data['graduateTime']
        }
        _data.enterTime = _data.enterTime ? new Date(_data.enterTime) / 1000 : '';
        _data.graduateTime = _data.graduateTime ? new Date(_data.graduateTime) / 1000 : '';
        cb(_data);
    });

    //成功回调
    form.on('ajax-success',function(ret){
        Dialog.tip("提交成功");
        setTimeout(function(){//两秒后跳转
            location.href = "/member-manage.htm?whichPage=1&perCount=8";
        },2000);

    })

    //错误回调
    form.on('ajax-error',function(ret){
        Dialog.tip(ret.error_message);
    })

    //日历插件
    function initDatePicker() {
        $('.datePicker').datetimepicker({
            locale: 'zh-cn',
            format: 'Y-m-d',
            sideBySide: true,
            timepicker:false
        });
    }
    function bindEvent(){
        // $mod.find("#head_img_upload").on("click",function(){
        //     Dialog.tip("success");
        //
        // });
    }
    // 文件上传
    layui.use('upload', function(){
        var $ = layui.jquery
            ,upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '.upload-btn'
            ,url: '/img-upload/member.do'
            ,done: function(res){
                //如果上传失败
                if(res.error_no > 0){
                    return layer.msg(res.error_message);
                }
                else {
                    //上传成功
                    $('#head_img').attr('src', res.data.img_url); //图片链接（base64）
                    $headImgUpload[0].innerHTML= '更换头像';
                    $('#headUrl').val(res.data.img_url);
                    return layer.msg('上传成功');
                }

            }
            ,error: function(){
                //演示失败状态，并实现重传
                return layer.msg('错误');
            }
        });

    });
})