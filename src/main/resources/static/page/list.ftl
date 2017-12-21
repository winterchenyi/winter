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
                            <div class="list_main">
                                <#if (list?exists && list?size>0)>
                                    <#list list as s>
                                        <div class="list_each">
                                            <div class="list_num">
                                                <span>${s_index + 1}</span>
                                            </div>
                                            <div class="pro_num">
                                                <span>${s.loginName}</span>
                                            </div>
                                            <div class="pro_name2">
                                                    <span>${s.userName}</span>
                                            </div>
                                            <div class="pro_day2">
                                                <span>${s.userAge}</span>
                                            </div>
                                            <div class="pro_time2">
                                                <span>${s.cellPhone}</span>
                                            </div>
                                            <div class="pro_day2">
                                                <span>${s.insDate?string('yyyy-MM-dd')}</span>
                                            </div>
                                            <div class="pro_do2">
                                                <div class="do_main do_main2">
                                                    <a href="javascript:void(0)">编辑</a>
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                        <!--分页-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
</script>
</body>
</html>
