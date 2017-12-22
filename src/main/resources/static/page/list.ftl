<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>列表</title>
    <link rel="stylesheet" href="/static/css/list.css">
</head>
<body>
<div class="main aside_nav_item_box">
    <div class="container-fluid" id="container">
        <div class="main_box">
            <div class="nav_item_tit">
                账户总览
            </div>
            <div class="nav_item_box">
                <div class='tab-content'>
                    <div class='tab-pane active'>
                        <div class="list_table">
                            <div class="col_box">
                                <div class="list_num list_num_info"></div>
                                <div class="pro_num list_num_info pro_num2">登录名/账号</div>
                                <div class="pro_name2 list_num_info">用户名</div>
                                <div class="pro_day2 list_num_info">用户年龄</div>
                                <div class="pro_time2 list_num_info">手机号</div>
                                <div class="pro_day2 list_num_info">创建时间</div>
                                <div class="pro_do2 list_num_info"></div>
                            </div>
                            <div class="list_main"  id="test">
                                    <div class="list_each">
                                        <#--<div class="list_num">-->
                                            <#--<span id="s_index"></span>-->
                                        <#--</div>-->
                                        <#--<div class="pro_num">-->
                                            <#--<span id="s_loginName"></span>-->
                                        <#--</div>-->
                                        <#--<div class="pro_name2">-->
                                            <#--<span id="s_userName"></span>-->
                                        <#--</div>-->
                                        <#--<div class="pro_day2">-->
                                            <#--<span id="s_userAge"></span>-->
                                        <#--</div>-->
                                        <#--<div class="pro_time2">-->
                                            <#--<span id="sex"></span>-->
                                        <#--</div>-->
                                        <#--<div class="pro_day2">-->
                                            <#--<span id="cellPhone"></span>-->
                                        <#--</div>-->
                                        <#--<div class="pro_do2">-->
                                            <#--<div class="do_main do_main2">-->
                                                <#--<a href="javascript:void(0)">编辑</a>-->
                                            <#--</div>-->
                                        <#--</div>-->
                                    </div>
                            </div>
                        </div>
                        <!--分页-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/static/js/plugin/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">
    $(function(){
        $.ajax({
            headers    : {
                "token" : "",
                contentType: "application/json"
            },
            url : "/acconut/list",
            type : "POST",
            success : function(XMLHttpRequest){
                console.log(XMLHttpRequest);
                var cdoe = XMLHttpRequest.code;
                var msg = XMLHttpRequest.msg;
                var data = XMLHttpRequest.data;
                if(cdoe != "0000"){
                    alert(msg);
                }else{
                    var list = data.list;
                    console.log(list);
                    var html = "";
                    for (var i = 0; i < list.length; i++) {
                    html = html + "<div class='list_each'><div class='list_num'><span id='s_index'>"+ i +"</span></div>" +
                        "<div class='pro_num'><span id='loginName'>"+list[i].loginName+"</span></div>" +
                        "<div class='pro_name2'><span id='userName'>"+list[i].userName+"</span></div>" +
                        "<div class='pro_day2'><span id='userAge'>"+list[i].userAge+"</span></div>" +
                        "<div class='pro_time2'><span id='sex'>"+list[i].sex+"</span></div>" +
                        "<div class='pro_day2'><span id='cellPhone'>"+list[i].cellPhone+"</span></div>" +
                        "<div class='pro_do2'><div class='do_main do_main2'><a href='javascript:void(0)'>编辑</a></div></div></div>";
                    }
                    $("#test").html(html);
                }
            }
        })
    });
</script>
</body>
</html>
