package com.example.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Shipment {
    @Id
    @Column(name="shipment_id")
    private String shipmentId;
    @Column(name = "origin")
    private String origin;
    @Column(name = "destination")
    private String destinationAddress;
    @Column(name = "status")
    private String status;
    @Column(name = "itemId")
    private String itemId;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private String quantity;

}

