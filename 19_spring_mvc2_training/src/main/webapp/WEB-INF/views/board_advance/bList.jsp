<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>BoardList</title>
<link href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" rel="stylesheet" crossorigin="anonymous" />
<!-- href 특성에 스타일 시트의 경로를, rel 특성에 stylesheet을 사용합니다. rel은 관계(relationship)를 뜻함. 
     crossorigin속성이 존재하면 CORS(cross origin resource share)가 가능해져 다른 origin의 자원을 얻어올 수 있음.
     annoymous : element의 CORS 요청의 credentials flag가 'same-origin'으로 지정-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js" crossorigin="anonymous"></script>
<link href="${contextPath}/resources/css/styles.css" rel="stylesheet" />
<script src="${contextPath}/resources/js/jquery-3.5.1.min.js"></script> <!-- jquery 선언문 -->
<script>

	$().ready(function(){//dom요소들 실행 후 실행되는 함수
		
		$("#onePageViewCount").change(function(){ //한페이지에 보고 싶은 게시물 개수, <select>id가 onepageViewCount인 것이 변경되었을 시 실행되는 함수
			
			var onePageViewCount = $("#onePageViewCount").val();
			var searchKeyword = $("#searchKeyword").val();
			var searchWord = $("#searchWord").val();
			var url = "${contextPath}/boardAdvance/boardList?";
				url	+= "onePageViewCount="+onePageViewCount;
				url	+= "&searchKeyword="+searchKeyword;
				url	+= "&searchWord="+searchWord;
			location.href = url;
			
		}) ;
		
		$("#getSearchBoard").click(function(){ // id가 getSearchBoard를 클릭하였을 때 실행되는 함수
			var onePageViewCount = $("#onePageViewCount").val();
			var searchKeyword = $("#searchKeyword").val();
			var searchWord = $("#searchWord").val();
			var url = "${contextPath}/boardAdvance/boardList?";
				url +="onePageViewCount="+onePageViewCount;
				url += "&searchKeyword="+searchKeyword;
				url += "&searchWord="+searchWord;
			location.href=url;
		});
			
		
	});
	
