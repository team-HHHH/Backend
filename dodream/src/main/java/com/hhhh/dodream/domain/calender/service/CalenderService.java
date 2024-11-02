package com.hhhh.dodream.domain.calender.service;

import com.hhhh.dodream.domain.calender.dto.request.CalenderCreateRequestDto;
import com.hhhh.dodream.domain.calender.dto.request.CalenderUpdateRequestDto;
import com.hhhh.dodream.domain.calender.dto.response.CalenderInquiryResponseDto;
import com.hhhh.dodream.domain.calender.entity.CalenderEntity;
import com.hhhh.dodream.domain.calender.repository.CalenderRepository;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import com.hhhh.dodream.domain.user.repository.UserRepository;
import com.hhhh.dodream.global.exception.kind.agreed_exception.UnAuthorizedException;
import com.hhhh.dodream.global.exception.kind.error_exception.DataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalenderService {
    private final UserRepository userRepository;
    private final CalenderRepository calenderRepository;

    public List<CalenderInquiryResponseDto> get(Long userId, Integer year, Integer month) {
        List<CalenderEntity> calenders = calenderRepository
                .findAllByUserIdAndDateInfoYearAndDateInfoMonth(userId, year, month);

        return calenders.stream().map(CalenderInquiryResponseDto::from).toList();

    }

    public List<CalenderInquiryResponseDto> get(Long userId) {
        List<CalenderEntity> calenders = calenderRepository.findAllByUserId(userId);

        return calenders.stream().map(CalenderInquiryResponseDto::from).toList();
    }

    @Transactional
    public Long save(Long userId, CalenderCreateRequestDto calenderRequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataFoundException("user db에 없습니다."));

        CalenderEntity calender = CalenderEntity.of(calenderRequest, user);
        calenderRepository.save(calender);

        return calender.getId();
    }

    @Transactional
    public void update(Long userId, Long calenderId, CalenderUpdateRequestDto updateRequestDto) {
        CalenderEntity calender = validateCalenderWithUser(calenderId, userId, "수정");

        calender.modify(updateRequestDto);
        calenderRepository.save(calender);
    }

    @Transactional
    public void delete(Long userId, Long calenderId) {
        CalenderEntity calender = validateCalenderWithUser(calenderId, userId, "삭제");

        calenderRepository.delete(calender);
    }

    private CalenderEntity validateCalenderWithUser(Long calenderId, Long userId, String action) {
        CalenderEntity calender = calenderRepository.findByIdWithUser(calenderId)
                .orElseThrow(() -> new DataFoundException("calender db에 없는 데이터입니다."));

        if (!userId.equals(calender.getUser().getId())) {
            throw new UnAuthorizedException("해당 일정을 " + action + "할 권한이 없습니다.");
        }

        return calender;
    }
}
