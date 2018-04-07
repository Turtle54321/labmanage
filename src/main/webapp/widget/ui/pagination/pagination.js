
define([
    "/widget/ui/dialog/dialog.js",
    "/widget/ui/base/base.js"
],function(Dialog,createUi){
    return createUi({
        _data:{
            pageName:"",
            container:"body",
            content:"",
            totalCount:0,
            perCount:10,
            getUrl:"",
            cur:1,
            refreshIng:false,
            onRefresh:function(curPage,callback){} //更新数据
        },

        getTpl:function(data){
            var tpl = '';
            tpl += '<div class="ui-pagination" id="'+data.gid+'">' +
                '    <div class="page-slider-loading"></div>' +
                '    <div class="page-slider-no-record">没有查询记录</div>' +
                '    <div class="page-slider" >' +
                '        <a class="png page-btn first-btn"></a>' +
                '        <a class="png page-btn pre-btn"></a>' +
                '        <span class="point left-point">…</span>' +
                '        <div class="page-number-container">' +
                '        </div>' +
                '        <span class="point right-point">…</span>' +
                '        <a class="png page-btn next-btn"></a>' +
                '        <a class="png page-btn last-btn"></a>' +
                '        <div class="page-search">' +
                '            <span class="total-count">共'+data.totalCount+'页</span>,' +
                '            <span>跳转到</span><input type="text" class="search-number">' +
                '            <a class="main search-btn">GO</a>' +
                '        </div>' +
                '    </div>' +
                '</div>'
            return tpl;
        },

        _create:function(){
            var me = this;
            var pageCount = this.getPageCount();
            var html = this.getTpl({totalCount:pageCount,gid:this.gid});
            var $root;
            if(this.data("content")){
                $root = $(this.data("content"));
            }else{
                $(this.data("container")).append(html);
                $root = $("#"+this.gid);
            }

            var dialog = Dialog.tip;
            this.data({
                '$root':$root,
                'dialog':dialog,
                '$loading':$root.find('.page-slider-loading'),
                '$noRecord':$root.find('.page-slider-no-record'),
                '$pageSlider':$root.find('.page-slider'),
                'onRefreshInstance':function(curPage,callback){
                    me.loading();
                    me.trigger('refresh',curPage,callback);
                }
            });
            this.initPage(this.data('cur'));
        },

        _init:function(){
            var me = this,
                dialog = this.data('dialog'),
                $root = this.data("$root");
            $root.find(".pre-btn").click(function(){
                var cur = me.data("cur");
                if(cur>1){
                    cur--;
                    me.trigger("refreshinstance",cur,function(){
                        me.createPageList(cur);
                    });
                }
            });

            $root.find(".next-btn").click(function(){
                var cur = me.data("cur"),
                    pageCount = me.getPageCount();
                if(cur<pageCount){
                    cur++;
                    me.trigger("refreshinstance",cur,function(total){
                        me.createPageList(cur);
                    });
                }
            });

            $root.find(".first-btn").click(function(){
                var cur = me.data("cur");
                if(cur!=1){
                    me.trigger("refreshinstance", 1, function(){
                        me.createPageList(1);
                    });
                }
            });

            $root.find(".last-btn").click(function(){
                var cur = me.data("cur"),
                    pageCount = me.getPageCount();
                if(cur!=pageCount){
                    me.trigger("refreshinstance",pageCount,function(total){
                        me.createPageList(pageCount);
                    });
                }
            });


            $root.find(".search-btn").click(function(){
                var cur = $root.find(".search-number").val(),
                    pageCount = me.getPageCount();
                if(me.data("cur")!=cur){
                    if(/^\d*$/.test(cur)){
                        if(cur>0 && cur<=pageCount){
                            cur = parseInt(cur);
                            me.trigger("refreshinstance",cur,function(){
                                me.createPageList(cur);
                            });
                        }else{
                            dialog("页码输入错误~")
                        }
                    }else{
                        dialog("请输入有效的数字~")
                    }
                }
            });
            this._pageEvent();
        },

        /**
         * 生成页码
         * @private
         */
        createPageList:function(cur){
            var $root = this.data("$root"),
                pageCount = this.getPageCount(),
                pageList = [],
                pageHtml = "",
                i =0;
            pageCount>0?this.showPage():this.noRecord();
            this.data("cur",cur);
            if(pageCount<=5 || cur<=3){
                for(i=1;i<=pageCount&&i<=5;i++){
                    pageList.push(i);
                }
            }else if(cur>pageCount-2){
                for(i=cur-(5-pageCount+cur-1);i<=pageCount;i++){
                    pageList.push(i);
                }
            }else{
                for(i=cur-2;i<=cur+2;i++){
                    pageList.push(i);
                }
            }
            console.log(pageCount,cur,'hahah')
            for(i=0;i<pageList.length;i++){
                pageHtml+='<a data-page="'+pageList[i]+'" class="page-number '+
                    (cur==pageList[i]?"cur-page":"")+'">'+pageList[i]+'</a>';
            }
            if(pageCount>5){
                pageList[0]>1?$root.find(".left-point").show():$root.find(".left-point").hide();
                pageList[4]<pageCount?$root.find(".right-point").show():$root.find(".right-point").hide();
            }else{
                $root.find(".left-point").hide();
                $root.find(".right-point").hide();
            }

            $root.find('.search-number').val(cur);
            $root.find(".page-number-container").html(pageHtml);
            $root.find(".total-count").text("共"+pageCount+"页");
            this._pageEvent();
        },

        /**
         * 计算总页面
         * @private
         */
        getPageCount:function(){
            if(this.data("totalCount") && this.data("totalCount")>0){
                return Math.ceil(this.data("totalCount")/this.data("perCount"));
            }else{
                return 0;
            }
        },

        /**
         * 设置图片总数
         * @param count
         * @param noRefresh  false|不设:触发countChange ，true不触发countChange事件
         */
        setTotalCount:function(count,noRefresh){
            this.data("totalCount",count);
            count>0?this.show():this.hide();
            noRefresh || this.trigger("countChange",this.data("cur"));
        },

        /**
         * 跳转到第几页
         * 如果cur大于总页数，将cur设为最后一页
         * @private
         */
        positionTo:function(cur){
            var me = this,
                pageCount = this.getPageCount();
            if(cur>pageCount){
                cur = pageCount;
                me.data("cur",cur);
            }
            this.trigger("refreshinstance",cur,function(total){
                me.createPageList(cur);
            });
        },

        //初始化页码
        initPage:function(cur){
            var me = this;
            this.trigger("refreshinstance",cur,function(totalCount){
                me.data('totalCount',totalCount);
                var pageCount = me.getPageCount();
                if(cur>pageCount){
                    cur = pageCount;
                    me.data("cur",cur);
                }
                me.createPageList(cur);
            });
        },


        /**
         * 隐藏
         */
        hide:function(){
            this.data("$root").hide();
        },


        loading:function(){
            this.data('$noRecord').hide();
            this.data('$pageSlider').hide();
            this.data('$loading').show();
        },

        noRecord:function(){
            this.data('$loading').hide();
            this.data('$pageSlider').hide();
            this.data('$noRecord').show();
        },


        showPage:function(){
            this.data('$loading').hide();
            this.data('$noRecord').hide();
            this.data('$pageSlider').show();
        },


        /**
         * 显示
         */
        show:function(){
            this.data("$root").show();
        },

        /**
         * 跳转到最后一页
         */
        toLastPage:function(){
            var lastPage = this.getPageCount(),
                me = this;
            this.trigger("refreshinstance",lastPage,function(){
                me.createPageList(lastPage);
            });
        },

        /**
         * 页码点击事件
         * @private
         */
        _pageEvent:function(){
            var me = this;
            this.data("$root").find(".page-number").click(function(){
                var pageIndex = parseInt($(this).attr("data-page")),
                    cur = me.data("cur");
                if(pageIndex!=cur){
                    me.trigger("refreshinstance",pageIndex,function(){
                        me.createPageList(pageIndex);
                    });
                }
            });
        }
    })
})