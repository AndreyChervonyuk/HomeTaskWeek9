<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

  <textarea cols="150" rows="30">
    ${content}
  </textarea><br>

  Back to: <a href="/dir/view?path=${backLink}" > ${backLink}</a>

</body>
</html>
