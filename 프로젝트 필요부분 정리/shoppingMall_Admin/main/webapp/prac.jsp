<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
$(document).on("change", "select.category1", function(){

    var cate2Arr = new Array();
    var cate2Obj = new Object();
    
    // 2차 분류 셀렉트 박스에 삽입할 데이터 준비
    for(var i = 0; i < jsonData.length; i++) {
     
     if(jsonData[i].level == "2") {
      cate2Obj = new Object();  //초기화
      cate2Obj.cateCode = jsonData[i].cateCode;
      cate2Obj.cateName = jsonData[i].cateName;
      cate2Obj.cateCodeRef = jsonData[i].cateCodeRef;
      
      cate2Arr.push(cate2Obj);
     }
    }
    
    var cate2Select = $("select.category2");
    
    /*
    for(var i = 0; i < cate2Arr.length; i++) {
      cate2Select.append("<option value='" + cate2Arr[i].cateCode + "'>"
           + cate2Arr[i].cateName + "</option>");
    }
    */
    
    cate2Select.children().remove();
 
    $("option:selected", this).each(function(){
     
     var selectVal = $(this).val();    
     cate2Select.append("<option value=''>전체</option>");
     
     for(var i = 0; i < cate2Arr.length; i++) {
      if(selectVal == cate2Arr[i].cateCodeRef) {
       cate2Select.append("<option value='" + cate2Arr[i].cateCode + "'>"
            + cate2Arr[i].cateName + "</option>");
      }
     }
     
    });
    
 });