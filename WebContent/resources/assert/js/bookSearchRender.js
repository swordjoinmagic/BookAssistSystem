// 使用Vue渲染搜索的结果页面

timeOutlist = []

// 用于显示搜查结果的VUE模型
var searchResult = new Vue({
     el:'#searchResult',
     data:{
        data:{
            document:{
                bookList:[],
                totalCount:0,
                totalPage:0,
                page:0
            },
        },
        isLoading:true
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
                    console.log("请求馆藏信息成功，他的数据为:");
                    console.log(data.book);
                    console.log("这本书是:");
                    console.log(book);
                    book.remainDataXiLi.remain = data.book[0].remain;
                    book.remainDataLiuXian.remain = data.book[1].remain;
                    console.log("这本书值改变之后");
                    console.log(book);
                    
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

// 用于显示分页结果的两个VUE模型
var dividePage1 = new Vue({
    el:'#dividePage1',
    data:{
        totalCount:1000,
        page:1,
        totalPage:100,
        range:[1,2,3,4,5,6]
    },
    methods:{
        gotoPage:function(page){
           updateSearchResult(page,searchInputModel.sortType,searchInputModel.searchType,encodeURI(searchInputModel.queryContent));
        }
    }
});
var dividePage2 = new Vue({
    el:'#dividePage2',
    data:{
        totalCount:1000,
        page:1,
        totalPage:100,
        range:[1,2,3,4,5,6]
    },
    methods:{
        gotoPage:function(page){
           updateSearchResult(page,searchInputModel.sortType,searchInputModel.searchType,encodeURI(searchInputModel.queryContent));
        }
    }
});

// 用于绑定表单输入
var searchInputModel = new Vue({
    el:'#searchInputModel',
    data:{
        sortType:0,
        searchType:'AllKeyButNotCatalog',
        queryContent:''
    },
    methods:{
        gotoPage:function(page){
            updateSearchResult(page,searchInputModel.sortType,searchInputModel.searchType,encodeURI(searchInputModel.queryContent));
         }
    }
});

// 用于更新页面上的搜查结果的ajax
function updateSearchResult(page,sortType,searchType,queryContent){
    searchResult.isLoading = true;
    $.ajax({
        url:'/BookAssitantSystem/search?page='+page+'&sortType='+sortType+'&searchType='+searchType+'&queryContent='+queryContent+'&token='+getEncryptionCode(),
        // dataType:'jsonp',
        type:'GET',
        success:function(data){
            searchResult.isLoading = false;
            searchResult.data = data;

            // 缩短书名
            searchResult.data.document.bookList.forEach(element => {
                element.bookName = element.bookName.split("/")[0];
                element.bookName = element.bookName.split('=')[0];
                // setTimeout(() => {
                //     getBookCollection(element.systemNumber,element);
                // }, Math.ceil(Math.random()*10));
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

            dividePage1.range = range;
            dividePage1.page = page;
            dividePage1.totalPage = totalPage;
            dividePage1.totalCount = totalCount;
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

// 获得一本书的馆藏副本信息,需要提供系统号
function getBookCollection(systemNumber,book){

    $.ajax({
        url:'http://localhost:8088/interface/getBookCollectionStatus?systemNumber='+systemNumber,
        // dataType:'jsonp',
        type:'GET',
        success:function(data){
            console.log("请求馆藏信息成功，他的数据为:");
            console.log(data.book);
            console.log("这本书是:");
            console.log(book);
            book.remainDataXiLi.remain = data.book[0].remain;
            book.remainDataLiuXian.remain = data.book[1].remain;
            console.log("这本书值改变之后");
            console.log(book);
            
        }
    });
}


// Test
