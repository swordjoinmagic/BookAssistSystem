// 用于渲染设置界面
// 用于渲染设置基本设置界面的Vue模型
var baseSetUpModel = new Vue({
    el:'#baseSetUpModel',
    data:{
        actived:false
    }
});
// 用于渲染设置基本设置的li
var basesetupLi = new Vue({
    el:'#basesetup',
    methods:{
        getActived:function(){
            return baseSetUpModel.actived;
        },
        clickThis:function(){
            // 将其他所有模型的可见性调为fasle
            vueModelList.forEach(element => {
                element.actived = false;
            })
            // 使自身可见
            baseSetUpModel.actived = true;
        }
    }
});

// 用于渲染历史记录的Vue模型
var historyModel = new Vue({
    el:'#historyModel',
    data:{
        userID:0,
        actived:false,
        historyList:[],     // 历史记录列表，用于渲染历史记录
        page:0
    },
    methods:{
        // 前往指定的一页
        gotoPage:function(page){
            // 下一页的情况
            if(page>historyModel.page){
                if(historyModel.historyList.length<5){
                    // 没有下一页
                    console.log("没有下一页");
                    return false;
                }else{
                    // 有下一页，使用ajax请求下一页
                    updateFreeNotice(page,userID);
                }
            }else{
                // 上一页的情况
                if(historyModel.page<=0){
                    // 没有上一页
                    console.log("没有上一页");
                    return false;
                }else{
                    // 有上一页，使用ajax请求上一页
                    updateFreeNotice(page,userID);
                }
            }
        }
    }
});
// 用于渲染历史记录的li
var historyLi = new Vue({
    el:'#history',
    methods:{
        getActived:function(){
            return historyModel.actived;
        },
        clickThis:function(){
            // 将其他所有模型的可见性调为fasle
            vueModelList.forEach(element => {
                element.actived = false;
            })
            // 使自身可见
            historyModel.actived = true;
        }
    }
});

// 用于渲染特别关注标签设置的Vue模型
var specialkeysModel = new Vue({
    el:'#specialkeysModel',
    data:{
        actived:false
    }
});
// 用于渲染特别关注标签设置的Li
var specialkeysLi = new Vue({
    el:'#attention',
    methods:{
        getActived:function(){
            return specialkeysModel.actived;
        },
        clickThis:function(){
            // 将其他所有模型的可见性调为fasle
            vueModelList.forEach(element => {
                element.actived = false;
            })
            // 使自身可见
            specialkeysModel.actived = true;
        }
    }
});

// 用于渲染馆藏空闲通知的vue模型
var freenoticeModel = new Vue({
    el:'#freenoticeModel',
    data:{
        userID:0,
        actived:false,
        freeNoticeList:[],
        page:0
    },
    methods:{
        // 前往指定的一页
        gotoPage:function(page){
            // 下一页的情况
            if(page>freenoticeModel.page){
                if(freenoticeModel.freeNoticeList.length<5){
                    // 没有下一页
                    console.log("没有下一页");
                    return false;
                }else{
                    // 有下一页，使用ajax请求下一页
                    updateHistory(page);
                }
            }else{
                // 上一页的情况
                if(freenoticeModel.page<=0){
                    // 没有上一页
                    console.log("没有上一页");
                    return false;
                }else{
                    // 有上一页，使用ajax请求上一页
                    updateHistory(page);
                }
            }
        }
    }
});
// 用于渲染馆藏空闲通知的vue模型
var freenoticeLi = new Vue({
    el:'#freenotice',
    methods:{
        getActived:function(){
            return freenoticeModel.actived;
        },
        clickThis:function(){
            // 将其他所有模型的可见性调为fasle
            vueModelList.forEach(element => {
                element.actived = false;
            })
            // 使自身可见
            freenoticeModel.actived = true;
        }
    }
});

var vueModelList = [baseSetUpModel,specialkeysModel,historyModel,freenoticeModel]



function updateHistory(page,userID){
    $.ajax({
        url:'http://localhost:8080/BookAssitantSystem/record/historys?fromUserID='+userID+'&page='+page,
        dataType:'jsonp',
        type:'GET',
        success:function(data){
            // 更新historyModel中的信息
            historyModel.historyList = data.historyList;
            historyModel.page = page;
        }
    });
}

function updateFreeNotice(page,userID){
    $.ajax({
        url:'http://localhost:8080/BookAssitantSystem/record/historys?fromUserID='+userID+'&page='+page,
        dataType:'jsonp',
        type:'GET',
        success:function(data){
            // 更新historyModel中的信息
            freenoticeModel.freeNoticeList = data.freeNoticeList;
            freenoticeModel.page = page;
        }
    });
}