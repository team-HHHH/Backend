package com.hhhh.dodream.domain.user.service;

import com.hhhh.dodream.domain.user.dto.request.EmailAuthRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserPasswordUpdateRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserRegisterDetailRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserRegisterRequestDto;
import com.hhhh.dodream.domain.user.dto.request.UserUpdateRequestDto;
import com.hhhh.dodream.domain.user.dto.response.UserDuplicatedResponseDto;
import com.hhhh.dodream.domain.user.dto.response.UserEmailCodeCheckResponseDto;
import com.hhhh.dodream.domain.user.dto.response.UserInquiryResponseDto;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import com.hhhh.dodream.domain.user.repository.UserRepository;
import com.hhhh.dodream.global.common.service.S3ImageService;
import com.hhhh.dodream.global.common.service.MailService;
import com.hhhh.dodream.global.common.utils.RandomUtils;
import com.hhhh.dodream.global.exception.kind.agreed_exception.DuplicatedException;
import com.hhhh.dodream.global.exception.kind.agreed_exception.MissingDataException;
import com.hhhh.dodream.global.exception.kind.agreed_exception.VerificationException;
import com.hhhh.dodream.global.exception.kind.error_exception.DataFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_IMAGE_PREFIX = "user_image/";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final S3ImageService s3ImageService;
    private final MailService mailService;
    private final UserRedisService userRedisService;
    private final AuthService authService;

    public void sendAuthCode(String email) {
        if (checkEmailDuplication(email).isDuplicated()) {
            throw new DuplicatedException("이미 가입된 이메일입니다.");
        }
        Integer authCode = RandomUtils.getAuthCode();
        mailService.sendAuthCodeOnMail(email, authCode);
        userRedisService.saveAuthCode(email, authCode);
    }

    public UserEmailCodeCheckResponseDto checkAuthCode(EmailAuthRequestDto authRequestDto) {
        boolean hasBeenChecked = userRedisService.checkAuthCode(authRequestDto.getEmail(),
                authRequestDto.getEmailCode());
        if (hasBeenChecked) {
            return new UserEmailCodeCheckResponseDto(true);
        }
        return new UserEmailCodeCheckResponseDto(false);
    }

    @Transactional(readOnly = true)
    public UserDuplicatedResponseDto checkEmailDuplication(String email) {
        boolean duplicated = userRepository.existsByEmail(email);
        return new UserDuplicatedResponseDto(duplicated);
    }

    @Transactional(readOnly = true)
    public UserDuplicatedResponseDto checkLoginIdDuplication(String loginId) {
        boolean duplicated = userRepository.existsByLoginId(loginId);
        return new UserDuplicatedResponseDto(duplicated);
    }

    @Transactional(readOnly = true)
    public UserDuplicatedResponseDto checkNicknameDuplication(String nickname) {
        boolean duplicated = userRepository.existsByNickname(nickname);
        return new UserDuplicatedResponseDto(duplicated);
    }

    @Transactional
    public void register(UserRegisterRequestDto userRegisterRequestDto, HttpServletResponse response) {
        if (checkLoginIdDuplication(userRegisterRequestDto.getLoginId()).isDuplicated()) {
            throw new DuplicatedException("중복된 아이디입니다.");
        }
        if (!userRedisService.isEmailChecked(userRegisterRequestDto.getEmail())) {
            throw new VerificationException("이메일 인증이 되지 않았습니다");
        }
        String encodedPassword = passwordEncoder.encode(userRegisterRequestDto.getPassword());
        UserEntity user = userRepository.save(
                new UserEntity(userRegisterRequestDto.getEmail(), userRegisterRequestDto.getLoginId(),
                        encodedPassword));
        authService.setJWT(user.getId(), user.getRole(), response);
    }

    @Transactional
    public void registerDetail(UserRegisterDetailRequestDto detailRequestDto, Long userId) {
        UserEntity user = this.findUser(userId);
        if (checkNicknameDuplication(detailRequestDto.getNickName()).isDuplicated()) {
            throw new DuplicatedException("중복된 닉네임입니다.");
        }
        String imagePath = s3ImageService.uploadImageToS3(detailRequestDto.getProfileImage(),
                USER_IMAGE_PREFIX + user.getId());
        user.registerDetail(detailRequestDto, imagePath);
    }

    public UserInquiryResponseDto get(Long userId) {
        UserEntity user = this.findUser(userId);
        return UserInquiryResponseDto.from(user);
    }

    @Transactional
    public void update(UserUpdateRequestDto updateRequestDto, Long userId) {
        UserEntity user = this.findUser(userId);

        user.modifyProfileImage(updateRequestDto);
        userRepository.save(user);
    }

    @Transactional
    public void update(MultipartFile profileImage, Long userId) {
        UserEntity user = this.findUser(userId);

        String imagePath = s3ImageService.uploadImageToS3(profileImage, USER_IMAGE_PREFIX + user.getId());

        updateProfileImageOnExtensionChange(imagePath, user);
    }

    @Transactional
    public void update(Long userId, UserPasswordUpdateRequestDto updateRequestDto) {
        UserEntity user = this.findUser(userId);

        validateAndUpdatePassword(updateRequestDto, user);
    }

    public void checkNickname(Long userId) {
        UserEntity user = findUser(userId);

        if (ObjectUtils.isEmpty(user.getNickname())) {
            throw new MissingDataException("닉네임 부재");
        }
    }

    private UserEntity findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new DataFoundException("user db에 없는 데이터입니다."));
    }

    private void updateProfileImageOnExtensionChange(String imagePath, UserEntity user) {
        if (!imagePath.equals(user.getImagePath())) {
            user.modifyProfileImage(imagePath);
            userRepository.save(user);
        }
    }

    private void validateAndUpdatePassword(UserPasswordUpdateRequestDto updateRequestDto, UserEntity user) {
        if (passwordEncoder.matches(updateRequestDto.getOriginPw(), user.getPassword())) {
            String encodedNewPw = passwordEncoder.encode(updateRequestDto.getNewPw());
            user.modifyPassword(encodedNewPw);
            userRepository.save(user);
        } else {
            throw new VerificationException("기존 비밀번호 불일치");
        }
    }
}