</script>
</head>
<body class="sb-nav-fixed">
    <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
        <a class="navbar-brand" href="index.html">BoardEx02</a> <!-- index.html 현재 없으므로 오류 -->
    </nav>
    <div id="layoutSidenav">
        <div id="layoutSidenav_nav">
            <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                <div class="sb-sidenav-menu">
                    <div class="nav">
                        <div class="sb-sidenav-menu-heading">Core</div>
                        <a class="nav-link" href="boardList">
                            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div><!-- i태그는 아이콘 삽입 -->
                            BoardList
                        </a>
                  	</div>
                </div>
                <div class="sb-sidenav-footer">
                    <div class="small">sb-amdin</div>
                    With Bootstrap Thema
                </div>
            </nav>
        </div>
        <div id="layoutSidenav_content">
            <main> <!-- <main> 태그는 해당 문서의 <body> 요소의 주 콘텐츠(main content)를 정의할 때 사용합니다./문서 전반에 걸쳐 반복되는 내용을 포함해서는 안 됩니다./나의 문서에는 단 하나의 <main> 요소만이 존재해야 하며, <main> 요소는 <article>, <aside>, <footer>, <header>, <nav> 요소의 자손 요소가 되어서는 안 됩니다. -->
                <div class="container-fluid">
                    <h1 class="mt-4">MVC2_Model_Advance</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"><a href="${contextPath }/boardAdvance/boardList">Dashboard</a></li>
                        <li class="breadcrumb-item active">advance</li>
                    </ol>
                    <div class="card mb-4">
                        <div class="card-body">
                            1) 부트스트랩 적용<br>
                            2) 검색기능 적용<br>
                            3) 페이징 처리 적용 > <a href="${contextPath }/boardAdvance/makeDummyData">테스트용 더미파일 만들기</a><br>
                            4) 계층형 댓글 적용<br>
                        </div>
                    </div>
                    <div class="card mb-4">
                        <div class="card-header">
                            <i class="fas fa-table mr-1"></i>
                            Total <span style="color: red; font-weight: bold">${totalBoardCount}</span> entries
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                            	<div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
                                	<div class="row">
                                		<div class="col-sm-12 col-md-6">
                                			<div class="dataTables_length" id="dataTable_length">
                                				<label>Show 
                                 				<select id="onePageViewCount" name="dataTable_length" aria-controls="dataTable" class="custom-select custom-select-sm form-control form-control-sm">
                                 					<option <c:if test="${onePageViewCount eq 5}"> selected</c:if> value="5">5</option>
													<option <c:if test="${onePageViewCount eq 7}"> selected</c:if> value="7">7</option>
													<option <c:if test="${onePageViewCount eq 10}"> selected</c:if> value="10">10</option>
                                 				</select> entries
                                 				</label>
                                 			</div>		                               
                              			</div>
                              			<div class="col-sm-12 col-md-6">
                              				<input type="button" class="btn btn-primary" style="float: right" value="Write" onclick="location.href='boardWrite'">
                              			</div>
                              		</div>
	                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
	                                    <colgroup>
											<col width="5%">
											<col width="60%">
											<col width="20%">
											<col width="10%">
											<col width="5%">
										</colgroup>
	                                    <thead>                                     
	                                        <tr>
	                                            <th>Order</th>
	                                            <th>Subject</th>
	                                            <th>Writer</th>
	                                            <th>RegDate</th>
	                                            <th>View</th>
	                                        </tr>
	                                    </thead>
	                                    <tbody>
	                                        <c:set var="order" value="${totalBoardCount - (currentPageNumber-1) * onePageViewCount}"/>
	                                        <c:forEach var="bdto" items="${boardList}">	                                        	
												<tr>
													<td><fmt:parseNumber integerOnly="true" value="${order}"/></td>	<c:set var="order" value="${order - 1}"/>
													<td>  <!-- fmt:parseNumber 문자열을 숫자(Number 타입)로 변환해 주는 기능을 제공하는 태그 -->
														 <c:if test="${bdto.reStep > 1}"><!-- 답글에 관한 내용 -->
														 	<c:forEach var="j" begin="0" end="${(bdto.reLevel-1 ) * 5 }">
														 		&nbsp;
														 	</c:forEach>
														 	»
														 </c:if>
														<a href="${contextPath }/boardAdvance/boardInfo?num=${bdto.num}"> ${bdto.subject}</a>
													</td>
													
													<td>${bdto.writer}</td>
													<td><fmt:formatDate value="${bdto.regDate}" pattern="yyyy-MM-dd"/></td>
													<td>${bdto.readCount}</td>
												</tr>
											</c:forEach>
											<tr>
												<td colspan="5" align="center">	
													<select id="searchKeyword" class="form-control" style="width: 150px; display: inline;">
														<option <c:if test="${searchKeyword eq 'total'}"> selected</c:if> value="total">total</option>
														<option <c:if test="${searchKeyword eq 'writer'}"> selected</c:if> value="writer">writer</option>
														<option <c:if test="${searchKeyword eq 'subject'}"> selected</c:if> value="subject">subject</option>
													</select>
			                                 		<input type="text" style="width: 300px; display: inline;" class="form-control" id="searchWord" name="searchWord" value="${searchWord}" >
													<input type="button" class="btn btn-outline-info btn-sm" value="Search" id="getSearchBoard">
												</td>
											</tr>
	                                    </tbody>										
	                                </table>
                                	<div style="display: table; margin-left: auto; margin-right: auto">
                                 	<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate">
                                 		<c:if test="${totalBoardCount gt 0 }"> <!-- totalBoardCount가 0 보다 클때 -->
                                  		<ul class="pagination">
                                  			<c:if test="${startPage gt 10 }"> <!-- startPage가 10보다 클때 --> <!-- *이전 버튼 -->
                                   			<li class="paginate_button page-item previous" id="dataTable_previous">
                                   				<a href="${contextPath }/boardAdvance/boardList?currentPageNumber=${startPage - 10} & onePageViewCount=${onePageViewCount} & searchKeyword=${searchKeyword} & searchWord=${searchWord}" aria-controls="dataTable" data-dt-idx="0" tabindex="0" class="page-link">Previous</a>
                                   			</li>                                                <!-- *currentPageNumber : 현재 페이지 -->
                                  			</c:if>
                                  			<c:forEach var="i" begin="${startPage}" end="${endPage }" > <!-- *숫자버튼 -->
                                   			<li class="paginate_button page-item">
                                   				<a href="${contextPath }/boardAdvance/boardList?currentPageNumber=${i}&onePageViewCount=${onePageViewCount}&searchKeyword=${searchKeyword}&searchWord=${searchWord}" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
                                   			</li>
                                   			</c:forEach>
                                  			<c:if test="${endPage le totalBoardCount && endPage ge 10}">  <!-- endPage가 totalBoardCount보다 작거나 같고 and endPage가 10보다 크거나 같을때 --> <!-- *다음 버튼 -->
                                  			<!-- 예/ startPage = 1 / endPage = 10 -->
                                   			<li class="paginate_button page-item next" id="dataTable_next">
                                   				<a href="${contextPath }/boardAdvance/boardList?currentPageNumber=${startPage + 10}&onePageViewCount=${onePageViewCount}&searchKeyword=${searchKeyword}&searchWord=${searchWord}" aria-controls="dataTable" data-dt-idx="7" tabindex="0" class="page-link">Next</a>
                                   			</li>                                               
                                  			</c:if>
                                  		</ul>
                                  	</c:if>
                                 	</div>
                                 </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </main>
            <footer class="py-4 bg-light mt-auto">
                <div class="container-fluid">
                    <div class="d-flex align-items-center justify-content-between small">
                        <div class="text-muted">Copyright &copy; Your Website 2021</div>
                        <div>
                            <a href=" #">Privacy Policy</a>
                            &middot;
                            <a href="#">Terms &amp; Conditions</a>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    </div>
</body>
</html>