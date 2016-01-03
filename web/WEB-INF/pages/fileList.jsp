<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        table, td{
            border: 2px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>

    <p>Current path: ${currentPath}</p>

    <c:if test="${not empty currentPath.parent}">
        <p>
            Back to: <a href="/dir/view?path=${currentPath.parent}" > ${currentPath.parent}</a>
        </p>
    </c:if>

    <form action="/file/create">
        <input type=hidden name=path value="${currentPath}">
        <label for="new_file">Create new</label>
        <input type=text name=fileName placeholder="name" id="new_file"><br>
        <label for="file">File</label>
        <input type="radio" name="type" value="file" id="file" checked><br>
        <label for="directory">Directory</label>
        <input type="radio" name="type" value="directory" id="directory"><br>
        <input type=submit value=Create>
    </form>

   <table>
      <c:forEach items="${fileList}" var="file">
          <tr>
              <c:choose>
                  <c:when test="${file.type == 'FILE'}">
                      <td><a href="/file/view?path=${file.path}" >${file.name}</a></td>
                  </c:when>
                  <c:when test="${file.type == 'DIRECTORY'}">
                      <td><a href="/dir/view?path=${file.path}" > ${file.name} </a></td>
                  </c:when>
              </c:choose>
              <td><a href="/file/remove?path=${file.path}" >Delete</a></td>
          </tr>
      </c:forEach>
   </table>

  <c:if test="${not empty message}">
      <script>
          alert("${message}")
      </script>
  </c:if>

</body>
</html>
