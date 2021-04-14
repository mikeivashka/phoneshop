<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<tags:master pageTitle="Order">
    <div class="row">
        <div>
            <h1>
                Order
            </h1>
        </div>
        <div class="col-md-1 pt-1">
            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/cart">Back to cart</a>
        </div>
    </div>
    <div class="row justify-content-center">
        <table class="table">
            <thead>
            <tr>
                <td>Image</td>
                <td>Brand</td>
                <td>Model</td>
                <td>Color</td>
                <td>Display size</td>
                <td>Quantity</td>
                <td>Price</td>
            </tr>
            </thead>
            <c:forEach var="item" varStatus="status" items="${order.orderItems}">
                <tr>
                    <td class="align-middle">
                        <a href="${pageContext.request.contextPath}/productDetails/${item.phone.id}">
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.phone.imageUrl}"
                                 height="80">
                        </a>
                    </td>
                    <td class="align-middle">${item.phone.brand}</td>
                    <td class="align-middle">
                        <a href="${pageContext.servletContext.contextPath}/productDetails/${item.phone.id}">${item.phone.model}</a>
                    </td>
                    <td class="align-middle">
                        <ul>
                            <c:forEach var="color" items="${item.phone.colors}">
                                <li>${color.code}</li>
                            </c:forEach>
                        </ul>
                    </td>
                    <td class="align-middle">${item.phone.displaySizeInches}"</td>
                    <td class="align-middle">
                        <div class="input-group">
                            <input class="form-control" value="${item.quantity}" disabled/>
                            <div class="invalid-tooltip">
                            </div>
                        </div>
                    </td>
                    <td class="align-middle">
                        <c:choose>
                            <c:when test="${not empty item.phone.price}">$${item.phone.price}</c:when>
                            <c:otherwise>unknown</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="5"/>
                <td>Subtotal:</td>
                <td><strong>$ ${order.subtotal}</strong></td>
            </tr>
            <tr>
                <td colspan="5"/>
                <td>Delivery price:</td>
                <td><strong>$ ${order.deliveryPrice}</strong></td>
            </tr>
            <tr>
                <td colspan="5">
                    <spring:hasBindErrors name="order">
                        <c:if test="${errors.hasFieldErrors('orderItems[*')}">
                            <p class="text-danger">Some of products were removed from your cart, because they are out of
                                stock</p>
                        </c:if>
                    </spring:hasBindErrors>
                </td>
                <td>TOTAL:</td>
                <td><strong>$ ${order.totalPrice}</strong></td>
            </tr>
        </table>
    </div>
    <div class="col-md-6  justify-content-between">
        <form:form action="${pageContext.servletContext.contextPath}/order" method="post" modelAttribute="order">
            <div class="form-group">
                <div class="input-group col-4">
                    <form:label path="firstName" for="firstName">
                        First name*
                    </form:label>
                    <form:input size="20" path="firstName" id="firstName" placeholder="Ivan"
                                value="${order.firstName}"/>
                    <div class="text-danger">
                        <form:errors path="firstName"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group col-6">
                    <form:label path="lastName" for="lastName">Last name*</form:label>
                    <form:input size="20" path="lastName" id="lastName" placeholder="Ivanov" value="${order.lastName}"/>
                    <div class="text-danger">
                        <form:errors path="lastName"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group col-6">
                    <form:label path="deliveryAddress" for="deliveryAddress">
                        Delivery address*
                    </form:label>
                    <form:input path="deliveryAddress" id="address" placeholder="Minsk, Melezha st., 5-2"
                                value="${order.deliveryAddress}"/>
                    <div class="text-danger">
                        <form:errors path="deliveryAddress"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group col-6">
                    <form:label path="contactPhoneNo" for="contactPhoneNo">
                        Phone*
                    </form:label>
                    <form:input size="20" path="contactPhoneNo" id="phone" placeholder="+"
                                value="${order.contactPhoneNo}"/>
                    <div class=" text-danger">
                        <form:errors path="contactPhoneNo"/>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-6">
                    <form:textarea path="additionalInfo" placeholder="Additional information" class="col"/>
                </div>
            </div>
            <input type="submit" class="btn btn-primary" value="Order">
        </form:form>
    </div>
</tags:master>