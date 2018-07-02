<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	
	<link rel="stylesheet" href="assert/css/styles.css" >
	<link rel="stylesheet" href="assert/css/bookSystem.css">

	<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
	<!-- å¼å¥vue -->
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

</head>
<body>

<!-- Logoä¸ç»å½çé¢ -->
<header id="header">
	<div id="head" class="parallax" parallax-speed="1">

		<!--è®¾ç½®æ°ä¹¦ééä»¥åèªå¨ç»­ååç»­åå¨é¨ä¹¦ç±-->
		<ul class="setup">
			<li id="openNewBookPush">æ°ä¹¦éé</li>
			<li id="openAutoBorrowPush">èªå¨ç»­å</li>
			<li id="openBorrowAllNoPush">ç»­åå¨é¨ä¹¦ç±</li>
		</ul>

		<h1 id="logo" class="text-center">Szpt BookSystem</h1>
		<!--æç´¢æ¡-->
		<div class="searchBar" id="searchInputModel">
			<form action="/bookSystem/searchwithpymongo" method="GET">
				<select name="searchType" id="find_code" v-model="searchType">
				     <option value="AllKeyButNotCatalog" selected="">ææå­æ®µ(é¤ç®å½)</option>
				     <option value="AllKey">ææå­æ®µ</option>
				     <option value="CatalogKey">ç®å½</option>
				     <option value="BookNameKey">é¢åå³é®è¯</option>
				     <option value="BookAuthorKey">èè</option>
				     <option value="BookPublisherKey">åºçç¤¾</option>
				     <option value="ISBNKey">ISBN</option>
				     <option value="IndexKey">ç´¢ä¹¦å·</option>
				     <option value="SystemNumberKey">ç³»ç»å·</option>
			    </select>
				<input id="searchInput" type="text" name="queryContent" v-model="queryContent" />
				<input type="submit" value="æ£ç´¢" />
				<div id="operate">
					<span id="label">æåºï¼</span>
					<select name="sortType" id="sort" v-model="sortType">
						<option value="0" selected="">å¹´/è¯å/è¯è®ºäººæ°(éåº)</option>
						<option value="1">è¯å/å¹´/è¯è®ºäººæ°(éåº)</option>
						<option value="2">è¯è®ºäººæ°/å¹´/è¯å(éåº)</option>
					</select>
				</div>
			</form>
		</div>

		<!--ç»å½çé¢,ç»å½ä¹å-->
		<!-- {% if request.session.isLogin %}
		<div class="login topy">
			<p>
				<strong class="vwmy"><a href="" target="_blank" title="è®¿é®æçç©ºé´">{{request.session.userName}}</a></strong>
				<span class="pipe">|</span><a href="javascript:;" id="myitem" class="showmenu" onmouseover="showMenu({'ctrlid':'myitem'});" initialized="true">æç</a>
				<span class="pipe">|</span><a href="/bookSystem/ttest">è®¾ç½®</a>
				<span class="pipe">|</span><a href="/bookSystem/quitLogin">éåº</a>
			</p>
		</div>
		{% else %}
		<form action="/bookSystem/login" method="POST">
			{% csrf_token %}
			<div class="topy login">
				<table cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<td><label for="ls_username">å¸å·</label></td>
							<td><input type="text" name="username" id="ls_username" class="px vm xg1" value="å­¦å·/Email" onfocus="if(this.value == 'å­¦å·/Email'){this.value = '';this.className = 'px vm';}" onblur="if(this.value == ''){this.value = 'å­¦å·/Email';}" tabindex="901"></td>
							<td class="fastlg_l"><label for="ls_cookietime"><input type="checkbox" name="cookietime" id="ls_cookietime" class="pc" value="2592000" tabindex="903">èªå¨ç»å½</label></td>
						</tr>
						<tr>
							<td><label for="ls_password">å¯ç </label></td>
							<td><input type="password" name="password" id="ls_password" class="px vm" autocomplete="off" tabindex="902"></td>
							<td class="fastlg_l"><button type="submit" class="pn vm" tabindex="904" style="width: 75px;"><em>ç»å½</em></button></td>
						</tr>
					</tbody>
				</table>
				<input type="hidden" name="quickforward" value="yes">
				<input type="hidden" name="handlekey" value="ls">
			</div>
		</form>
		{% endif %}
	</div> -->

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-collapse collapse">

				<ul class="nav navbar-nav">
					<li><a href="index.html">Home</a></li>
					<li><a href="blog.html">æ°ä¹¦éé</a></li>
					<li class="active"><a href="about.html">About</a></li>
				</ul>

			</div>
		</div>
	</nav>
