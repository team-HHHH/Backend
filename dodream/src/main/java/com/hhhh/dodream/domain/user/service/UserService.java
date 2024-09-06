package com.hhhh.dodream.domain.user.service;

import com.hhhh.dodream.domain.user.dto.request.UserUpdateRequestDto;
import com.hhhh.dodream.domain.user.dto.response.UserInquiryResponseDto;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import com.hhhh.dodream.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserInquiryResponseDto find(Long userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("db에 없는 데이터입니다."));
        return user.toInquiryDto();
    }

    public void update(UserUpdateRequestDto updateRequestDto, Long userId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("db에 없는 데이터입니다."));
        String passwordEncode = passwordEncoder.encode(updateRequestDto.getPassword());
        updateRequestDto.setPassword(passwordEncode);
        user.updateEntity(updateRequestDto);
        userRepository.save(user);
    }
}
