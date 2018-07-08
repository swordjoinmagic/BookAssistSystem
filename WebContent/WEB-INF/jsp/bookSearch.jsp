<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
 
	<title>Search[Search_Key=]|Book System</title>
 
	<link rel="shortcut icon" href="assets/images/gt_favicon.png">
	<!-- Bootstrap -->
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.no-icons.min.css" rel="stylesheet">
	<!-- Icon font -->
	<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
	<!-- Fonts -->
	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Alice|Open+Sans:400,300,700">
	<!-- Custom styles -->
	<!-- <link rel="stylesheet" href="{% static 'bookRecommand/css/styles.css' %}"> -->
	<!-- <link rel="stylesheet" href="{% static 'bookRecommand/css/bookSystem.css' %}" /> -->
	
	<link rel="stylesheet" href="/BookAssitantSystem/resources/assert/css/styles.css" >
	<link rel="stylesheet" href="/BookAssitantSystem/resources/assert/css/bookSystem.css">

	<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
	<!-- 引入vue -->
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
	<script src="/BookAssitantSystem/resources/assert/js/myEncryption.js"></script>
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
		<div class="searchBar" id="searchInputModel">
			<form action="" method="GET">
				<select name="searchType" id="find_code" v-model="searchType">
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
				<input id="searchInput" type="text" name="queryContent" v-model="queryContent" value="${extraQuery}" />
				<input type="submit" value="检索"  href="javascript:void(0);" v-on:click="gotoPage(0);return false;" onclick="return false;" />
				<div id="operate">
					<span id="label">排序：</span>
					<select name="sortType" id="sort" v-model="sortType">
						<option value="0" selected="">年/评分/评论人数(降序)</option>
						<option value="1">评分/年/评论人数(降序)</option>
						<option value="2">评论人数/年/评分(降序)</option>
					</select>
				</div>
			</form>
		</div>

		<!--登录界面-->
		<c:if test="${sessionScope.isLogin}">
		<div class="login topy">
			<p>
				<strong class="vwmy"><a href="" target="_blank">${sessionScope.userName}</a></strong>
				<span class="pipe">|</span><a href="/BookAssitantSystem/setup">设置</a>
				<span class="pipe">|</span><a href="/BookAssitantSystem/login/quit">退出</a>
			</p>
		</div> 
		</c:if>
		<c:if test="${empty sessionScope.isLogin || sessionScope.isLogin==false}">
		<form action="/BookAssitantSystem/login" method="POST">
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
			</div>
		</form>
		</c:if>
	</div>

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-collapse collapse">

				<ul class="nav navbar-nav">
					<li><a href="/BookAssitantSystem/bookSearch">Home</a></li>
					<li><a href="blog.html">新书速递</a></li>
					<li class="active"><a href="about.html">About</a></li>
				</ul>

			</div>
		</div>
	</nav>
</header>

<script type="text/javascript" src="/BookAssitantSystem/resources/assert/js/drawFiveStar.js"></script>
  
