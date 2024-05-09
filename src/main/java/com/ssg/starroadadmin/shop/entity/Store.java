package com.ssg.starroadadmin.shop.entity;

import com.ssg.starroadadmin.global.entity.BaseTimeEntity;
import com.ssg.starroadadmin.user.entity.Manager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Store extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "complex_shoppingmall_id")
    private ComplexShoppingmall complexShoppingmall;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
    private String storeType;

    @Column(unique = true)
    private String name;
    private String imagePath;
    private String contents;
    private int floor;
    private String operatingTime; // hh:mm ~ hh:mm format
    private String contactNumber;
    private String storeGuideMap;
    private int reviewCount;

    public void updateInfo(String contents, String operatingTime, String contactNumber) {
        this.contents = checkNull(contents, this.contents);
        this.operatingTime = checkNull(operatingTime, this.operatingTime);
        this.contactNumber = checkNull(contactNumber, this.contactNumber);
    }

    public void updateImagePath(String imagePath) {
        this.imagePath = checkNull(imagePath, this.imagePath);
    }

    private <T> T checkNull(T newValue, T currentValue) {
        return newValue != null ? newValue : currentValue;
    }


}
