package com.example.callcenter1.model.call;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CallDetailsView {
    @Id
    private Integer call_id;
    private String call_time;
    private String call_date;
    private Integer call_duration;
    private Integer customer_id;
    private String customer_name;
    private String customer_surname;
    private String description;
    private String service_type;
    private String city;
    private String district;
    private String township;
    private String neighbourhood;
    private Integer operator_id;

    // Getters and Setters
    public Integer getCall_id() { return call_id; }
    public void setCall_id(Integer call_id) { this.call_id = call_id; }
    public String getCall_time() { return call_time; }
    public void setCall_time(String call_time) { this.call_time = call_time; }
    public String getCall_date() { return call_date; }
    public void setCall_date(String call_date) { this.call_date = call_date; }
    public Integer getCall_duration() { return call_duration; }
    public void setCall_duration(Integer call_duration) { this.call_duration = call_duration; }
    public Integer getCustomer_id() { return customer_id; }
    public void setCustomer_id(Integer customer_id) { this.customer_id = customer_id; }
    public String getCustomer_name() { return customer_name; }
    public void setCustomer_name(String customer_name) { this.customer_name = customer_name; }
    public String getCustomer_surname() { return customer_surname; }
    public void setCustomer_surname(String customer_surname) { this.customer_surname = customer_surname; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getService_type() { return service_type; }
    public void setService_type(String service_type) { this.service_type = service_type; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getTownship() { return township; }
    public void setTownship(String township) { this.township = township; }
    public String getNeighbourhood() { return neighbourhood; }
    public void setNeighbourhood(String neighbourhood) { this.neighbourhood = neighbourhood; }
    public Integer getOperator_id() { return operator_id; }
    public void setOperator_id(Integer operator_id) { this.operator_id = operator_id; }
} 