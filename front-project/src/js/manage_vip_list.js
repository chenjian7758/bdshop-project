define(['jquery', "template", "components", "bootstrap", "manageCommon"], function(jquery,  template, components, bootstrap, manageCommon) {
    getModules();
    function getModules() {
        var flag1 = false;
        var flag2 = false;
        var flag3 = false;
        var flag4 = false;
        var flag5 = false;
        var flag6 = false;
        //$.ajax({
        //    url: apiUrl + "/admin/module/module/queryModules",
        //    type: 'GET',
        //    xhrFields: {
        //        withCredentials: true
        //    },
        //    crossDomain: true,
        //}).done(function (msg) {
        components.getMsg(apiUrl + "/admin/module/module/queryModules").done(function (msg) {
            if (msg.res == 1) {
                var objList = msg.obj;
                for (var i = 0; i < objList.length; i++) {
                    var module = objList[i];
                    var id = module.moduleId;
                    var pid = module.parentId;
                    if (id == 7) {
                        $("#goodsList").css("display", "block");
                        flag1 = true;
                    }
                    if (id == 8) {
                        $("#goodsOrder").css("display", "block");
                        flag1 = true;
                    }
                    if (id == 9) {
                        $("#goodsCatory").css("display", "block");
                        flag1 = true;
                    }
                    if (id == 10) {
                        $("#memberManage").css("display", "block");
                        flag2 = true;
                    }
                    if (id == 11) {
                        $("#messageManage").css("display", "block");
                        flag2 = true;
                    }
                    if (id == 12) {
                        $("#articleManage").css("display", "block");
                        flag3 = true;
                    }
                    if (id == 13) {
                        $("#adManage").css("display", "block");
                        flag4 = true;
                    }
                    if (id == 14) {
                        $("#goodsReport").css("display", "block");
                        flag5 = true;
                    }
                    if (id == 15) {
                        $("#orderReport").css("display", "block");
                        flag5 = true;
                    }
                    if (id == 16) {
                        $("#adminManage").css("display", "block");
                        flag6 = true;
                    }
                    if (id == 17) {
                        $("#roleManage").css("display", "block");
                        flag6 = true;
                    }
                    if (flag1) {
                        $("#div1").css("display", "block");
                    }
                    if (flag2) {
                        $("#div2").css("display", "block");
                    }
                    if (flag3) {
                        $("#div3").css("display", "block");
                    }
                    if (flag4) {
                        $("#div4").css("display", "block");
                    }
                    if (flag5) {
                        $("#div5").css("display", "block");
                    }
                    if (flag6) {
                        $("#div6").css("display", "block");
                    }
                    if (flag1 || flag2 || flag3 && flag4 || flag5 || flag6) {
                        $('.container-wrap').css('display', 'block')
                    }
                }
            }
            //location.href = "/page/manage_login.html";
        });
    }

    $('#submit-btn-query').on('click', function(){
		getData(1);
	});
	 	
	function getData(pageNo) {       
       var pageSize=10;
		var data = $("#form-box").serialize();	
		data=data+"&pageSize="+pageSize;		
        searchData=data;
        data=data+"&pageNo="+pageNo;
        var url = apiUrl + "/admin/user/userAdmin/queryUserByPage";
        components.getMsg(url, data, "post").done(function(msg) {
            var res = msg.res;
            if (res == 1) {
                msg = msg.obj;               
                 render(msg);
                    var options = {
                        bootstrapMajorVersion: 3, //版本
                        currentPage: 1, //当前页数
                        totalPages: 1, //总页数
                        itemTexts: function(type, page, current) {
                            switch (type) {
                                case "first":
                                    return "首页";
                                case "prev":
                                    return "上一页";
                                case "next":
                                    return "下一页";
                                case "last":
                                    return "末页";
                                case "page":
                                    return page;
                            }
                        }, //点击事件，用于通过Ajax来刷新整个list列表
                        onPageClicked: function(event, originalEvent, type, page) {
                            $("#cloading").removeClass("hidden");
                            var data = searchData;
                            data=data+"&pageNo="+page;
                            selectIdListData=new Object();//保存选中了哪些条目
                            components.getMsg(url, data, "post").done(function(msg) {
                                render(msg.obj);
                            });
                        }
                    };
                    options.totalPages = msg.pages;
                    $(".page-sum").html('当前页面总数：<span class="text-333">' + msg.pages + '</span> 当前总个数：<span class="text-333">' + msg.total + '</span>');
                    $("#cloading").addClass("hidden");
                    $('#pagination').bootstrapPaginator(options);
                    $("#pagination li").removeClass("active").eq(data.pageNo - 1).addClass("active");
                    if (options.totalPages == 1) {
                        $("#pagination").hide();
                    } else {
                        $("#pagination").show();
                    }
                } else {
                    document.getElementById('table-list-tpl').innerHTML = "没有数据";
                    $("#cloading").addClass("hidden");
                    $(".page-sum").html('');
                    $("#pagination").hide();
                    document.getElementById('table-list-content').innerHTML = "没有数据";
                }
            });
        }

    function render(msg) {
        var html = template('table-list-tpl', msg);
        document.getElementById('table-list-content').innerHTML = html;
    }

    listUrl = apiUrl + "/admin/user/userAdmin/getUserByPage";
    require(['./table']);
});