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

        <div class="col-md-4 column">
            <table class="table table-condensed table-bordered table-hover">
                <thead>
                <tr>
                    <th>订单id</th>
                    <th>订单总金额</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDTO.orderId}</td>
                    <td>${orderDTO.orderAmout}</td>
                </tr>

                </tbody>
            </table>
        </div>

        <#--订单详情表数据-->
        <div class="col-md-12 column">
    <table class="table table-condensed table-bordered table-hover">
        <thead>
        <tr>
            <th>商品id</th>
            <th>商品名称</th>
            <th>价格</th>
            <th>数量</th>
            <th>总额</th>
        </tr>
        </thead>
        <tbody>
        <#list orderDTO.orderDetailList as orderDetail>
            <tr>
                <td>${orderDetail.productId}</td>
                <td>${orderDetail.productName}</td>
                <td>${orderDetail.productPrice}</td>
                <td>${orderDetail.productQuantity}</td>
                <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
            </tr>
        </#list>
        </tbody>
    </table>

</div>
<div class="col-md-12 column">
    <#if orderDTO.getOrderStatusEnum().message == "新订单">
        <a type="button" class="btn btn-success btn-lg " href="/sell/seller/order/finish?orderId=${orderDTO.orderId}">完结订单</a>
        <a type="button" class="btn btn-lg active btn-danger" href="/sell/seller/order/cancle?orderId=${orderDTO.orderId}">取消订单</a>
    </#if>
</div>
</div>
    </div>
</div>
</div>
</body>
</html>