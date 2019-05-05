<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="__STATIC__/home/js/jquery-3.2.1.min.js"></script>
</head>
<body>
<form action="{:url('account/reg')}" method="post">
    用户名:<input type="text" name="phone" id="phone"><br>
    验证码:<input type="text" name="captcha" id="captcha"><div><img id="verify_img" src="" alt="captcha" onclick="refreshVerify()"></div><br>
    <!--<input type="text" name="phoneMessage"><button id="btnSendCode" onclick="codeMessage()">发送短信验证码</button><br>-->
    <td>
        短信验证码： <input id="phoneMessage" type="text" name="phoneMessage" style="width:150px;" onblur="checkPhoneMessageCode()" >
        <input id="btnSendCode" type="button" style="width:100px;font-size:14px;" value="获取验证码" onclick="phoneMessageCodeSend()"><br>
    <td id="CodeInput"></td>
    </td>
    <button type="submit">提交</button>
</form>

<script type="text/javascript">

    function refreshVerify() {
        var ts = Date.parse(new Date())/1000;
        var img = document.getElementById('verify_img');
        img.src = "{:captcha_src()}?id="+ts;
    }
    window.onload =function() {
        refreshVerify();
    }//网页加载完成后再加载验证

    //发送按钮的点击事件
    function phoneMessage(){
        //向后端请求发送短信，传值过去判断
        var phone = document.getElementById("phone").value;//获取手机号数值
        var captcha = document.getElementById("captcha").value;//获取验证码数值
        $.ajax({
            url:"reg",
            data:{"type":1,"phone":phoneh,"captcha":captca},
            type:"POST",

            success:function(data){
                if(data.code) {
                    alert("发送短信成功");
                } else {
                    alert(data.msg);
                    refreshVerify();
                }
                //成功反馈
                console.log(data);
            }
        });
    }

    function phoneMessageCodeSend() {


        phoneMessage();
    }


    //短信验证码输入框的焦点事件
    function checkPhoneMessageCode(){
        var phone=document.getElementById("phone").value;
        var phoneMessage=document.getElementById("phoneMessage").value;
        var s=document.getElementById("CodeInput");
        $.ajax({
            url:"reg",
            data:{"type":2,"phoneMessage":phoneMessage,"phone":phone},
            type:"POST",
            sueccess:function (data) {
                console.log(data);
                var dataObj=JSON.parse(data);//对字符串形式的json解析为对象
                console.log(dataObj.result);
                if(dataObj.result=="validate!"){
                    s.innerHTML="校验成功";
                }else{
                    s.innerHTML="校验失败";
                }
            }
        })
    }



    // function codeMessage(){
    //       sendMessage();
    //     // SetRemainTime();
    //     // doCompare();
    // };
    //       //ajax验证手机短信
    //   function sendMessage() {
    //       // curCount = count;
    //       // //设置button效果，开始计时
    //       // $("#btnSendCode").attr("disabled", "true");
    //       // $("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
    //       // InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    //       // //向后台发送处理数据
    //       $.ajax({
    //           type: "POST", //用POST方式传输     　　
    //           url: 'account.php', //目标地址.
    //           dataType: "json", //数据格式:JSON
    //           data: "$phone",
    //           success: function(json){
    //               if(json.msgid==1){//成功的处理
    //                   alert(json.html);
    //               }
    //               else if(json.msgid==2){//失败的处理
    //                   alert(json.html);
    //               }
    //               else{
    //                   alert(json.html);
    //               }
    //           }
    //           error:function(data) {          // 请求错误
    //                 return "你错了";
    //           }
    //       });
    //   }
    //
    // //timer处理函数
    // function SetRemainTime() {
    //     if (curCount == 0) {
    //         window.clearInterval(InterValObj);//停止计时器
    //         $("#btnSendCode").removeAttr("disabled");//启用按钮
    //         $("#btnSendCode").val("重新发送验证码");
    //     }
    //     else {
    //         curCount--;
    //         $("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
    //     }
    // }

</script>
</body>
</html>