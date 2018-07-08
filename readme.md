# SZPT图书馆辅助系统 #
## 重构声明 ##
该项目使用Java语言重构我之前用Python Django写的那个图书管理系统。因为当时对python不是很熟的原因，所以写的项目不好维护，用起来也难受，所以这里，特地用java重写一遍该项目。  
放一下之前用Django写的图书管理系统的地址：  
https://github.com/swordjoinmagic/BookRecommandSystemWeb-with-Django

## 使用技术栈 ##
### 后端 ###
后端使用SSM（Spring MVC,Spring,Mybatis）框架来实现。同时，基于Spring的@Scheduled注解来完成定时任务（定时爬取学校图书馆、定时向用户推送新书等等）。其中Spring MVC负责管理表示层的各种业务逻辑（由其中的Controller实现），Mybatis用于管理持久层业务，在这里主要用于映射SQL语句，同时
### 前端 ###
网页方面，使用Vue.js渲染HTML页面，由JQuery异步请求后端编写的接口，得到json数据，由Vue渲染json到html页面上。  
安卓app方面，使用Unity实现该安卓app，自己写了一个类似MVVM思想的Unity UI框架，以渲染json到Unity UGUI界面中。Unity通过yield return new WWW(url)来对后端编写的接口进行请求，请求得到一个json数据。
### 爬虫 ###
爬虫使用python语言编写，对于简单的爬虫（比如登录学校图书馆、获得目标用户所有已借书籍等等），使用requests实现，对于整个学校图书馆的爬虫，使用scrapy框架进行爬取（速度快）。


## 需求分析 ##
1.学校图书馆借的书都很容易忘记还，这个时候需要有个软件提醒什么时候还书，或者自动帮我进行书籍的续借  

2.比较难知道学校的新书有什么，需要有一个推送新书的程序告诉我，比如说我关注的一个关键词有python，就会在新书出现python关键字的时候给我推送特别关注的新书  

3.馆藏空闲通知，有时候，我想要借的书给别人借走了，这个时候，就特别想要一个程序，可以及时通知我那本书什么时候还回来了  

4.高级搜索功能，学校图书馆借阅系统只能通过出版年份/作者进行排序，这样只能通过一本书的名字来判断好坏，所以，急需增加一本知道图书好坏的功能，所以增加了通过豆瓣评分排序，豆瓣评分人数排序，豆瓣评论人数排序等排序功能  

5.书籍详情页面显示的内容太少了，学校图书馆的系统，最多显示内容简介跟书名，其他信息都要到其他平台进行搜索，这太麻烦了，所以，想到了，在书籍详情页面增加很多内容，大致有，目录，内容简介，作者，还有评论，关于评论这一部分，想到了通过两种方式来显示，一种是普通的显示，也就是一条一条评论的显示，一种是直接全部文字的词云显示，可以让读者第一时间知道其他人对这本书的第一印象  

6.同时需要注意的是，所有关于通知的内容（续借，馆藏空闲通知，新书速递）都通过Spring的@Scheduled注解来完成，也就是，一天运行一次，或者一个月运行一次，避免给学校图书馆服务器造成太大压力。  

