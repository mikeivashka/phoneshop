<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<tags:master pageTitle="Products list">
    <h1>
        Cart
    </h1>

    <c:choose>
        <c:when test="${not empty cart}">
            <div class="row justify-content-center">
                <form:form modelAttribute="cartUpdateForm" action="${pageContext.servletContext.contextPath}/cart"
                           method="put">
                    <table class="table">
                        <thead>
                        <tr>
                            <td>Image</td>
                            <td>Brand</td>
                            <td>Model</td>
                            <td>Color</td>
                            <td>Display size</td>
                            <td>Price</td>
                            <td>Quantity</td>
                            <td>Action</td>
                        </tr>
                        </thead>
                        <c:forEach var="item" varStatus="status" items="${cart.keySet()}">
                            <tr>
                                <td class="align-middle">
                                    <a href="${pageContext.servletContext.contextPath}/productDetails/${item.id}">
                                        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.imageUrl}"
                                             height="80">
                                    </a>
                                </td>
                                <td class="align-middle">${item.brand}</td>
                                <td class="align-middle">
                                    <a href="${pageContext.servletContext.contextPath}/productDetails/${item.id}">${item.model}</a>
                                </td>
                                <td class="align-middle">
                                    <ul>
                                        <c:forEach var="color" items="${item.colors}">
                                            <li>${color.code}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                                <td class="align-middle">${item.displaySizeInches}"</td>
                                <td class="align-middle">
                                    <c:choose>
                                        <c:when test="${not empty item.price}">$${item.price}</c:when>
                                        <c:otherwise>unknown</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="align-middle">
                                    <div class="input-group">
                                        <form:input path="cartEntries[${status.index}].productId"
                                                    hidden="hidden"
                                                    value="${item.id}"/>
                                        <form:input path="cartEntries[${status.index}].quantity"
                                                    cssClass="form-control"
                                                    cssErrorClass="form-control is-invalid"
                                                    value="${cartUpdateForm.cartEntries.get(status.index).quantity}"/>
                                        <div class="invalid-tooltip">
                                            <form:errors path="cartEntries[${status.index}].quantity"/>
                                        </div>
                                    </div>
                                </td>
                                <td class="align-middle">
                                    <a onclick="deleteFromCart(${item.id})"
                                       class="btn btn-primary text-white">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="row justify-content-end mt-3 mb-3">
                        <input type="submit" class="btn btn-primary mr-1" value="Update"/>
                        <a class="btn btn-primary ml-3" href="${pageContext.servletContext.contextPath}/order">Order</a>
                    </div>
                </form:form>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row">
                <div class="col">
                    <h3 class="text-secondary text-center">Your cart is empty</h3>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</tags:master>