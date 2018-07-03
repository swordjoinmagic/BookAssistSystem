// 用于绘制五角星的脚本

function getFiveStar(id,filled){
    var canvas = document.getElementById(id);
    canvas.height = canvas.height;
    var context = canvas.getContext("2d");
    context.beginPath();
    //设置是个顶点的坐标，根据顶点制定路径
    for (var i = 0; i < 5; i++) {
        context.lineTo(Math.cos((18+i*72)/180*Math.PI)*8.5+8.5,
                        -Math.sin((18+i*72)/180*Math.PI)*8.5+8.5);
        context.lineTo(Math.cos((54+i*72)/180*Math.PI)*4+8.5,
                        -Math.sin((54+i*72)/180*Math.PI)*4+8.5);
    }
    context.closePath();
    //设置边框样式以及填充颜色
    context.lineWidth="1";
    context.fillStyle = "#F5270B";
    context.strokeStyle = "#F5270B";
    if(filled){
    	context.fill();
    }
    context.stroke();
  }

  // 给出某本书的ISBN码及其评分，绘制出它的评分
  function drawFiveStar(bookList){
    console.log("drwaFiveStar方法开始了");
    console.log(bookList);
    bookList.forEach(element => {
        ISBN = element.ISBN;
        rating = element.ratingAverage;
        for(i=1;i<=5;i++){
            id = 'canvas'+ISBN+'with'+i;
            if(rating/2 >= i){
                getFiveStar(id,true);
            }else{
                getFiveStar(id,false);
            }
        }
    });
  }