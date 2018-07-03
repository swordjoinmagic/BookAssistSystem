// 用于渲染用户名的错误信息
var errorMsgUserName = new Vue({
    el:'#usernameDiv',
    data:{
        actived:false
    }
});

// 用于验证码的错误信息
var errorMsgVerificationCodeDiv = new Vue({
    el:'#verificationCodeDiv',
    data:{
        actived:false
    }
});