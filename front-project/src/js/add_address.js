define(['jquery', "components", "common", "weui", "cityPicker", "template"], function (jquery, components, common, weui, cityPicker, template) {
    var receiveId = components.GetQueryString("id");
    var rid = components.GetQueryString("rid");
    var text = "";
    var isphone = false,
        isuser = false,
        isaddress = false,
        isphoneText = "请输入手机号码",
        isuserText = "请输入收货人姓名",
        isaddressText = "请输入详细地址";
    if (receiveId) {
        getDate();
        text = "编辑成功";
    } else {
        initCity();
        initInfo();
        saveAddress();
        text = "添加成功";
    }
    /*
     *
     *
     * */
    function getDate() {
        components.getMsg(apiUrl + "/front/receive/receive/getReceiveById?receiveId=" + receiveId).done(function (msg) {
            var res = msg.res;
            if (res == 1) {
                isphone = true;
                isuser = true;
                isaddress = true;
                var data = msg.obj;
                for (var k in data) {
                    $('input[name=' + k + ']').val(data[k])
                }
                $('#city-picker').val(data.receiveProvince + " " + data.receiveCity + " " + data.receiveCounty);
                $('#address').val(data.receiveAddress);
                initCity();
                initInfo();
                saveAddress(receiveId);
            }
        });
    }

    function saveAddress(id) {
        $("#submitAddress").click(function () {
            var address = $("#city-picker").val();
            if (address != null) {
                var splitAddress = address.split(" ");
                $("#receiveProvince").val(splitAddress[0]);
                $("#receiveCity").val(splitAddress[1]);
                $("#receiveCounty").val(splitAddress[2]);
            }
            var data = $("#add-address-box").serialize();
            var url = "";
            if (id) {
                data = data + "&receiveId=" + id;
                url = apiUrl + "/front/receive/receive/editReceive";
            } else {
                url = apiUrl + "/front/receive/receive/addReceive";
            }
            if (!isphone) {
                $.toast(isphoneText, "text");
            } else if (!isuser) {
                $.toast(isuserText, "text");
            } else if (!isaddress) {
                $.toast(isaddressText, "text");
            } else {
                components.getMsg(url, data, "post").done(function (msg) {
                    var res = msg.res;
                    if (res == 1) {
                        rid = msg.obj.receiveId;
                        window.location = "/page/choice_address.html?rid="+rid;
                    }
                });
            }
        });
    }


    function initInfo() {
        var $phone = $("#phone"),
            $userName = $("#userName"),
            $address = $("#address");

        // 判断收货人
        $userName.blur(function () {
            var str = $(this).val();
            if (getStrLength(str) == 0) {
                $.toast("请输入姓名", "text");
                isuser = false;
                isuserText = "请输入姓名";
            } else {
                isuser = true;
            }
        });
        // 判断手机号码
        $phone.blur(function () {
            var str = $(this).val();
            var re = /^1([3578]\d|4[57])\d{8}$/;
            if (getStrLength(str) == 0) {
                $.toast("请输入手机号码", "text");
                isphone = false;
                isphoneText = "请输入手机号码";
            } else if (!re.test(str)) {
                $.toast("手机格式错误", "text");
                isphone = false;
                isphoneText = "手机格式错误";
            } else {
                isphone = true;
            }
        });

        // 判断收货地址
        $address.blur(function () {
            var str = $(this).val();
            if (getStrLength(str) == 0) {
                $.toast("请输入详细地址", "text");
                isaddress = false;
                isaddressText = "请输入详细地址";
            } else {
                isaddress = true;
            }
        });
    }

    // 得到字符串长度
    function getStrLength(str) {
        return str.replace(/[^\x00-xff]/g, "xx").length;
    }

    // 初始化地区
    function initCity() {
        $("#city-picker").cityPicker({
            title: "选择地址"
        });
    }

});
