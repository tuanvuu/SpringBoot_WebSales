<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<div class="row">
	<div class="col-sm-5 text-center">
		<img src="/static/images/products/${prod.image}" class="detail-img">
	</div>
	<div class="col-sm-7">
		<ul class="detail-info">
			<li>Name :${prod.name}</li>
			<li>UnitPrice :<f:formatNumber value="${prod.unitPrice}"
					pattern="#,###.00" /></li>
			<li>Product Date : <f:formatDate value="${prod.productDate}"
					pattern="dd-MM-yyyy" /></li>
			<li>Category : ${prod.category.nameVN}</li>
			<li>Quantity : ${prod.quantity}</li>
			<li>Discount : <f:formatNumber value="${prod.discount}"
					type="percent" /></li>
			<li>View Count : ${prod.viewCount}</li>
			<li>Available :${prod.available?'YES':'NO'}</li>
			<li>Special :${prod.special?'YES':'NO'}</li>

		</ul>


	</div>
	<div class="text-justify">${prod.description}</div>
</div>

<ul class="nav nav-tabs">
	<li class="active"><a data-toggle="tab" href="#tab1">Sản phẩm
			cùng loại</a></li>
	<li><a data-toggle="tab" href="#tab2">Sản phẩm yêu thích</a></li>
	<li><a data-toggle="tab" href="#tab3">Sản phẩm đã xem</a></li>
</ul>

<div class="tab-content">
	<div id="tab1" class="tab-pane fade in active">
		<div>
			<c:forEach var="p" items="${list}">
				<a href="/product/detail/${p.id}"> <img
					src="/static/images/products/${p.image}" class="thumg-img" />
				</a>
			</c:forEach>
		</div>
	</div>
	<div id="tab2" class="tab-pane fade">
		<div>
			<c:forEach var="k" items="${favo}">
				<a href="/product/detail/${k.id}"> <img
					src="/static/images/products/${k.image}" class="thumg-img" />
				</a>
			</c:forEach>
		</div>
	</div>
	<div id="tab3" class="tab-pane fade">
		<div>
			<c:forEach var="k" items="${view_list}">
				<a href="/product/detail/${k.id}"> <img
					src="/static/images/products/${k.image}" class="thumg-img" />
				</a>
			</c:forEach>
		</div>
	</div>
</div>