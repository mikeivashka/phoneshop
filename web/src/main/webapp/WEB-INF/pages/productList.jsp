<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<tags:master pageTitle="Products list">
    <div class="row justify-content-between">
        <div>
            <h1>
                Hello from product list!
            </h1>
            <p>
                Found ${totalResults} phones.
            </p>
        </div>
        <div class="col-md-1 pt-1">
            <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/quickOrder">Quick order</a>
        </div>
        <!-- Search form -->
        <div>
            <form>
                <input type="hidden" name="order" value="${param.order}">
                <input type="hidden" name="sort" value="${param.sort}">
                <input name="query" class="form-control" type="text" placeholder="Search" aria-label="Search"
                       value="${param.query}">
                <button type="submit" class="btn btn-primary">
                    <em class="fas fa-search">Submit</em>
                </button>
            </form>
        </div>
    </div>

    <table class="table">
        <thead class="thead-light">
        <tr>
            <th>Image</th>
            <th><tags:sortSelect fieldName="BRAND"/>Brand</th>
            <th><tags:sortSelect fieldName="MODEL"/>Model</th>
            <th>Color</th>
            <th><tags:sortSelect fieldName="DISPLAY_SIZE"/>Display size</th>
            <th><tags:sortSelect fieldName="PRICE"/>Price</th>
            <th>Quantity</th>
            <th>Action</th>
        </tr>
        </thead>
        <c:forEach var="item" items="${phones}">
            <tr>
                <td>
                    <img height="70px"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.imageUrl}">
                </td>
                <td>${item.brand}</td>
                <td><a href="${pageContext.servletContext.contextPath}/productDetails/${item.id}">${item.model}</a>
                </td>
                <td><c:if test="${not empty item.colors}">
                    <ul>
                        <c:forEach var="color" items="${item.colors}">
                            <li>${color.code}</li>
                        </c:forEach>
                    </ul>

                </c:if>
                </td>
                <td>${item.displaySizeInches}</td>
                <td>$ ${item.price}</td>
                <td><input id="${item.id}" value="1"/><label for="${item.id}"></label></td>
                <td>
                    <button class="btn btn-secondary" onclick="addToCart(${item.id}, $('#${item.id}').val())">Add to
                        cart
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <nav aria-label="Page navigation example">
        <form id="pageForm">
            <input type="hidden" name="page" id="pageInput" value="${page}">
            <input type="hidden" name="order" value="${param.order}">
            <input type="hidden" name="sort" value="${param.sort}">
            <input type="hidden" name="query" value="${param.query}">
            <ul class="pagination justify-content-center">

                <li class="page-item" onclick="document.getElementById('pageInput').value=0; pageForm.submit();">
                    <p class="page-link" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </p>
                </li>
                <c:forEach var="i"
                           begin="${page-3 > 0? page-3 : 0}"
                           end="${page+3 < pagesCount ? page+3 : pagesCount}">
                    <li class="page-item <c:if test='${i eq page}'>active</c:if>"
                        onclick="document.getElementById('pageInput').value=${i}; pageForm.submit();">
                        <p class="page-link" aria-label="Next">
                            <span aria-hidden="true">${i+1}</span>
                        </p>
                    </li>
                </c:forEach>
                <li class="page-item"
                    onclick="document.getElementById('pageInput').value=${pagesCount}; pageForm.submit();">
                    <p class="page-link" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </p>
                </li>
            </ul>
        </form>
    </nav>
</tags:master>