##使用的数据库 ##
### 1.MongoDB（主要保存爬虫爬取的图书信息，同时也用于搜索（nosql查询速度比较快）） ###
### 2.MySQL（用于管理一些比较通常的业务数据，比如用户数据，评论数据等等） ###
## 核心业务 ##
### 1.搜索--基于目录的搜索使用中文全文索引技术（使用solr或jieba+mongodb实现全文索引） ###
### 2.自动管理-自动续借，新书速递，馆藏空闲通知等，基于spring框架的定时任务注解完成 ###
### 3.图书数据保存在MongoDB中，加快搜索速度，用户数据，评论数据保存在MySQL中，因为不需要频繁搜索。 ###
## 难点（按要做的时间顺序往下） ##
### 1.MongoDB连接类的封装，这个我不是很熟 ###
### 2.搜索的编写，以及MongoDB的索引、分页（更快的分页（基于时间戳索引），不是skip、limit这种）设置，都不是很熟 ###
### 3.缩小搜索范围的编写，难点在于如何优雅的在原本搜索的限定文档的情况下，增加搜索条件。 ###
### 4.spring定时任务的编写，这个没学过，现学 ###
### 5.mybatis框架实现对用户数据（借阅书籍，借阅历史）、评论数据进行管理，存到MySQL里面去，mybatis忘得差不多了，现学。 ###
### 6.Unity彩蛋，暂时还没想到以什么形式出现以及又怎样的新意，前面的都写完了再想。 ###
### 7.真正的前后端分离，后端返回的数据一律采用JSON的格式，前端使用各种前端进行制作，比如Unity(着重体现特效方面)。 ###

## 定时任务的设计 ##
定时任务使用spring的轻便型定时框架设计，自动续借功能每天中午12点检查一次，新书速递、重新爬取每个月的一号触发一次。  
自动续借每次触发时，对数据库进行搜索，找到所有开启自动续借的用户，然后访问pytho开放的接口，得到用户每本书的剩余借阅日期（json）。然后判断是否已经到达临界值。

## 加密策略 ##
使用时间戳进行加密。  
前端，js每次发送请求的时候，都发送一个token，token是当前时间戳每个数字都转化为ascill码，然后进行base64加密的形式。  
后端，后台收到token后，对token进行base64解密对该时间戳进行解密，获得到的时间跟当前时间进行对比，如果当前请求的年份-月份-天数-小时对的上，那么就是一个正确请求，如果对不上，那就是一个错误请求，返回错误信息。

## 数据库设计 ##

数据库名：BookSystemDataWithJava

### 用户 ###
表名：users  
字段　　　　　　　　类型　　　　　　　　　　描述  
userName　　　　　　varchar　　　　　　　用户名，用来登录的,主键,唯一  
email　　　　　　　　varchar　　　　　　　邮箱，唯一，用来发送各种通知信息或者登录的  
nickName　　　　　　varchar　　　　　　　用户昵称，用来在评论区显示的  
password　　　　　　varchar　　　　　　　密码，用来登录学校图书馆系统的  
isEnableAutoBorrow　tinyint　　　　　　是否开启自动续借，0为false，1为true  
isEnableAutoNewBook　tinyint　　　　　　是否开启新书速递，同上  

SQL：  
create table users(userName varchar(20) not null primary key,email varchar(40),nickName varchar(20),password varchar(20) not null)charset utf8;

测试数据：  
insert into users values('16240011','16240011@qq.com','sjm','09043330');

用户表关联方法：  
1.根据用户UserName找用户，用于登录  
2.根据用户Email找用户，用于登录，发送新书速递，自动续借  
3.根据用户的isEnableAutoBorrow找用户，用于查找所有开启自动续借的用户  
4.根据用户的isEnableAutoNewBook找用户，用于查找所有开启新书速递的用户 
5.根据UserName、password插入用户  
6.根据email或者nickName更改用户信息

### 评论数据 ###
表名：comments  
字段　　　　　　　　类型　　　　　　　　　　描述  
id				 int					主键，自增，用以标识每个评论数据  
comment　　　　　　text　　　　　　　　　　评论主体数据  
fromISBN　　　　　varchar　　　　　　　　表示这个评论是来自于哪本书（使用ISBN码标识）的，外键  
fromUserID　　　　varchar　　　　　　　　表示这个评论是来自于哪个用户的（使用userName标识），外键  


SQL:  
 create table comments(id int auto_increment not null primary key,comment text,fromISBN varchar(40) not null,fromUserID varchar(20) not null,foreign key(fromUserID) references users(userName))charset utf8;

测试数据：  
insert into comments(comment,fromISBN,fromUserID) values('just is a comment','9787115385734','16240011');

评论表关联方法：  
1.根据ISBN码找评论集合（有分页机制）  
2.根据用户id，ISBN码找评论集合

