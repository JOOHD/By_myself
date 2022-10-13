<script>
var item_no = ${itemContent.item_no};

	$(document)
			.ready(
					function() {
						var target = document.getElementById("item_optiont");
						$("#cartInsert").on("click",function() {
											var item_optionValue = target.options[target.selectedIndex].value;
											var item_optionContent = target.options[target.selectedIndex].text;
											$
													.ajax({
														type : "POST",
														url : "/main/cart",
														dataType : "json",
														data : {
															'cart_option_no' : item_optionValue,
															'cart_option_content' : item_optionContent,
															'cart_item_no' : item_no
														},
														error : function(
																request,
																status, error) {
															alert("code:"
																	+ request.status
																	+ "\n"
																	+ "message:"
																	+ request.responseText
																	+ "\n"
																	+ "error:"
																	+ error);
														},
														success : function(data) {

															if (data == 1) {
																cartHeaderView();
																toastr.options.preventDuplicates = true;

																toastr
																		.success("장바구니 추가완료");
															} else if (data == 2) {
																toastr.options.preventDuplicates = true;

																toastr
																		.warning("이미 추가 된 상품입니다");
															}

														}
													});
										})
</script>

<!--
    장바구니 버튼을 눌렀을때, onclike 이벤트로 해당 script 함수에 들어온다.
    arget.option을 사용해서 select option의 text값과 value값 ( 옵션 Content와 옵션 no ) 을 가져온다.
    그리고 상품 내용페이지를 구성하는 select문의 결과에서 상품no를 가져온다.
    그 후 ajax로 3개의 데이터를 컨트롤러로 보내준다.
    컨트롤러에서 반환되는 값이 1이라면 추가완료,
    2라면 중복이다.
-->

<script>
$(document).ready(function(){
	cartHeaderView();

	
	})

function cartHeaderView(){
	
	  $.ajax({
		  url : "/main/cartHeaderView",
		  type : "get",
		  dataType : "json",
		  success : function(list){
		  var s ='';
		  var headersum = 0 ;
		  if(list<1){
			  s += '<div style="text-align:center"><h3>쇼핑백이 비었습니다</h3></div>' ;
		  }
		  
		  $.each(list, function(key,value){
			  var item_price = parseInt(value.itemVO.item_price);
			  var price = new Intl.NumberFormat('ko-KR', {
				  style : 'currency',
				  currency : 'KRW'
			  }).format(item_price);
			  
			  var cart_no = parseInt(value.cart_no);
			  
			  s +=	'<tr> <td style="width:18%"> <a href="/main/itemContent?item_no='+value.itemVO.item_no+'"><img src="/img/'+value.itemVO.item_imgmain+'"></a></td>';
			  s +=	'<td style="vertical-align : bottom; font-size:13px;"><a href="/main/itemContent?item_no='+value.itemVO.item_no+'">'+value.itemVO.item_name+'<p>'+value.optionVO.option_content+'</p></a>';
			  s +=	'<a onclick="cartHeaderDel('+cart_no+');" style="font-size:6px" href="#"><u>삭제하기</u></a> </td>';
			  s += '<td style="width:20%;vertical-align : middle; font-size:13px;">'+price+'</td> </tr>';
			  
			  headersum = headersum + parseInt(item_price);
		  });
		  
		  var cartTotal = new Intl.NumberFormat('ko-KR', {
			  style : 'currency',
			  currency : 'KRW'
		  }).format(headersum);
		  
		  
		  $("#cartPrice").html(cartTotal);
		  $("#cartView").html(s);
		  
		  }
	  })
	  
	  
}
</script>

<!-- 삭제 부분의 함수 -->
<script>
function cartHeaderDel(cart_no){
	 
	$.ajax({
		url : "/main/cartDelete",
		type : "post",
		dataType : "json",
		data : {"cart_no" : cart_no},
		success : function(data){
			cartHeaderView();
			toastr.options.preventDuplicates = true;
			toastr.success("삭제완료");
		}
	})
}
</script>

<script>
	$(document).ready(function(){
		
	itemTotal();
	
	});
	 function cartDelete(cart_no){
		 
		$.ajax({
			type : "POST",
			url : "/main/cartDelete",
			dataType : "json",
			data : {"cart_no" : cart_no},
			error : function(request, status, error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){
				if(data == 1){
					alert("삭제완료");
					location.reload();
				}
			}
		
		});
	 }
		
		 function cartDeleteAll(){
			
			 
			 $.ajax({
					type : "POST",
					url : "/main/cartDeleteAll",
					dataType : "json",
					error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					},
					success : function(data){
						if(data == 1){
							alert("삭제완료");
							location.reload();
						}
					}
				
				});
	 }
		 
		 
		 function orderBtn(){
			 console.log("들어옴?");
			if(${member == null }){
				toastr.options.preventDuplicates = true;
				toastr.warning("주문하시려면 로그인을 해주세요");
				return false;
			}
			 
			 var optionVol = $('input[name="optionVol"]');
			 var sum = 0;
			var count = optionVol.length;
			console.log(count);
			var optionMax= $('input[name="select_vol"]'); 
			for(var i = 0; i < count; i++){
				sum = parseInt(optionVol[i].value);
				summ = parseInt(optionMax[i].value);
				console.log(summ);
				if(sum == 0){
					toastr.options.preventDuplicates = true;
					toastr.warning("품절상품은 주문하실 수 없습니다");
					return false;
				}else if(sum < summ){
					toastr.options.preventDuplicates = true;
					toastr.warning("최대수량을 넘겨서 주문하실 수 없습니다");
					return false;
					
				}
				
			}
	
			
			
			 var formObj = $("form[name='orderForm']");
			 formObj.attr("action", "/main/orderView");
			 formObj.attr("method", "post");
			 formObj.submit();
			 
		 }
		 
		 
		 
	function itemTotal() {
		console.log("토탈들어옴?");
		var itemPrice = $('input[name="itemPrice"]');
		var selectvol = $('input[name="select_vol"]');
		var sum = 0;
		var count = itemPrice.length;
		var itemvol = 0;
	
		for (var i = 0; i < count; i++){
			
			sum += parseInt(itemPrice[i].value) * parseInt(selectvol[i].value);
			itemvol += parseInt(selectvol[i].value);
		}
		var summ = new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(sum);
		$("#itemTotalPrice").html(summ);
		$("#itemTotalVol").html(itemvol);
		$("#totalPrice").val(sum);
		$("#totalVol").val(itemvol);
	}
	</script>

	script는 4가지 function이 있다.
	장바구니 삭제, 전체삭제, 주문, 그리고 가격을 합산해주는 부분이다.
	
	먼저 총액을 만들어주는 itemTotal()이다.
	cart테이블을 select해서 나오는 List이다.
	그 List를 foreach로 뷰에 출력할때, input hidden으로 itemPrice와 select_vol이라는 두개의 input태그를 만들었다.
	여러개의 List가 출력되는데, 하나의 input태그 name값을 가지고 있다.
	때문에 해당 input태그는 배열로 출력되는데, var count에 itemPrice의 length를 담는다.
	배열의 길이만큼 for문을 돌려서 가격 (itemPrice) * 수량(select_vol) 을 sum += 해준다
	그 값을 div의 id값에 맞춰서 보내주면 된다.

	<script>
		
		<!-- 동기 -->
	function cartDelete(cart_no){
		
		$.ajax({
			type : "POST",
			url : "/main/cartDelete",
			dataType : "json",
			data : {"cart_no" : cart_no},
			error : function(request, status, error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			},
			success : function(data){
				if(data == 1){
					alert("삭제완료");
					location.reload();
				}
			}
		
		});
	}
		
		function cartDeleteAll(){
			
			
			$.ajax({
					type : "POST",
					url : "/main/cartDeleteAll",
					dataType : "json",
					error : function(request, status, error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					},
					success : function(data){
						if(data == 1){
							alert("삭제완료");
							location.reload();
						}
					}
				
				});
	}
	
	
	
	<!-- 비동기 -->
	
	function cartHeaderDel(cart_no){
		
			$.ajax({
				url : "/main/cartDelete",
				type : "post",
				dataType : "json",
				data : {"cart_no" : cart_no},
				success : function(data){
					cartHeaderView();
					toastr.options.preventDuplicates = true;
					toastr.success("삭제완료");
				}
			})
	}
	
	</script>

	
	$(document).ready(function() {

		
$("#fromBtn").on("click", function(){

	var name = document.getElementById('order_to_name').value; 
	var tel =  document.getElementById('order_to_tel').value; 
	var email =  document.getElementById('order_to_email').value; 
	var post =  document.getElementById('order_to_post').value; 
	var adr =  document.getElementById('order_to_adr').value; 

	$("#order_from_name").val(name);
	$("#order_from_tel").val(tel);
	$("#order_from_email").val(email);
	$("#order_from_post").val(post);
	$("#order_from_adr").val(adr);

	
});

total();
});


<!-- 결제 부분 	
	결제 부분은 총 4개의 script 함수가 있다.

	- 결제금액을 계산해주는 total()
	- 입력칸 유효성검사 orderBtn()
	- 결제api iamport()
	- submit을 보낼 orderInsert() 이다.
	-->

function total() {
		console.log("total실행됨");
		var coupon = document.getElementById("order_coupon");
		var point = document.getElementById("order_point");
		
		var totalPrice = ${totalPrice};

		var order_coupon = coupon.options[coupon.selectedIndex].value;
		var order_coupon_name = coupon.options[coupon.selectedIndex].text;
		var order_point = point.value;
		var mem_point = ${member.MEM_POINT};
        var payment = 0;
		
		if(parseInt(mem_point) < parseInt(order_point)){
			$("#order_point").val(0);
			return false;
		}
		var discount = (parseInt(totalPrice) * (parseInt(order_coupon) * 0.01))

		payment = parseInt(totalPrice) - discount;
		payment = payment - parseInt(order_point) +2500;

		var sum = new Intl.NumberFormat('ko-KR', {
			style : 'currency',
			currency : 'KRW'
		}).format(payment);
		
		var discountSum = new Intl.NumberFormat('ko-KR', {
			style : 'currency',
			currency : 'KRW'
		}).format(discount);
		
		$("#payment").html(sum);
		$("#discount").html(discountSum);
		$("#order_cpn_name").val(order_coupon_name);
		$("#ordered_cpn_disc").val(parseInt(discount));
		$("#ordered_orderprice").val(parseInt(payment));
		
		

	}
	
	function orderBtn(){
		
		var email_rule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		var tel_rule = /^\d{2,3}\d{3,4}\d{4}$/;

		if ($("#order_to_name").val() == null || $("#order_to_name").val() == "") {
			toastr.options.preventDuplicates = true;
			toastr.warning("구매자 명을 입력해주세요");
			$("#order_to_name").focus();

			return false;
		}

		if ($("#order_to_tel").val() == null || $("#order_to_tel").val() == "") {
			
			toastr.options.preventDuplicates = true;
			toastr.warning("구매자번호를 입력해주세요.");
			$("#order_to_tel").focus();

			return false;
		}

		if ($("#order_to_email").val() == null || $("#order_to_email").val() == "") {
			toastr.options.preventDuplicates = true;
			toastr.warning("구매자이메일을 입력해주세요.");
			$("#order_to_email").focus();

			return false;
		}

		if ($("#order_to_adr").val() == null || $("#order_to_adr").val() == "") {

			toastr.options.preventDuplicates = true;
			toastr.warning("구매자주소를 입력해주세요.");
			$("#order_to_adr").focus();

			return false;
		}

		if ($("#order_from_name").val() == null || $("#order_from_name").val() == "") {
			toastr.options.preventDuplicates = true;
			toastr.warning("수취인명을 입력해주세요.");
			$("#order_from_name").focus();

			return false;
		}

		if ($("#order_from_tel").val() == null || $("#order_from_tel").val() == "") {
			toastr.options.preventDuplicates = true;
			toastr.warning("수취인번호를 입력해주세요.");
			$("#order_from_tel").focus();

			return false;
		}


		if ($("#order_from_adr").val() == null || $("#order_from_adr").val() == "") {
			toastr.options.preventDuplicates = true;
			toastr.warning("수취인 주소를 입력해주세요.");
			$("#order_from_adr").focus();

			return false;
		}

		if ($("#order_from_post").val() == null || $("#order_from_post").val() == "") {
			toastr.options.preventDuplicates = true;
			toastr.warning("수취인 우편번호를 입력해주세요.");
			$("#order_from_post").focus();

			return false;
		}

		if ($("#order_to_post").val() == null || $("#order_to_post").val() == "") {
			toastr.options.preventDuplicates = true;
			toastr.warning("구매자 우편번호를 입력해주세요.");
			$("#order_to_post").focus();

			return false;
		}
		iamport();
	}
	
	
	function iamport(){
		
		var name = document.getElementById('order_to_name').value; 
		var tel =  document.getElementById('order_to_tel').value; 
		var email =  document.getElementById('order_to_email').value; 
		var post =  document.getElementById('order_to_post').value; 
		var adr =  document.getElementById('order_to_adr').value;  
		console.log("totalPay11=" + payment);
		
		IMP.init('----');
		IMP.request_pay({
		    pg : 'html5_inicis',
		    pay_method : 'card',
		    merchant_uid : new Date().getTime() ,
		    name : 'Homme 결제' ,
		    amount : parseInt(payment), 
		    buyer_email : email,
		    buyer_name : name,
		    buyer_tel : tel,
		    buyer_addr : adr,
		    buyer_postcode : post
		}, function(rsp) {
			console.log(rsp);
		    if ( rsp.success ) {
		    	var msg = '결제가 완료되었습니다.';
		        msg += '고유ID : ' + rsp.imp_uid;
		        msg += '상점 거래ID : ' + rsp.merchant_uid;
		        msg += '결제 금액 : ' + rsp.paid_amount;
		        msg += '카드 승인번호 : ' + rsp.apply_num;
				
				$("#merchant_uid").val(parseInt(rsp.merchant_uid));

		        orderInsert();
		    } else {
		    	 var msg = '결제에 실패하였습니다.';
		         msg += '//' + rsp.error_msg;
		    }
		    alert(msg);
		});
	}
	
	function orderInsert(){
		
		var formObj = $("form[name='orderForm']");


			formObj.attr("action", "/main/orderInsert");
			formObj.attr("method", "post");
			formObj.submit();
		
	}
</script>