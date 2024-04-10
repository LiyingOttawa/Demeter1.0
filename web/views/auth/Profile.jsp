<%-- 
    Document   : Profile
    Created on : Apr 6, 2024, 10:40:24 AM
    Author     : hanzg
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
     <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/demeter.css">
</head>
<body>
    <c:choose>
        <c:when test="${empty sessionScope.userRoleFunction}">
            <script>
                alert("Please log in");
            </script>
        </c:when>
    </c:choose>

    <div class="main-container">
        <div class="main-row">
            <div class="main-section">
                <h3>Photo</h3>
                <img src="${ctx}/resources/img/user/profile_small-1.jpg" alt="User Photo" class="profile-photo">
            </div>
            <div class="main-section">
                <h3>Role</h3>
                <label>Role:</label>
                <span>${sessionScope.userRoleFunction.roleName}</span><br>
                <!-- Add more role info fields as needed -->
            </div>
        </div>

        <div class="main-row">
            <div class="main-section">
                <h3>Basic Information</h3>
                <label>Name:</label>
                <span>${sessionScope.userRoleFunction.name}</span><br>
                <label>Email:</label>
                <span>${sessionScope.userRoleFunction.email}</span><br>
                <!-- Add more basic info fields as needed -->
            </div>
         <div class="main-section">
                <h3>Permission</h3>
                <label>You can do:</label>
                <ul>
                    <c:forEach var="item" items="${sessionScope.userRoleFunction.functionURLs}">
                        <li>${item.funcName}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</body>     
</html>
