<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!-- dat biến trung gian voi ten cart voi gia tri la gio hang trong session-->
<c:set var="cart" value="${sessionScope['scopedTarget.cartService']}" /> 
<div class="panel panel-default">
	<div class="panel-heading">Shopping Cart</div>
	<div class="panel-body">
		<img id="cartIMG" alt="" src="/static/images/shoppingcart.gif" class="col-sm-5">
		<ul class="col-sm-7">
			<li><b id="cart-count">${cart.count}</b> mặt hàng</li>
			<li><b id="cart-amount"><f:formatNumber value="${cart.amount}" pattern="#,###.00" /></b> VNĐ</li>
			<li><a href="/cart/view">Xem giỏ hàng</a></li>
		</ul>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">TÌM KIẾM</div>
	<div class="panel-body">
		<form action="/product/list-by-keywords">
			<input name="keywords" class="form-control" placeholder="Tìm kiếm" />
		</form>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">DANH MỤC HÀNG HÓA</div>
	<div class="list-group">
		<c:forEach var="c" items="${cates}">
			<a href="/product/list-by-category/${c.id}" class="list-group-item">${c.nameVN}</a>
		</c:forEach>

	</div>

</div>
<div class="panel panel-default">
	<div class="panel-heading">ĐẶC BIỆT</div>
	<div class="list-group">
		<a href="/product/special/0" class="list-group-item">Sản phẩm mới</a>
		<a href="/product/special/1" class="list-group-item">Sản phẩm bán
			chạy</a> <a href="/product/special/2" class="list-group-item">Sản
			phẩm yêu thích</a> <a href="/product/special/3" class="list-group-item">Sản
			phẩm giảm giá</a>
	</div>
</div>
<style id="cart-css"></style>


