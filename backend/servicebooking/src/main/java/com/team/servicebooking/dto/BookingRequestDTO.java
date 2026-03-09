package com.team.servicebooking.dto;

import java.util.List;

public class BookingRequestDTO {

    private String clientId;
    private String consultantId;
    private String serviceId;
    private List<String> slotIds;

    public BookingRequestDTO() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public List<String> getSlotIds() {
        return slotIds;
    }

    public void setSlotIds(List<String> slotIds) {
        this.slotIds = slotIds;
    }
}
