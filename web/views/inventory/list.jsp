<%-- 
    Document   : inventoryItems
    Created on : Mar. 23, 2024, 10:38:28 a.m.
    Author     : Glily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory</title>
      <jsp:include page="/resources/layout/_css.jsp"/>
       <link rel="stylesheet" href="${ctx}/resources/css/datapicker/bootstrap-datepicker.css">
    </head>
    <body>
        <form method="get">
            <label for="itemTypeFilter">Item Type:</label>
            <select id="itemTypeFilter" name="itemType">
                <option value="">All</option>
                <c:forEach items="${viewModel.typeOptions}" var="type">
                    <option value="${type.itemTypeId}" ${param.itemType eq type.itemTypeId ? 'selected' : ''}>${type.itemTypeName}</option>
                </c:forEach>
            </select>

            <label for="statusFilter">Status:</label>
            <select id="statusFilter" name="status">
                <option value="">All</option>
                <c:forEach var="option" items="${viewModel.statusOptions}">
                   <option value="${option.symbol}" ${param.status eq option.symbol ? 'selected' : ''}>${option.text}</option>
               </c:forEach>
            </select>

            <label for="expireDaysFilter">Expire Date (Within):</label>
            <select id="expireDaysFilter" name="expireDays">
                <option value="" ${empty param.expireDays ? 'selected' : ''}>Any</option>
                <option value="1" ${param.expireDays eq '1' ? 'selected' : ''}>1 day</option>
                <option value="7" ${param.expireDays eq '7' ? 'selected' : ''}>7 days</option>
                <option value="30" ${param.expireDays eq '30' ? 'selected' : ''}>30 days</option>
                <option value="60" ${param.expireDays eq '60' ? 'selected' : ''}>60 days</option>
            </select>

            <input type="submit" value="Apply Filter">
        </form>
       <div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <table border="2">
                        <thead>
                            <tr>
                                <th>Item ID</th>
                                <th>Item Name</th>
                                <th>Unit</th>
                                <th>Location ID</th>
                                <th>Create Date</th>
                                <th>User Id</th>
                                <th>Item Type</th>
                                <th>Quantity</th>
                                <th>Expire Date</th>
                                <th>Price</th>
                                <th>Status</th>
                                <th>Status Date</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${viewModel.items}" var="item">
                                <tr>
                                    <td>${item.itemId}</td>
                                    <td>${item.itemName}</td>
                                    <td>${item.unit}</td>
                                    <td>${item.location}</td>
                                    <td>${item.createDate}</td>
                                    <td>${item.userName}</td>
                                    <td>${item.itemType}</td>
                                    <td>${item.quantity}</td>
                                    <td>${item.expirDate}</td>
                                    <td>${item.price}</td>
                                    <td>${item.status}</td>
                                    <td>${item.statusDate}</td>
                                    <td>
                                        <a href="edit?id=${item.itemId}">Edit</a>
                                        <a href="view?id=${item.itemId}">Surplus</a>
                                        <a href="delete?id=${item.itemId}">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <a href="add">Add New Item</a>
    </div>
</div>
 </body>
<jsp:include page="/resources/layout/_script.jsp"/>
<script src="${ctx}/resources/js/datapicker/bootstrap-datepicker.js"></script>
<script>
  <jsp:include page="/resources/layout/_script.jsp"/>
  <script src="${ctx}/resources/js/datapicker/bootstrap-datepicker.js">
</script>

</html>
