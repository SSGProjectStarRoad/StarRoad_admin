package com.ssg.starroadadmin.shop.entity;

import com.ssg.starroadadmin.common.entity.BaseTimeEntity;
import com.ssg.starroadadmin.user.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Store extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "complex_shoppingmall_id")
    private ComplexShoppingmall complexShoppingmall;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Users managerId;

    @Column(unique = true)
    private String name;
    private String storeType;
    private String imagePath;
    private String contents;
    private int floor;
    private String operatingTime; // hh:mm ~ hh:mm format
    private String contactNumber;
}
