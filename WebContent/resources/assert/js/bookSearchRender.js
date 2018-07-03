// 使用Vue渲染搜索的结果页面

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
            }
        }
     },
     methods:{
         getBookUrl:function(ISBN){
            console.log("getBookUrl方法执行");
            console.log('/BookAssitantSystem/book/'+ISBN);
            return '/BookAssitantSystem/book/'+ISBN;
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
    $.ajax({
        url:'http://localhost:8080/BookAssitantSystem/search?page='+page+'&sortType='+sortType+'&searchType='+searchType+'&queryContent='+queryContent+'&token='+getEncryptionCode(),
        dataType:'jsonp',
        type:'GET',
        success:function(data){
            searchResult.data = data;

            // 缩短书名
            searchResult.data.document.bookList.forEach(element => {
                element.bookName = element.bookName.split("/")[0]
                element.bookName = element.bookName.split('=')[0]
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




// Test
updateSearchResult(0,0,"",encodeURI('java[^script]'));
