package com.elux.appliancecontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="APPLIANCES")
@JsonIgnoreProperties("id")
public class Appliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="appliance_id")
    private String applianceId;

    @Column(name="appliance_type")
    private String applianceType;

    public Appliance() {
    }


    public Appliance(int id, String applianceId, String applianceType) {
        this.id = id;
        this.applianceId = applianceId;
        this.applianceType = applianceType;
    }

    public Appliance(String applianceId, String applianceType) {
        this.applianceId = applianceId;
        this.applianceType = applianceType;
    }

    @Override
    public String toString() {
        return '{'+"applianceId:'" + applianceId + '\'' +
                ", applianceType:'" + applianceType + '\'' +
                '}';
    }
}
