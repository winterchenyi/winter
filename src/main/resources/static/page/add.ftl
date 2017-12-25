<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>添加</title>
    <link rel="stylesheet" href="/static/css/index.css">
</head>
<body>
<header>
    <div class="header-line"></div>
</header>
<div class="content">
    <#--<img class="content-logo" src="/static/images/jianpam.png" alt="logo">-->
    <h1 class="content-title">添加</h1>
    <div class="content-form">
        <form>
            <div id="change_margin_1">
                <input id="loginName" class="user" name="loginName" type="text" placeholder="请输入登录名/账号" />
            </div>
            <div id="change_margin_2">
                <input id="password" class="password" name="password" type="password" placeholder="请输入密码" />
            </div>
            <div id="change_margin_2">
                <input id="userName" class="user" name="userName" type="text" placeholder="请输入用户名" />
            </div>
            <div id="change_margin_2">
                <input id="userAge" class="user" name="userAge" type="text" placeholder="请输入年龄" />
            </div>
            <div id="change_margin_2">
                <input id="sex" class="user" name="sex" type="text" placeholder="请输入性别"/>
            </div>
            <div id="change_margin_2">
                <input id="cellPhone" class="user" name="cellPhone" type="text" placeholder="请输入手机号"/>
            </div>
            <div id="change_margin_3">
                <input class="content-form-signup" type="button" onclick="getSubmit()" value="提交" />
            </div>
        </form>
    </div>
</div>
<script src="/static/js/plugin/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">

    //若输入框为空，阻止表单的提交
    function getSubmit() {
        var loginName = $("#loginName").val();
        var pwd = $("#password").val();
        var userName = $("#userName").val();
        var userAge = $("#userAge").val();
        var sex = $("#sex").val();
        var cellPhone = $("#cellPhone").val();
        if (loginName=='' || pwd=='' || userName=='' || userAge=='' || sex=='' || cellPhone=='') {
            alert("请填写完整！");
            return false;
        } else{
            var param = {
                "loginName" : loginName,      //测试后台参数验证
                "password" : pwd,
                "userName" : userName,
                "userAge" : userAge,
                "sex" : sex,
                "cellPhone" : cellPhone
            };
            var requestBody = new Object();
            requestBody.body = param;
            requestBody.appId = "PC";
//            requestBody.page = ;  无分页
            $.ajax({
                headers    : {
                    "token" : ""
                },
                url : "/acconut/save",
                contentType: "application/json; charset=utf-8",
//                dataType:"json",
                type : "POST",
                data : JSON.stringify(requestBody),
                success : function(data){
                    console.log(data);
                    var cdoe = data.code;
                    var msg = data.msg;
                    var _data = data.data;
                    if(cdoe != "0000"){
                        alert(msg + _data);
                    }else{
                        window.location.href="/link?link=/list";
                    }
                }
            })
        }
    }

</script>
</body>
</html>