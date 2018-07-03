<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">

	<title>Blog post | Initio - Free, multipurpose html5 template by GetTemplate</title>

	<!-- Bootstrap -->
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.no-icons.min.css" rel="stylesheet">
	<!-- Icon font -->
	<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
	<!-- Custom styles -->
	<link rel="stylesheet" href="/BookAssitantSystem/resources/assert/css/styles.css" />
	<link rel="stylesheet" href="/BookAssitantSystem/resources/assert/css/bookSystem.css" />

	<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>

	  <script type="text/javascript" src="/BookAssitantSystem/resources/assert/js/drawFiveStar.js" ></script>

	  	<!-- 引入vue -->
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
</head>
<body>

<!-- Logo与登录界面 -->
<header id="header">
	<div id="head" class="parallax" parallax-speed="1">

		<!--设置新书速递以及自动续借和续借全部书籍-->
		<ul class="setup">
			<li id="openNewBookPush">新书速递</li>
			<li id="openAutoBorrowPush">自动续借</li>
			<li id="openBorrowAllNoPush">续借全部书籍</li>
		</ul>

		<h1 id="logo" class="text-center">Szpt BookSystem</h1>
		<!--搜索条-->
		<div class="searchBar">
			<form action="/bookSystem/searchwithpymongo" method="GET">
				<select name="find_code" id="find_code">
				     <option value="AllKeyButNotCatalog" selected="">所有字段(除目录)</option>
				     <option value="AllKey">所有字段</option>
				     <option value="CatalogKey">目录</option>
				     <option value="BookNameKey">题名关键词</option>
				     <option value="BookAuthorKey">著者</option>
				     <option value="BookPublisherKey">出版社</option>
				     <option value="ISBNKey">ISBN</option>
				     <option value="IndexKey">索书号</option>
				     <option value="SystemNumberKey">系统号</option>
			    </select>
				<input id="searchInput" type="text" name="searchKey" value="{{ searchKey }}" />
				<input type="submit" value="检索" />
				<div id="operate">
					<span id="label">排序：</span>
					<select name="sort" id="sort">
						<option value="Year-Rating-Person" selected="">年/评分/评论人数(降序)</option>
						<option value="Rating-Year-Person">评分/年/评论人数(降序)</option>
						<option value="Person-Year-Rating">评论人数/年/评分(降序)</option>
					</select>
				</div>
			</form>
		</div>

		<!--登录界面,登录之前-->
		<!-- {% if request.session.isLogin %}
		<div class="login topy">
			<p>
				<strong class="vwmy"><a href="" target="_blank" title="访问我的空间">{{request.session.userName}}</a></strong>
				<span class="pipe">|</span><a href="javascript:;" id="myitem" class="showmenu" onmouseover="showMenu({'ctrlid':'myitem'});" initialized="true">我的</a>
				<span class="pipe">|</span><a href="/bookSystem/ttest">设置</a>
				<span class="pipe">|</span><a href="/bookSystem/quitLogin">退出</a>
			</p>
		</div>
		{% else %}
		<form action="/bookSystem/login" method="POST">
			{% csrf_token %}
			<div class="topy login">
				<table cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<td><label for="ls_username">帐号</label></td>
							<td><input type="text" name="username" id="ls_username" class="px vm xg1" value="学号/Email" onfocus="if(this.value == '学号/Email'){this.value = '';this.className = 'px vm';}" onblur="if(this.value == ''){this.value = '学号/Email';}" tabindex="901"></td>
							<td class="fastlg_l"><label for="ls_cookietime"><input type="checkbox" name="cookietime" id="ls_cookietime" class="pc" value="2592000" tabindex="903">自动登录</label></td>
						</tr>
						<tr>
							<td><label for="ls_password">密码</label></td>
							<td><input type="password" name="password" id="ls_password" class="px vm" autocomplete="off" tabindex="902"></td>
							<td class="fastlg_l"><button type="submit" class="pn vm" tabindex="904" style="width: 75px;"><em>登录</em></button></td>
						</tr>
					</tbody>
				</table>
				<input type="hidden" name="quickforward" value="yes">
				<input type="hidden" name="handlekey" value="ls">
			</div>
		</form>
		{% endif %} -->
	</div>

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-collapse collapse">

				<ul class="nav navbar-nav">
					<li><a href="index.html">Home</a></li>
					<li><a href="blog.html">新书速递</a></li>
					<li class="active"><a href="about.html">About</a></li>
				</ul>

			</div>
		</div>
	</nav>
