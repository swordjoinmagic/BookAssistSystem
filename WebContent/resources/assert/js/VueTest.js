// $.ajax({
//     url:'http://localhost:8080/BookAssitantSystem/login',
//     type:"POST",
//     data:{
//         userName:'sjm',
//         password:'12345',
//         token:'12321421'
//     },
//     success:function(data){
//         console.log(data);
//     }
// });
// console.log(getEncryptionCode());
 function getBookCollection(systemNumber){

    $.ajax({
        url:'http://localhost:8088/interface/getBookCollectionStatus?systemNumber='+systemNumber,
        type:'GET',
        success:function(data,book){
            console.log("请求馆藏信息成功，他的数据为:");
            console.log(data.book);
            book.remainDataXiLi = data.book[0];
            book.remainDataLiuXian = data.book[1];
            
        }
    });
}
getBookCollection('001288532');
