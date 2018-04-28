define([
    '/widget/ui/form-handle/form-handle.js',
    '/widget/ui/utils/utils.js',
    "/widget/ui/dialog/dialog.js"
],function(formHandle,Utils,Dialog){
    layui.use('form', function(){
        var form = layui.form;

    });

    var $mod = $('.file-upload');
    // var $form = $mod.find('.form');
    // var form = new formHandle({form:$form,uploadType:'ajax',is_check:false});
    // var $headImgUpload = $mod.find('#head_img_upload');
    // $('.note').hide();
    var $files_url = $('#files_url');

    $('#testListAction').click(function () {
        $files_url.val("");
    });

    $('.demo-reload').click(function () {
        $files_url.val("");
    });

    layui.use('upload', function() {
        var $ = layui.jquery
            , upload = layui.upload;

        //选完文件后不自动上传
        upload.render({
            elem: '#file_upload'
            ,url: '/file-upload.do'
            ,auto: false
            //,multiple: true
            ,accept: 'file'
            ,bindAction: '#file_upload_btn'
            ,done: function(res){
                if (res.error_no == 0){
                    Dialog.tip("上传成功");
                    $('#file_url').val(res.data.file_url);
                }
            }
        });

        //多文件列表示例
        var demoListView = $('#demoList')
            ,uploadListIns = upload.render({
            elem: '#testList'
            ,url: '/file-upload.do'
            ,accept: 'file'
            ,multiple: true
            ,auto: false
            ,bindAction: '#testListAction'
            ,choose: function(obj){
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function(index, file, result){
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    demoListView.append(tr);
                });
            }
            ,done: function(res, index, upload){
                if(res.error_no == 0){ //上传成功
                    Dialog.tip("上传成功");
                    var tr = demoListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(''); //清空操作
                    $files_url.val($files_url.val()+res.data.file_url+"\n");
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });
    });

})