package com.es.phoneshop.web.controller.dto;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateOrderForm {
    @Valid
    private List<@Valid OrderItemEntryCreateForm> orderItems;
    @Size(min = 1, message = "First name is required")
    private String firstName;
    @Size(min = 1, message = "Last name is required")
    private String lastName;
    @Size(min = 1, message = "Delivery address is required")
    private String deliveryAddress;
    @Pattern(regexp = "^\\+375\\((17|29|33|44)\\)[0-9]{3}-[0-9]{2}-[0-9]{2}$", message = "Enter a valid phone number")
    private String contactPhoneNo;
    @Size(max = 4096, message = "Limit is 4096 symbols")
    private String additionalInfo;

    public List<OrderItemEntryCreateForm> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntryCreateForm> orderItems) {
        this.orderItems = orderItems;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