</header>

<main id="main">

	<div class="container">

		<!-- 这里用JSP渲染 -->
		<div class="row topspace mainContent">
			<div class="col-sm-8 col-sm-offset-2">

				<!-- 书的基本信息（书名，作者，出版社，出版年，ISBN,系统号,评分） -->
				<div class="baseInformation">
					<!-- 封面 -->
					<img class="bookImg bookDetailImg" src="assets/images/guy.jpg" />
					<!--基本信息-->
					<table class="bookBaseInformation">
						<tr>
							<td class="bookDetailLabel">书名：</td>
							<td class="">${bookName}</td>
						</tr>
						<tr>
							<td class="bookDetailLabel">作者：</td>
							<td class="">${author}</td>
						</tr>
						<tr>
							<td class="bookDetailLabel">索书号：</td>
							<td class="">${index}</td>
						</tr>
						<tr>
							<td class="bookDetailLabel">出版社：</td>
							<td class="">${publisher}</td>
						</tr>
						<tr>
							<td class="bookDetailLabel">年份：</td>
							<td class="bookDetailContent">${publishYear}</td>
						</tr>
						<tr>
							<td class="bookDetailLabel">馆藏复本:</td>
							<td class="bookISBN ">加载中....</td>
						</tr>
					</table>

					<!--评分界面-->
					<div class="rating">
						<span>评分：</span>
						<c:forEach begin="1" end="5" var="i">
							<canvas id="canvas${ISBN}with${i}" height="20px" width="20px"></canvas>
						</c:forEach>
						<center><p>${ratingAverage<=0 ? '暂无评' : ratingAverage}分</p></center>
						<p>评分人数：${ratingNumberRaters<=0 ? '暂无' : ratingNumberRaters}人</p>
					</div>
				</div>
				<!-- 书籍的内容简介 -->
				<div class="bookContent">
					<legend><span class="bookLabel">内容简介  · · · · · ·</span></legend>
					<div class="shortintro">
						<!--markdown区域-->
						<c:out value="${smallContent}" escapeXml="false"></c:out>
						<a class="watchMore" href="javascript:$('.shortintro').hide();$('.longintro').show();">查看更多</a>
					</div>
					<div class="longintro">
						<!-- 防止xss注入攻击 -->
						<c:out value="${content}" escapeXml="false"></c:out>
						<a class="watchMore" href="javascript:$('.longintro').hide();$('.shortintro').show();">收起</a>
					</div>
				</div>
				<!-- 书籍的目录 -->
				<div class="bookCatalogue">
					<legend><span class="bookLabel">目录  · · · · · ·</span></legend>
					<div class="shortCatalog">
						<!--目录-->
						<c:out value="${smallCatalog}" escapeXml="false"></c:out>
						<a class="watchMore" href="javascript:$('.shortCatalog').hide();$('.longCatalog').show();">查看更多</a>
					</div>
					<div class="longCatalog">
						<!--目录-->
						<c:out value="${catalog}" escapeXml="false"></c:out>
						<a class="watchMore" href="javascript:$('.longCatalog').hide();$('.shortCatalog').show();">收起</a>
					</div>
				</div>
			
			</div>
		</div> <!-- /row post  -->

		<!--
        	同类型书籍推荐部分，此处使用vue渲染
        -->
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">
				<legend><span class="bookLabel">同类型书籍推荐  · · · · · ·</span></legend>
				<div class="bookRecommand">
					<div class="wordWithImg">
						<div class="str">
							asdasd
						</div>
						<img src="assets/images/avatar_man.png" height="150px" width="150px" />
					</div>
				</div>
				<div class="bookRecommand">
					<div class="wordWithImg">
						<div class="str">
							asdasd
						</div>
						<img src="assets/images/avatar_man.png" height="150px" width="150px" />
					</div>
				</div>
							<div class="bookRecommand">
					<div class="wordWithImg">
						<div class="str">
							asdasd
						</div>
						<img src="assets/images/avatar_man.png" height="150px" width="150px" />
					</div>
				</div>
							<div class="bookRecommand">
					<div class="wordWithImg">
						<div class="str">
							asdasd
						</div>
						<img src="assets/images/avatar_man.png" height="150px" width="150px" />
					</div>
				</div>

			</div>
		</div>

		<br/>

		<!-- 评论部分，此处使用vue渲染 -->
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">

				<div id="comments">
					<legend><span class="bookLabel">评论 · · · · · ·</span></legend>
					<span><a href="">文字版</a></span><span class="cleaned">/</span> <span><a href="">词云版</a></span>

					<ol class="comments-list word" id="commentsResult">

						<template v-if="comments.length > 0">
							<template v-for="comment in comments">
								<li class="comment">
									<div>
										<!-- <img src="assets/images/avatar_woman.png" alt="Avatar" class="avatar"> -->
										<div class="comment-meta">
											<!--用户名-->
											<span class="author"><a href="#">{{comment.fromUserID}}</a></span>
											<!--用户评论时间-->
											<span class="date">{{getDate(comment.createTime)}}</span>
										</div><!-- .comment-meta -->
  
									 	<!--评论主体-->
										<div class="comment-body">
											{{comment.comment}}
										</div><!-- .comment-body -->
									</div>
								</li>
								<hr>
							</template>
						</template>
						<template v-else>
							暂无评论
						</template>
					</ol>

					<!--词云部分-->
					<div class="wordCloud">
						<center>
							<img src="assets/images/s1.jpg" height="300px" width="600px" />
						</center>
					</div>
					 
					<!-- 评论分页 -->
					<div class="" id="dividePageComments">
						<center>
							<ul class="pagination margin">
								<li><a href="" onclick="return false;" v-on:click="gotoPageComments(page-1)">&lt;&lt;上一页 </a></li>
								<li class="active"><a href="">{{page}}</a></li>
								<li><a href="" onclick="return false;" v-on:click="gotoPageComments(page+1)">下一页>></a></li>
							</ul>
						</center>
					</div>

					<div class="clearfix"></div>

					<nav id="comment-nav-below" class="comment-navigation clearfix" role="navigation"><div class="nav-content">

					</div></nav><!-- #comment-nav-below -->



					<!--对书籍进行评论-->
					<div id="respond">
						<h3 id="reply-title">Leave a Reply</h3>
						<form action="" method="post" id="commentform" class="">
							<div class="form-group">
								<label for="inputComment">Comment</label>
								<textarea class="form-control" rows="6"></textarea>
							</div>
							<div class="row">
								<div class="col-md-8">

								</div>
								<div class="col-md-4 text-right">
  									<button type="submit" class="btn btn-action">Submit</button>
								</div>
						</form>
					</div> <!-- /respond -->
				</div>
			</div>
		</div> <!-- /row comments -->
		<div class="clearfix"></div>

	</div>	<!-- /container -->

