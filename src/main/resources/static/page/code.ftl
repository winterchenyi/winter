<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>验证码</title>
    <link rel="stylesheet" href="/static/css/index.css">
</head>
<body>
<header>
    <div class="header-line"></div>
</header>
<div class="content">
        <div class="col-sm-8">
            <input type="text" id="code" name="code" class="form-control" style="width:250px;"/>
            <img id="imgObj" alt="验证码" src="/validateCode" onclick="changeImg()"/>
            <a href="javascript(0);" onclick="changeImg()">换一张</a>
        </div>
</div>
<script src="/static/js/plugin/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">

    // 刷新图片
    function changeImg() {
        var imgSrc = $("#imgObj");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", changeUrl(src));
    }
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
    function changeUrl(url) {
        var timestamp = (new Date()).valueOf();
        var index = url.indexOf("?",url);
        if (index > 0) {
            url = url.substring(0, url.indexOf(url, "?"));
        }
        if ((url.indexOf("&") >= 0)) {
            url = url + "×tamp=" + timestamp;
        } else {
            url = url + "?timestamp=" + timestamp;
        }
        return url;
    }

</script>
</body>
</html>