specialKeysAsideModel = new Vue({
    el:'#specialKeysAside',
    data:{
        specialKeys:[],
        searchType:"",
        queryContent:""
    },
    methods:{
        // key:要在新书集中搜索的内容，keyType：搜索模式：如对书名进行搜索，对目录进行搜索等等
        searchNewBook:function(key,keyType){
            this.searchType = keyType;
            this.queryContent = key;
            updateSearchResult(0,0,keyType,key);
        },
        getKeyType:function(keyType){
            if(keyType=="AllKey"){
                return "所有字段";
            }else if(keyType=="CatalogKey"){
                return "目录";
            }else if(keyType=="BookNameKey"){
                return "题名关键词";
            }else if(keyType=="BookAuthorKey"){
                return "著者";
            }else if(keyType=="BookPublisherKey"){
                return "出版社";
            }else if(keyType=="ISBNKey"){
                return "ISBN";
            }else if(keyType=="IndexKey"){
                return "索书号";
            }else if(keyType=="SystemNumberKey"){
                return "系统号";
            }else{
                return "所有字段(除目录)";
            }
        }
    }
});


searchResult = new Vue({
    el:'#newBookSearchResult',
    data:{
        data:{
            document:{
                bookList:[],
                totalCount:0,
                totalPage:0,
                page:0
            },
        },
        isLoading:false
    },
    methods:{
        getBookUrl:function(ISBN){
            return '/BookAssitantSystem/book/'+ISBN;
         },
         getBookCollectionStatusWithVue:function(book,systemNumber){

            $("#CollapseButton"+book.ISBN).show();
            $("#ExpandButton"+book.ISBN).hide();

            $("#bookCollectionStatus"+book.ISBN).slideDown("slow");

            $.ajax({
                url:'http://localhost:8088/interface/getBookCollectionStatus?systemNumber='+systemNumber,
                // dataType:'jsonp',
                type:'GET',
                success:function(data){
                    // console.log("请求馆藏信息成功，他的数据为:");
                    // console.log(data.book);
                    // console.log("这本书是:");
                    // console.log(book);
                    book.remainDataXiLi.remain = data.book[0].remain;
                    book.remainDataLiuXian.remain = data.book[1].remain;
                    // console.log("这本书值改变之后");
                    // console.log(book);
                    
                }
            });
         },
         collapse:function(book){

            $("#CollapseButton"+book.ISBN).hide();
            $("#ExpandButton"+book.ISBN).show();

            $("#bookCollectionStatus"+book.ISBN).slideUp("slow");

         },
         getRating:function(rating){
             if(rating == -1 || rating==0){
                 return "暂无";
             }
             return rating;
         }
    }
});
// 分页模型
var dividePage2 = new Vue({
    el:'#dividePage2',
    data:{
        totalCount:0,
        page:0,
        totalPage:0,
        range:[]
    },
    methods:{
        gotoPage:function(page){
           updateSearchResult(page,0,specialKeysAsideModel.searchType,encodeURI(specialKeysAsideModel.queryContent));
        }
    }
});

// 用于更新页面上的搜查结果的ajax
function updateSearchResult(page,sortType,searchType,queryContent){
    searchResult.isLoading = true;
    $.ajax({
        url:'/BookAssitantSystem/search?page='+page+'&sortType='+sortType+'&searchType='+searchType+'&queryContent='+queryContent+'&token='+getEncryptionCode()+'&isNewBook=true',
        type:'GET',
        // dataType: "jsonp",
        success:function(data){
            searchResult.isLoading = false;
            searchResult.data = data;

            // 缩短书名
            searchResult.data.document.bookList.forEach(element => {
                element.bookName = element.bookName.split("/")[0];
                element.bookName = element.bookName.split('=')[0];
            });

            // 更新分页
            totalPage = data.document.totalPage;
            totalCount = data.document.totalCount;

            // 构造分页范围
            range = []
            start = page-5<0 ? 0 : page-5;
            end = page+5>totalPage ? totalPage : page+5
            for(i=start;i<end;i++){
                range.push(i);
            }

            dividePage2.range = range;
            dividePage2.page = page;
            dividePage2.totalPage = totalPage;
            dividePage2.totalCount = totalCount;

            // 绘制五角星
            setTimeout(() => {
                drawFiveStar(searchResult.data.document.bookList);    
            }, 10);
            
        }
    });
}


// 整个页面的初始化，获得目标用户的所有新书速递字段
function init(userID){
    $.ajax({
        url:'/BookAssitantSystem/record/getSpecialKeys',
        type:'GET',
        // dataType: "jsonp",
        success:function(data){
            specialKeysAsideModel.specialKeys = data.specialKeys;
        }
    });
}

init();
setTimeout(() => {
    if(specialKeysAsideModel.specialKeys.length>0){
        var specialKey = specialKeysAsideModel.specialKeys[0];
        specialKeysAsideModel.searchType = specialKey.keyType;
        specialKeysAsideModel.queryContent = specialKey.key;
        updateSearchResult(0,0,specialKey.keyType,specialKey.key);
    }    
}, 150);