### 用户特别关注标签 ###
表名：specialKeys  
字段　　　　　　　　类型　　　　　　　　　　描述  
id　　　　　　　　　int　　　　　　　　　　主键，自增，用以标识每个用户的特别关注标签  
specialKey　　　　text　　　　　　　　　　特别关注标签的主体内容部分  
keyType　　　　　varchar　　　　　　　　 表示这个特别关注标签是关于什么的，目录:catalog,书名:bookName等等，这个方面跟mongodb数据库里书的那些key值一样  
fromUserID　　　　varchar　　　　　　　　表示这个特别关注标签是来自于哪个用户的（使用userName标识），外键

SQL:  
create table specialKeys(id int auto_increment not null primary key,specialKey text not null,keyType varchar(30) not null,fromUserID varchar(20) not null,foreign key(fromUserID) references users(userName),foreign key(keyType) references keyTypes(keyType))charset utf8;

测试数据:  
insert into specialKeys(specialKey,keyType,fromUserID) values("Unity","Catalog","16240011");

特别关注标签表关联方法：  
1.根据用户ID查找某个用户的所有特别关注标签

### keyType ###
emmmmmmmm,这是一个为了防止用户瞎点弄得数据库  
表名:keyTypes  
字段　　　　　　　　类型　　　　　　　　　　描述  
keyType　　　　　　varchar　　　　　　　　 跟特别关注标签一样

SQL:  
create table keyTypes(keyType varchar(30) not null primary key)charset utf8;

测试数据：  
CatalogKey  
BookNameKey  
BookAuthorKey  
BookPublisherKey  
AllKeyButNotCatalog

### 历史记录 ###
显示用户的历史记录  
表名：historys  
字段　　　　　　　　类型　　　　　　　　　　描述  
type　　　　　　　varchar　 用于区分自动续借和馆藏空闲通知等信息  
description　　　text　　　用于描述该历史记录干了什么  
fromUserID　　　　varchar　　　来自于哪个用户
createTime　　　　timestamp　　历史记录表创建时间

SQL:  
create table historys(type varchar(50),description text,fromUserID varchar(20) not null,createTime timestamp null default CURRENT_TIMESTAMP,foreign key(fromUserID) references users(userName))charset utf8;

### 馆藏空闲通知表 ###
显示用户关注的馆藏空闲书籍：  
表名:freenNotices  
字段　　　　　　　　类型　　　　　　　　　　描述    
id　　　　　　　　　int　　　　　　　　　　自增主键  
bookName　　　　　　varchar　　　　　用户关注的那本书的名字  
ISBN　　　　　　　　varchar　　　　　　　　该书的ISBN码  
FromUserID　　　　　varchar　　　　　　　哪个用户关注该书  
bookIndex　　　　　　　　varchar　　　　　　　索书号    
(nowStatus,js动态加载，不保存在数据库)　　　　boolean　　　　　　　　目前状态，是否处于空闲状态

SQL:  
create table freenNotices(id int auto_increment not null primary key,bookName varchar(100) not null,ISBN varchar(40) not null,FromUserID varchar(20) not null,foreign key(FromUserID) references users(userName))charset utf8;


### 语音验证码题库 ###
表名:questions  
字段　　　　　　　　类型　　　　　　　　　　描述    
id　　　　　　　　　int　　　　　　　　　　自增主键  
question　　　　　　varchar　　　  　　　　　问题描述  
answer　　　　　　　　varchar　　　　　　　问题的答案    

SQL：  
create table questions(id int auto_increment not null primary key,question varchar(100) not null,answer varchar(60) not null)charset utf8;  

测试数据：  
insert into questions(question,answer) values('哼哼哈嘿快使用双截棍，哼哼哈嘿快使用双截棍,这首歌的原唱是谁','周杰伦');
insert into questions(question,answer) values('请问在如今计算机语言中，哪个语言是最好的语言','PHP');