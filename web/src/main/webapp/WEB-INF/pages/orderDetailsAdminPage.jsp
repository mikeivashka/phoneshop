<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<tags:master pageTitle="Order overview">
    <div class="row justify-content-around">
        <div class="col">
            <h1>
                Order №${order.id}<br/>
            </h1>
        </div>
        <div class="col">
            <h1>
                Status: ${order.status.displayName}<br/>
            </h1>
        </div>
    </div>
    <div class="row justify-content-center">
        <table class="table">
            <thead class="thead-light">
            <tr>
                <th>Brand</th>
                <th>Model</th>
                <th>Color</th>
                <th>Display size</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
            </thead>
            <c:forEach var="item" varStatus="status" items="${order.orderItems}">
                <tr>
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
                <td colspan="4"/>
                <td>Subtotal:</td>
                <td><strong>$ ${order.subtotal}</strong></td>
            </tr>
            <tr>
                <td colspan="4"/>
                <td>Delivery price:</td>
                <td><strong>$ ${order.deliveryPrice}</strong></td>
            </tr>
            <tr>
                <td colspan="4">
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
    <div class="row p-3">
        <div class="col-2">
            <a class="btn btn-secondary btn-lg text-light" href="${pageContext.request.contextPath}/admin/orders">
                Back
            </a>
        </div>
        <c:if test="${order.status.name() == 'NEW'}">
            <div class="col-2">
                <form:form method="post">
                    <input name="status" type="hidden" value="DELIVERED">
                    <button type="submit" class="btn btn-secondary btn-lg">Delivered</button>
                </form:form>
            </div>
            <div class="col-2">
                <form:form method="post">
                    <input name="status" type="hidden" value="REJECTED">
                    <button type="submit" class="btn btn-secondary btn-lg">Rejected</button>
                </form:form>
            </div>
        </c:if>


    </div>
</tags:master>
