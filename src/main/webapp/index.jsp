<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<body>
<h2>Hello World!</h2>

springmvc上传文件

<form name="form1" action="http://localhost:8081/mmall_war/manage/product/upload.do" method="post"
      enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" value="springmvc上传文件">
</form>

富文本图片上传
<form name="form1" action="http://localhost:8081/mmall_war/manage/product/richTextImgUpload.do"
      method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" value="富文本上传文件">
</form>
</body>

登陆
<form name="form1" action="http://localhost:8081/mmall_war/user/login.do" method="post">
    <input type="text" name="username">
    <input type="text" name="password">
    <input type="submit" value="登陆">
</form>
</body>
</html>