<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<tags:master pageTitle="Products list">
    <div class="row justify-content-around">
        <div class="col-md-4">
            <div>
                <h4>
                    Product details: <strong>${phone.model}</strong>
                </h4>
            </div>
            <img height="300px"
                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"/>
            <div class=" border border-dark rounded p-3">
                <h2>Price, $: ${phone.price}</h2>
                <input id="${phone.id}" value="1"/><label for="${phone.id}"></label>
                <button class="btn btn-secondary" onclick="addToCart(${phone.id}, $('#${phone.id}').val())">Add to
                    cart
                </button>
            </div>
        </div>
        <div class="col-md-4">
            <h3>Display</h3>
            <table class="table">
                <tr>
                    <td>Size, inches</td>
                    <td>${not empty phone.displaySizeInches ? phone.displaySizeInches : 'Unknown'}</td>
                </tr>
                <tr>
                    <td>Resolution</td>
                    <td>${not empty phone.displayResolution ? phone.displayResolution : 'Unknown'}</td>
                </tr>
                <tr>
                    <td>Technology</td>
                    <td>${not empty phone.displayTechnology ? phone.displayTechnology : 'Unknown'}</td>
                </tr>
                <tr>
                    <td>Pixel density</td>
                    <td>${not empty phone.pixelDensity ? phone.pixelDensity : 'Unknown'}</td>
                </tr>
            </table>
            <h3>Dimensions & weight</h3>
            <table class="table">
                <tr>
                    <td>Length, mm</td>
                    <td>${not empty phone.lengthMm ? phone.lengthMm : 'Unknown'}</td>
                </tr>
                <tr>
                    <td>Width, mm</td>
                    <td>${not empty phone.widthMm ? phone.widthMm : 'Unknown'}</td>
                </tr>
                <tr>
                    <td>Colors</td>
                    <td>
                        <c:if test="${empty phone.colors}">Unknown</c:if>
                        <c:forEach var="color" items="${phone.colors}">
                            ${color.code}<br>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>Weight, gr</td>
                    <td>${not empty phone.weightGr ? phone.weightGr : 'Unknown'}</td>
                </tr>
            </table>
            <h3>Camera</h3>
            <table class="table">
                <tr>
                    <td>Front, megapixels</td>
                    <td>${not empty phone.frontCameraMegapixels ? phone.frontCameraMegapixels : 'Unknown'}</td>
                </tr>
                <tr>
                    <td>Back, megapixels</td>
                    <td>${not empty phone.backCameraMegapixels ? phone.backCameraMegapixels : 'Unknown'}</td>
                </tr>
            </table>
            <h3>Battery</h3>
            <table class="table">
                <tr>
                    <td>Talk time, hours</td>
                    <td>${not empty phone.talkTimeHours ? phone.talkTimeHours : 'Unknown'}</td>
                </tr>
                <tr>
                    <td>Stand by time, hours</td>
                    <td>${not empty phone.standByTimeHours ? phone.standByTimeHours : 'Unknown'}</td>
                </tr>
                <tr>
                    <td>Battery capacity, mAh</td>
                    <td>${not empty phone.batteryCapacityMah ? phone.batteryCapacityMah : 'Unknown'}</td>
                </tr>
            </table>
            <h3>Other</h3>
            <table class="table">
                <tr>
                    <td>Device type</td>
                    <td>${not empty phone.deviceType ? phone.deviceType : 'Unknown'}</td>
                </tr>
                <tr>
                    <td>Bluetooth</td>
                    <td>${not empty phone.bluetooth ? phone.bluetooth : 'Unknown'}</td>
                </tr>
            </table>
        </div>
    </div>
</tags:master>