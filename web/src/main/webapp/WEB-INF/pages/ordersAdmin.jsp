<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tags:master pageTitle="Orders">
    <h1>
        Orders
    </h1>
    <div class="justify-content-center">
        <table class="table">
            <thead class="thead-light">
            <tr>
                <th>Order number</th>
                <th>Customer</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Date</th>
                <th>Total price</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td class="align-middle">
                           <a href="${pageContext.request.contextPath}/admin/orders/${order.id}"> ${order.id}</a>
                    </td>
                    <td class="align-middle">
                            ${order.firstName} ${order.lastName}
                    </td>
                    <td class="align-middle">
                            ${order.contactPhoneNo}
                    </td>
                    <td class="align-middle">
                            ${order.deliveryAddress}
                    </td>
                    <td class="align-middle">
                            ${order.placementDate}
                    </td>
                    <td class="align-middle">
                            ${order.totalPrice}
                    </td>
                    <td class="align-middle">
                            ${order.status.displayName}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</tags:master>