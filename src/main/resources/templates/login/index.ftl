<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftl">
    <#--主要内容区域-->

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-8 column">
                    <h2>欢迎登录</h2>
                    <form role="form" action="/sell/seller/login" method="geti" >
                        <div class="form-group">
                            <label >openid</label>
                            <input name="openid" type="text" class="form-control" />
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

