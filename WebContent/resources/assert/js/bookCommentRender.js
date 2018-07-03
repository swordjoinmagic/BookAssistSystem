// 用于显示评论的VUE模型
var commentsResult = new Vue({
    el:'#commentsResult',
    data:{
        ISBN:'',
        comments:[],
        page:0
    },
    methods:{
        getDate:function(createTime){
            date =  new Date(createTime);
            y = 1900+date.getYear();
            m = "0"+(date.getMonth()+1);
            d = "0"+date.getDate();
            return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
        }  
    }
});

// 用于渲染分页的vue模型
var dividePageComments = new Vue({
    el:'#dividePageComments',
    data:{
        ISBN:'',
        page:0,
    },
    methods:{
        // 获得下一页的方法
        gotoPageComments:function(page){
            if(page>dividePageComments.page){
                if(commentsResult.comments.length>=5)
                    updateCommentsResultWithISBN(page,ISBN);
                else
                    console.log("没有下一页了");
            }else{
                if(dividePageComments.page>0)
                    updateCommentsResultWithISBN(page,ISBN);
                else
                console.log("没有上一页了");
            
            }

        }
    }
});

// 用于更新评论的Ajax,输入两个参数，一个Page，表示页数，一个ISBN，表示要查看哪一本书的评论
function updateCommentsResultWithISBN(page,ISBN){
    $.ajax({
        url:'http://localhost:8080/BookAssitantSystem/book/comments/'+ISBN+'/page/'+page,
        dataType:'jsonp',
        type:'GET',
        success:function(data){

            console.log("请求成功，请求得到的数据如下");
            console.log(data);

            // 更新评论的Vue模型
            commentsResult.ISBN = ISBN;
            commentsResult.comments = data.comments;
            commentsResult.page = page;

            // 更新分页模型
            dividePageComments.ISBN = ISBN;
            dividePageComments.page = page;
        }
    });
}

function init(){
    console.log("正在执行init");
    console.log("ISBN码是：");
    console.log(commentsResult.ISBN);
    updateCommentsResultWithISBN(0,commentsResult.ISBN);
}

