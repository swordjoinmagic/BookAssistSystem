var loginFormModel = new Vue({
    el:'#loginFormModel',
    methods:{
        getToken:function(){
            return getEncryptionCode();
        }
    }
});