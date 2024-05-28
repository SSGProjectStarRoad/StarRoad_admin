package com.ssg.starroadadmin.reward.service;

import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.reward.dto.RewardRegisterRequest;
import com.ssg.starroadadmin.reward.entity.Reward;
import com.ssg.starroadadmin.reward.repository.RewardRepository;
import com.ssg.starroadadmin.reward.service.impl.RewardServiceImpl;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RewardServiceMockTest {
    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private RewardServiceImpl rewardService;

    private Manager adminManager1;
    private Manager adminManager2;
    private Reward reward;
    private RewardRegisterRequest rewardRequest;

    @BeforeEach
    void setUp() {
        adminManager1 = Manager.builder()
                .id(1L)
                .authority(Authority.ADMIN)
                .build();
        adminManager2 = Manager.builder()
                .id(2L)
                .authority(Authority.MALL)
                .build();

        reward = Reward.builder()
                .id(1L)
                .name("Gift Card")
                .rewardImagePath("https://image.url")
                .build();

        rewardRequest = new RewardRegisterRequest("Gift Card", (MultipartFile) new File("img/starroad-1-no-text.png"));

        when(managerRepository.findByIdAndAuthority(1L, Authority.ADMIN)).thenReturn(Optional.of(adminManager1));
        when(rewardRepository.save(any(Reward.class))).thenReturn(reward);
    }

    @Test
    void createReward_withAdminAuthority_shouldSucceed() {
        // Act
        Long createdRewardId = rewardService.createReward(1L, rewardRequest);

        // Assert
        assertEquals(reward.getId(), createdRewardId);
        verify(rewardRepository, times(1)).save(any(Reward.class));
    }

    @Test
    @Disabled
    void createReward_withNonAdminAuthority_shouldThrowException() {
        // Arrange

        when(managerRepository.findByIdAndAuthority(2L, Authority.STORE))
                .thenReturn(Optional.of(adminManager2));

        // Act & Assert
        assertThrows(ManagerException.class, () -> {
            rewardService.createReward(2L, rewardRequest);
        });

        verify(rewardRepository, never()).save(any(Reward.class));
    }
}
