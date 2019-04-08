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
                <div class="col-md-12 column">
                    <table class="table table-condensed table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>类目Id</th>
                            <th>名字</th>
                            <th>type</th>
                            <#--<th>创建时间</th>-->
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list categoryList as category>
                        <tr>
                            <td>${category.categoryId}</td>
                            <td>${category.categoryName}</td>
                            <td>${category.categoryType}</td>
                            <#--<td>${productInfo.createTime}</td>-->
                            <td><a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
                            <#--<td>-->
                                <#--<#if category.getProductStatusEnu m().message == "上架">-->
                                    <#--<a href="/sell/seller/category/off_sale?productId=${category.categoryId}">下架</a>-->
                                <#--<#else>-->
                                    <#--<a href="/sell/seller/category/on_sale?productId=${category.categoryId}">上架</a>-->
                                <#--</#if>-->
                            <#--</td>-->
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>


        </div>
    </div>
</div>

</body>
</html>

