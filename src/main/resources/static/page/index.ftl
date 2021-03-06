<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="/static/css/index.css">
</head>
<body>
<script type="text/javascript" color="0,0,255" opacity='0.7' zIndex="-2" count="99" src="//cdn.bootcss.com/canvas-nest.js/1.0.1/canvas-nest.min.js"></script>
<#--<script type="text/javascript" src="http://ip.chinaz.com/getip.aspx"></script>-->
<header>
    <div class="header-line"></div>
</header>
<div class="content">
    <img class="content-logo" src="/static/images/jianpam.png" alt="logo">
    <h1 class="content-title">登录</h1>
    <div class="content-form">
        <form>
            <div id="change_margin_1">
                <input class="user" name="loginName" type="text" placeholder="请输入用户名" onblur="oBlur_1()" />
            </div>
            <!-- input的value为空时弹出提醒 -->
            <p id="remind_1"></p>
            <div id="change_margin_2">
                <input class="password" name="password" type="password"placeholder="请输入密码" onblur="oBlur_2()" />
            </div>
            <!-- input的value为空时弹出提醒 -->
            <p id="remind_2"></p>
            <div id="change_margin_3">
                <input class="content-form-signup" type="button" onclick="getSubmit()" value="登录" />
            </div>
        </form>
    </div>
    <div class="content-login-description">没有账户？</div>
    <div><a class="content-login-link" href="#">注册</a></div>
</div>
<script src="/static/js/plugin/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">

    function getusername() {
        return document.getElementsByTagName("input")[0].value;
    }
    function getpwd() {
        return document.getElementsByTagName("input")[1].value;
    }
    
    //用户框失去焦点后验证value值
    function oBlur_1() {
        if (!getusername()) { //用户框value值为空
            document.getElementById("remind_1").innerHTML = "请输入用户名！";
            document.getElementById("change_margin_1").style.marginBottom = 1 + "px";
        } else { //用户框value值不为空
            document.getElementById("remind_1").innerHTML = "";
            document.getElementById("change_margin_1").style.marginBottom = 19 + "px";
        }
    }

    //密码框失去焦点后验证value值
    function oBlur_2() {
        if (!getpwd()) { //密码框value值为空
            document.getElementById("remind_2").innerHTML = "请输入密码！";
            document.getElementById("change_margin_2").style.marginBottom = 1 + "px";
            document.getElementById("change_margin_3").style.marginTop = 2 + "px";
        } else { //密码框value值不为空
            document.getElementById("remind_2").innerHTML = "";
            document.getElementById("change_margin_2").style.marginBottom = 19 + "px";
            document.getElementById("change_margin_3").style.marginTop = 19 + "px";
        }
    }

    //若输入框为空，阻止表单的提交
    function getSubmit() {
        if (!getusername() && !getpwd()) { //用户框value值和密码框value值都为空
            document.getElementById("remind_1").innerHTML = "请输入用户名！";
            document.getElementById("change_margin_1").style.marginBottom = 1 + "px";
            document.getElementById("remind_2").innerHTML = "请输入密码！";
            document.getElementById("change_margin_2").style.marginBottom = 1 + "px";
            document.getElementById("change_margin_3").style.marginTop = 2 + "px";
            return false;
        } else if (!getusername()) { //用户框value值为空
            document.getElementById("remind_1").innerHTML = "请输入用户名！";
            document.getElementById("change_margin_1").style.marginBottom = 1 + "px";
            return false;
        } else if (!getpwd()) { //密码框value值为空
            document.getElementById("remind_2").innerHTML = "请输入密码！";
            document.getElementById("change_margin_2").style.marginBottom = 1 + "px";
            document.getElementById("change_margin_3").style.marginTop = 2 + "px";
            return false;
        }else{
            var param = {
                loginName : getusername(),
                password : getpwd()
            };
            $.ajax({
                headers    : {
                    "token" : "",
                    contentType: "application/json"
                },
                url : "/login",
                type : "POST",
                data : param,
                success : function(data){
                    console.log(data);
                    var cdoe = data.code;
                    var msg = data.msg;
                    var data = data.data;
                    if(cdoe != "0000"){
                        alert(msg);
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