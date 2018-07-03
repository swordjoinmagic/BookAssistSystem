ia# Java Web项目规划 #
## 数据库 ##
### 1.MongoDB ###
### 2.MySQL ###
## 后端框架 ##
### 1.spring mvc ###
### 2.持久层使用mybaits ###
## 核心业务 ##
### 1.搜索--基于目录的搜索使用中文全文索引技术（使用solr或jieba+mongodb实现全文索引） ###
### 2.自动管理-自动续借，新书速递，馆藏空闲通知等，基于spring框架的定时任务注解完成 ###
### 3.图书数据保存在MongoDB中，加快搜索速度，用户数据，评论数据保存在MySQL中，因为不需要频繁搜索。 ###
## 特色 ##
### 1.搜索业务，以及良好的缩小搜索范围选项 ###
### 2.更加便捷的访问方式--微信小程序（这就要保证后端的数据基本都是使用JSON格式的数据） ###
### 3.Unity彩蛋 ###
## 难点（按要做的时间顺序往下） ##
### 1.MongoDB连接类的封装，这个我不是很熟 ###
### 2.搜索的编写，以及MongoDB的索引、分页（更快的分页（基于时间戳索引），不是skip、limit这种）设置，都不是很熟 ###
### 3.缩小搜索范围的编写，难点在于如何优雅的在原本搜索的限定文档的情况下，增加搜索条件。 ###
### 4.spring定时任务的编写，这个没学过，现学 ###
### 5.mybatis框架实现对用户数据（借阅书籍，借阅历史）、评论数据进行管理，存到MySQL里面去，mybatis忘得差不多了，现学。 ###
### 6.为微信小程序重新写一个前端，后端沿用之前写的，微信小程序没学过，现学。 ###
### 7.Unity彩蛋，暂时还没想到以什么形式出现以及又怎样的新意，前面的都写完了再想。 ###
### 8.真正的前后端分离，后端返回的数据一律采用JSON的格式，前端使用各种前端进行制作，比如Unity(着重体现特效方面)、微信小程序（着重体现方便层面）。 ###

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