</main>

<footer id="footer" class="topspace">
	<div class="container">
		<div class="row">

			<div class="col-md-3 widget">
				<h3 class="widget-title">Text widget</h3>
				<div class="widget-body">
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Atque, nihil natus explicabo ipsum quia iste aliquid repellat eveniet velit ipsa sunt libero sed aperiam id soluta officia asperiores adipisci maxime!</p>

				</div>
			</div>

			<div class="col-md-3 widget">
				<h3 class="widget-title">Form widget</h3>
				<div class="widget-body">
					<p>+234 23 9873237<br>
						<a href="mailto:#">some.email@somewhere.com</a><br>
						<br>
						234 Hidden Pond Road, Ashland City, TN 37015
					</p>
				</div>
			</div>

		</div> <!-- /row of widgets -->
	</div>
</footer>

</body>
</html>

<!-- 使用vue进行渲染 -->
<script src="/BookAssitantSystem/resources/assert/js/bookCommentRender.js"></script>
<script> 
	// 绘制五角星
	var book = new Object();
	book.ISBN = '${ISBN}';
	book.ratingAverage = ${ratingAverage};
	var arrays = []
	arrays.push(book);
	drawFiveStar(arrays);  
	   
	// 为vue的comment模型提供ISBN
	commentsResult.ISBN = '${ISBN}';
	console.log("为vue的comment模型提供ISBN");
	console.log(commentsResult.ISBN);
	setTimeout(() => {
		init();
	}, 10);
</script>