package com.ssg.starroadadmin.coupon.entity;

import com.ssg.starroadadmin.common.entity.BaseTimeEntity;
import com.ssg.starroadadmin.user.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class CouponHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private Long couponId;
    private boolean usageStatus;
    private LocalDate expiredAt;

}
