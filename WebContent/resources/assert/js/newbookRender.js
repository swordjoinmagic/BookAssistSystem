
newBookSearchResultModel = new Vue({
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

         }
    }
});


// 用于更新页面上的搜查结果的ajax
function updateSearchResult(page,sortType,searchType,queryContent){
    searchResult.isLoading = true;
    $.ajax({
        url:'/BookAssitantSystem/search?page='+page+'&sortType='+sortType+'&searchType='+searchType+'&queryContent='+queryContent+'&token='+getEncryptionCode(),
        type:'GET',
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