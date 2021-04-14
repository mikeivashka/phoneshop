<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<tags:master pageTitle="Order overview">
    <div class="row">
        <div>
            <h1>
                Order №${order.id}<br/>
                Thank you for your order!
            </h1>
        </div>
        <div class="col-md-1 pt-1">
            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/productList">Continue
                shopping</a>
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
                </td>
                <td>TOTAL:</td>
                <td><strong>$ ${order.totalPrice}</strong></td>
            </tr>
        </table>
    </div>
    <form>
        <div class="form-group">
            <div class="input-group col-6">
                <label for="firstName">
                    First name
                </label>
                <input size="20" id="firstName" value="${order.firstName}" disabled="true"/>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group col-6">
                <label for="lastName">Last name</label>
                <input size="20" id="lastName" value="${order.lastName}" disabled="true"/>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group col-6">
                <label for="deliveryAddress">
                    Delivery address
                </label>
                <input id="deliveryAddress" value="${order.deliveryAddress}" disabled="true"/>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group col-6">
                <label for="contactPhoneNo">
                    Phone
                </label>
                <input size="20" id="contactPhoneNo" value="${order.contactPhoneNo}" disabled="true"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-6">
                <textarea class="col" disabled="true">${order.additionalInfo}</textarea>
            </div>
        </div>
    </form>

</tags:master>
