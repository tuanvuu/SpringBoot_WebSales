$(document).ready(
		function() {
			/* cart remove tung item trong gio hang */
			$(".btn-cart-remove").click(function() {
				var id = $(this).closest("tr").attr("data-id");
				$.ajax({
					url : "/cart/remove/" + id,
					success : function(response) {
						$("#cart-count").html(response[0]);
						$("#cart-amount").html(response[1]);
					}

				});
				$(this).closest("tr").remove();

			});
			/* ajax thay doi tang giam so luong item -> price */
			$("tr[data-id] input").on("input", function() {
				var id = $(this).closest("tr").attr("data-id");
				var price = $(this).closest("tr").attr("data-price");
				var discount = $(this).closest("tr").attr("data-discount");
				var qty = $(this).val();
				$.ajax({
					url : `/cart/update/${id}/${qty}`,
					success : function(response) {
						$("#cart-count").html(response[0]);
						$("#cart-amount").html(response[1]);
					}

				});
				var amt = qty*price*(1-discount);
				$(this).closest("tr").find("td.amt").html(amt);

			});

			/* ajax clear gio hang */
			$(".btn-cart-clear").click(function() {
				$.ajax({
					url : "/cart/clear",
					success : function(response) {
						$("#cart-count").html(0);
						$("#cart-amount").html(0);
						$("table>tbody").html("");
					}

				});

			});

			$(".btn-star").click(function() {
				var id = $(this).closest("div").attr("data-id");
				$.ajax({
					url : "/product/add-to-favo/" + id,
					success : function(response) {
						if (response) {
							alert("da them thanh cong")
						}

						else {
							alert("san pham da o san")
						}
					}
				});
			});

			$(".btn-open-dialog").click(function() {
				var id = $(this).closest("div").attr("data-id");
				$("#myModal #id").val(id);
			});

			$(".btn-send-mail-to-friend").click(function() {
				/* object dang json chưa 3 thuoc tinh id khai báo trong form */
				var form = {
					id : $("#myModal #id").val(),
					to : $("#myModal #email").val(),
					body : $("#myModal #comments").val(),
					from : $("#myModal #from").val()
				}

				$.ajax({
					url : "/product/send-to-friend",
					data : form,
					success : function(response) {
						if (response) {
							$("[data-dismiss]").click(); /*
															 * giả lập để click
															 * đóng modal form
															 * lại
															 */
							alert("Đã gửi thành công")
						} else {
							alert("Lỗi gửi email thất bại")
						}
					}

				});

			});

			$(".btn-add-to-cart").click(
					function() {
						var id = $(this).closest("div").attr("data-id");
						$.ajax({
							url : "/cart/add/" + id,
							success : function(response) {
								$("#cart-count").html(response[0]);
								$("#cart-amount").html(response[1]);
							}

						});

						var img = $(this).closest(".thumbnail").find("a>img");
						var options = {
							to : '#cartIMG',
							className : 'cart-fly'
						}
						var cart_css = '.cart-fly{background-image: url("'
								+ img.attr("src")
								+ '");background-size: 100% 100%;}';
						$("style#cart-css").html(cart_css);
						img.effect('transfer', options, 1000);
					});

		});