<!-- 主显示区域  -->
<main id="main">
	<div class="container">

		<div class="row topspace">
				<!--分页标签-->
				<div class="dividePage" id="dividePage1">
					<legend>
						本次搜索一共有{{totalCount}}条记录，当前页数为{{page}},总页数为{{ totalPage }},搜索上限为2000条记录
					</legend>
					<legend>
						<center class="">
							<ul class="pagination">
								<template v-if="page <= 0">
									<li><a href="" onclick="return false;">«</a></li>
								</template>
								<template v-else>
									<li><a href="" v-on:click="gotoPage(page-1)" onclick="return false;">«</a></li>
								</template>

								<template v-if="page-5>0">
									<li><a href="" v-on:click="gotoPage(0)" onclick="return false;">0</a></li>
									<li><a href="" onclick="return false;">.......</a></li>
								</template>

								<template v-for="p in range">
									<template v-if="p == page">
										<li class="active"><a href="" onclick="return false;">{{ p }}</a></li>
									</template>
									<template v-else>
										<li><a href="" v-on:click="gotoPage(p)" onclick="return false;">{{ p }}</a></li>
									</template>
								</template>

								<template v-if="page+5 < totalPage">
									<li><a href="" onclick="return false;">.......</a></li>
								</template>

								<li><a href="" v-on:click="gotoPage(totalPage)" onclick="return false;">{{totalPage}}</a></li>

								<template v-if="page>=totalPage">
									<li><a href="" onclick="return false;">»</a></li>
								</template>
								<template v-else>
									<li><a href="" v-on:click="gotoPage(page+1)" onclick="return false;">»</a></li>
								</template>
							</ul>
						</center>
					</legend>
				</div>

			<!-- 搜索结果 -->
			<div class="searchResult" id="searchResult">
				<template v-if="isLoading">
					<p>正在加载~~~~</p>
				</template>
				<template v-else>
					<template  v-if="data.document.bookList.length > 0">
						<template v-for="book in data.document.bookList">
							<article class="book">
								<div class="bookDetail">
									<div class="bookDetailTitle">
										<div class="bookImg">
											<!-- <img height="150px" src="{% static 'bookRecommand/images/avatar_man.png' %}" /> -->
										</div>
										<legend>
											<a v-bind:href="getBookUrl(book.ISBN)">{{ book.bookName }}</a>
											<br/>
											<template v-for="i in 5">
												<canvas v-bind:id="'canvas'+book.ISBN+'with'+i" height="17px" width="17px"></canvas>
											</template>
											<span class="ratingScore">评分：{{ book.ratingAverage }}</span>
											<span class="ratingScore">评分人数:{{ book.ratingNumberRaters }}</span>
										</legend>

									</div>
									<table class="bookDetailTable">
										<tr>
											<td class="bookDetailLabel">作者：</td>
											<td class="bookDetailContent">{{ book.author }}</td>
											<td class="bookDetailLabel">索书号：</td>
											<td class="bookDetailContent">{{ book.index }}</td>
										</tr>
										<tr>
											<td class="bookDetailLabel">出版社：</td>
											<td class="bookDetailContent">{{ book.publisher }}</td>
											<td class="bookDetailLabel">年份：</td>
											<td class="bookDetailContent">{{ book.publishYear }}</td>
										</tr>
									</table>
									<br>
									<table class="bookCollectionTable">
										<tr>
											<td class="bookDetailLabel">馆藏复本:</td>
											<td class="bookISBN bookDetailContent" v-bind:id="'ExpandButton'+book.ISBN"><a style="color: blue" v-on:click="getBookCollectionStatusWithVue(book,book.systemNumber)">展开↓</a></td>
											<td style="display: none" class="bookISBN bookDetailContent" v-bind:id="'CollapseButton'+book.ISBN"><a style="color: blue" v-on:click="collapse(book)">收起↑</a></td>
										</tr>
									</table>
									<div v-bind:id="'bookCollectionStatus'+book.ISBN" style="display: none">
										<table class="bookCollectionTable">
												<tr>
													<td class="labelCollection"></td>
													<td class="labelCollection">西丽湖校区</td>
													<td class="labelCollection">在架上：{{book.remainDataXiLi.remain['free']}}</td>
													<td class="labelCollection">已借出：{{book.remainDataXiLi.remain['Lent']}}</td>
												</tr>
												<tr>
													<td class="labelCollection"></td>
													<td class="labelCollection">留仙洞校区</td>
													<td class="labelCollection">在架上：{{book.remainDataLiuXian.remain['free']}}</td>
													<td class="labelCollection">已借出：{{book.remainDataLiuXian.remain['Lent']}}</td>
												</tr>
										</table>
									</div>
								</div>
							</article>
							<br/>
						</template>
					</template>
					<template v-else>
						暂无图书
					</template>
				</template>
		</div>
		<!-- 右侧边栏 -->
		<aside class="col-md-4 sidebar sidebar-left right">

			<!-- 缩小查询范围 -->
			<div class="widget queryAssistance">
				<fieldset class="C">
					<legend class="B">缩小查询范围</legend>
					<!-- 本次查询出现次数最多的十个年份 ,按大到小的顺序排列 ,可伸缩-->
					<span>年份:</span>
					<ul>
						<template v-if="data.document.years.length > 0">
							<template v-for="year in data.document.years">
								<li><a href="">{{year}}</a></li>
							</template>
						</template>
						<template v-else>
							<li><a href="">暂无年份可缩小</a></li>
						</template>
					</ul>
					<span>分类:</span>
					<ul>
						<li><a href="">程序设计Tp312</a></li>
						<li><a href="">算法语言Tp312.1</a></li>
					</ul>
				</fieldset>
			</div>

			<!-- 常用标签栏 -->
			<div class="widget commonLabels">
				<fieldset class="C">
					<legend class="B">常用标签栏</legend>
					<span style="font-size: 28px;"><a href="">Unity</a></span>
					<span style="font-size: 20px;"><a href="">Python</a></span>
					<span style="font-size: 32px;"><a href="">Java</a></span>
					<span style="font-size: 18px;"><a href="">算法</a></span>
					<span style="font-size: 28px;"><a href="">Unity</a></span>
					<span style="font-size: 20px;"><a href="">Python</a></span>
					<span style="font-size: 32px;"><a href="">Java</a></span>
					<span style="font-size: 18px;"><a href="">算法</a></span>
				</fieldset>
			</div>


			<!-- 本次查询中点击率最高的一本书 -->
			<div class="widget bestSearch">
				<fieldset class="C">
					<legend class="B">本次查询点击率最高的书籍：</legend>
					<div class="recommandImg">
						<p>此处预留图片</p>
					</div>
					<span>书名:<a href="">游戏人工智能</a></span>
					<br/>
					<span>作者:xxxx</span>
					<br/>
					<span>点击量:xxxx</span>
					<br/>
				</fieldset>
			</div>
		</aside>
	</div>
	<!--分页标签-->
	<div class="dividePage" id="dividePage2">
		<legend>
			<center class="">
				<ul class="pagination">
					<template v-if="page <= 0">
						<li><a href="">«</a></li>
					</template>
					<template v-else>
						<li><a href="">«</a></li>
					</template>

					<template v-if="page-5>0">
						<li><a href="">0</a></li>
						<li><a>.......</a></li>
					</template>

					<template v-for="p in range">
						<template v-if="p == page">
							<li class="active"><a href="">{{ p }}</a></li>
						</template>
						<template v-else>
							<li><a href="">{{ p }}</a></li>
						</template>
					</template>

					<template v-if="page+5 < totalPage">
						<li><a>.......</a></li>
					</template>

					<li><a href="">{{totalPage}}</a></li>

					<template v-if="page>=totalPage">
						<li><a href="">»</a></li>
					</template>
					<template v-else>
						<li><a href="">»</a></li>
					</template>
				</ul>
			</center>
		</legend>
	</div>
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


<!-- 使用VUE渲染该页面 -->
<script src="/BookAssitantSystem/resources/assert/js/bookSearchRender.js"></script>
<script>
	updateSearchResult(${extraPage},${extraSortType},'${extraSearchType}',encodeURI('${extraQuery}'));
</script>