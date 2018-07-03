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
        actived:false
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
        actived:false
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
