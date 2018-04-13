layui.use('upload', function(){
    var $ = layui.jquery
        ,upload = layui.upload;

    //普通图片上传
    var uploadInst = upload.render({
        elem: '.upload-btn'
        ,url: '/img-upload/index.do'

        ,before: function(obj){
            $('#tmp').attr('value',this.item[0].id);
            // //预读本地文件示例，不支持ie8
            // obj.preview(function(index, file, result){
            //     $('#img'+this.item.id).attr('src', result); //图片链接（base64）
            // });
        }
        ,data: {
            seq:function(){
                return $('#tmp').val();
            }
        }
        ,done: function(res){
            //如果上传失败
            if(res.error_no > 0){
                return layer.msg(res.error_message);
            }
            else {
                $('#img'+this.item[0].id).attr('src', res.data.img_url); //图片链接（base64）
                $('#'+this.item[0].id).attr('innerHTML', '更换');
                return layer.msg('上传成功');
            }
            //上传成功
        }
        ,error: function(){
            //演示失败状态，并实现重传
            return layer.msg('错误');
        }
    });

    });