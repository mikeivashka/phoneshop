<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<tags:master pageTitle="Quick Order">
    <div class="row">
        <div>
            <h1>
                Quick Order
            </h1>
        </div>
        <div class="col-md-1 pt-1">
            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/">Back to Product
                List</a>
        </div>
    </div>
    <c:if test="${not empty added}">
        <p class="text-success"> Successfully added:
            <c:forEach var="item" items="${added.keySet()}">
                ${item.brand} ${item.model}
            </c:forEach>
        </p>
    </c:if>
    <c:if test="${hasBindingErrors}">
        <p class="text-danger">There were errors</p>
    </c:if>
    <form:form method="post" modelAttribute="orderForm" action="${pageContext.servletContext.contextPath}/quickOrder">
        <c:forEach var="i" begin="0" end="7">
            <div class="input-group">
                <div>
                    <span class="input-group-text">Product code and quantity</span>
                </div>
                <div class="form-group" style="display: block">
                    <form:input placeholder="samsung-s-1" name="productCode" type="text" aria-label="First name"
                                cssClass="form-control" path="orderItems[${i}].productKey"/><br/>
                    <c:if test="${not empty orderForm.orderItems[i].productKey || not empty orderForm.orderItems[i].quantity}">
                        <form:label cssStyle="display: block" cssClass="text-danger" path="orderItems[${i}].productKey">
                            <form:errors path="orderItems[${i}].productKey"/>
                        </form:label>
                    </c:if>
                </div>
                <div class="form-group" style="display: inline-block">
                    <form:input placeholder="Required quantity" name="quantity" type="text" aria-label="Last name"
                                cssClass="form-control" path="orderItems[${i}].quantity"/><br/>
                    <c:if test="${not empty orderForm.orderItems[i].productKey || not empty orderForm.orderItems[i].quantity}">
                        <form:label cssStyle="display: block" cssClass="text-danger" path="orderItems[${i}].quantity">
                            <form:errors path="orderItems[${i}].quantity"/>
                        </form:label>
                    </c:if>
                </div>
            </div>
        </c:forEach>
        <input type="submit" class="btn btn-primary" value="Add to cart">
    </form:form>
</tags:master>