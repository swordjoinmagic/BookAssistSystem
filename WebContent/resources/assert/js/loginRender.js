var loginFormModel = new Vue({
    el:'#loginFormModel',
    data:{
        errorMsg:'请输入正确的用户名',
        userName:'',
        password:'',
        token:'',
        verificationCode:''
    },
    methods:{
        getToken:function(){
            this.token = getEncryptionCode();
            return this.token;
        },
        getLoginCheck:function(){
            loginFormModel.errorMsg = '正在提交中~';
            $.ajax({
                url:'http://localhost:8080/BookAssitantSystem/login/check?userName='+this.userName+'&password='+this.password+'&verificationCode='+this.verificationCode+'&token='+this.token,
                type:'GET',
                success:function(data){

                    console.log("请求成功，返回数据如下：");
                    console.log(data);
                
                    var status = data.status;
                    var errorMsg = data.errorMsg;

                    if(!status){
                        loginFormModel.errorMsg = errorMsg;
                    }else{
                        loginFormModel.errorMsg = '成功登录，准备跳转页面~';
                        window.location.href="/BookAssitantSystem/bookSearch";
                    }
                }
            });
        }
    }
});