</header>

  <script type="text/javascript" >
  function getFiveStar(id,filled){
    var canvas = document.getElementById(id);
    var context = canvas.getContext("2d");
    context.beginPath();
    //è®¾ç½®æ¯ä¸ªé¡¶ç¹çåæ ï¼æ ¹æ®é¡¶ç¹å¶å®è·¯å¾
    for (var i = 0; i < 5; i++) {
        context.lineTo(Math.cos((18+i*72)/180*Math.PI)*8.5+8.5,
                        -Math.sin((18+i*72)/180*Math.PI)*8.5+8.5);
        context.lineTo(Math.cos((54+i*72)/180*Math.PI)*4+8.5,
                        -Math.sin((54+i*72)/180*Math.PI)*4+8.5);
    }
    context.closePath();
    //è®¾ç½®è¾¹æ¡æ ·å¼ä»¥åå¡«åé¢è²
    context.lineWidth="1";
    context.fillStyle = "#F5270B";
    context.strokeStyle = "#F5270B";
    if(filled){
    	context.fill();
    }
    context.stroke();
  }
  </script>
  
<!-- ä¸»æ¾ç¤ºåºå  -->
<main id="main">
	<div class="container">

		<div class="row topspace">
				<!--åé¡µæ ç­¾-->
				<div class="dividePage" id="dividePage1">
					<legend>
						æ¬æ¬¡æç´¢ä¸å±æ{{totalCount}}æ¡è®°å½ï¼å½åé¡µæ°ä¸º{{page}},æ»é¡µæ°ä¸º{{ totalPage }},æç´¢ä¸éä¸º2000æ¡è®°å½
					</legend>
					<legend>
						<center class="">
							<ul class="pagination">
								<template v-if="page <= 0">
									<li><a href="javascript:void(0);" v-on:click="gotoPage(0)">Â«</a></li>
								</template>
								<template v-else>
									<li><a href="javascript:void(0);" v-on:click="gotoPage(0)">Â«</a></li>
								</template>

								<template v-if="page-5>0">
									<li><a href="javascript:void(0);" v-on:click="gotoPage(0)">0</a></li>
									<li><a href="javascript:void(0);">.......</a></li>
								</template>

								<template v-for="p in range">
									<template v-if="p == page">
										<li class="active"><a href="javascript:void(0);" v-on:click="gotoPage(p)">{{ p }}</a></li>
									</template>
									<template v-else>
										<li><a href="javascript:void(0);" v-on:click="gotoPage(p)">{{ p }}</a></li>
									</template>
								</template>

								<template v-if="page+5 < totalPage">
									<li><a href="javascript:void(0);">.......</a></li>
								</template>

								<li><a href="javascript:void(0);" v-on:click="gotoPage(totalPage)">{{totalPage}}</a></li>

								<template v-if="page>=totalPage">
									<li><a href="javascript:void(0);" v-on:click="gotoPage(totalPage)">Â»</a></li>
								</template>
								<template v-else>
									<li><a href="javascript:void(0);" v-on:click="gotoPage(totalPage)">Â»</a></li>
								</template>
							</ul>
						</center>
					</legend>
				</div>

			<!-- æç´¢ç»æ -->
			<div class="searchResult" id="searchResult">
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
										<span class="ratingScore">è¯åï¼{{ book.ratingAverage }}</span>
										<span class="ratingScore">è¯åäººæ°:{{ book.ratingNumberRaters }}</span>
									</legend>

								</div>
								<br/>
								<table class="bookDetailTable">
									<tr>
										<td class="bookDetailLabel">ä½èï¼</td>
										<td class="bookDetailContent">{{ book.author }}</td>
										<td class="bookDetailLabel">ç´¢ä¹¦å·ï¼</td>
										<td class="bookDetailContent">{{ book.index }}</td>
									</tr>
									<tr>
										<td class="bookDetailLabel">åºçç¤¾ï¼</td>
										<td class="bookDetailContent">{{ book.publisher }}</td>
										<td class="bookDetailLabel">å¹´ä»½ï¼</td>
										<td class="bookDetailContent">{{ book.publishYear }}</td>
									</tr>
									<tr>
										<td class="bookDetailLabel">é¦èå¤æ¬:</td>
										<td class="bookISBN bookDetailContent">å è½½ä¸­....</td>
									</tr>
								</table>
							</div>
						</article>
						<br/>
					</template>
				</template>
				<template v-else>
					ææ å¾ä¹¦
				</template>


		</div>

		<!--åé¡µæ ç­¾-->
		<div class="dividePage" id="dividePage2">
			<legend>
				<center class="">
					<ul class="pagination">
						<template v-if="page <= 0">
							<li><a href="">Â«</a></li>
						</template>
						<template v-else>
							<li><a href="">Â«</a></li>
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
							<li><a href="">Â»</a></li>
						</template>
						<template v-else>
							<li><a href="">Â»</a></li>
						</template>
					</ul>
				</center>
			</legend>
		</div>

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


<!-- ä½¿ç¨VUEæ¸²æè¯¥é¡µé¢ -->
<script src="assert/js/bookSearchRender.js"></script>