package com.hhhh.dodream.domain.calender.service;

import com.hhhh.dodream.domain.calender.dto.request.CalenderCreateRequestDto;
import com.hhhh.dodream.domain.calender.dto.request.CalenderUpdateRequestDto;
import com.hhhh.dodream.domain.calender.dto.response.CalenderInquiryResponseDto;
import com.hhhh.dodream.domain.calender.entity.CalenderEntity;
import com.hhhh.dodream.domain.calender.repository.CalenderRepository;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import com.hhhh.dodream.domain.user.repository.UserRepository;
import com.hhhh.dodream.domain.user.service.UserService;
import com.hhhh.dodream.global.exception.kind.agreed_exception.UnAuthorizedException;
import com.hhhh.dodream.global.exception.kind.error_exception.DataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalenderService {
    private final CalenderRepository calenderRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<CalenderInquiryResponseDto> get(Long userId, Integer year, Integer month) {
        return calenderRepository
                .findByUserIdAndDateInfoYearAndDateInfoMonth(userId, year, month)
                .stream().map(CalenderEntity::toCalenderResponseDto).toList();

    }

    public Long save(Long userId, CalenderCreateRequestDto calenderRequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataFoundException("user db에 없습니다."));
        CalenderEntity calender = CalenderEntity.builder()
                .dateInfo(calenderRequest.getDateInfo())
                .title(calenderRequest.getTitle())
                .content(calenderRequest.getContent())
                .startDay(calenderRequest.getStartDay())
                .endDay(calenderRequest.getEndDay())
                .user(user).build();
        calenderRepository.save(calender);
        return calender.getId();
    }

    public void update(Long userId, Long calenderId, CalenderUpdateRequestDto updateRequest) {
        CalenderEntity calender = calenderRepository.findByIdWithUser(calenderId)
                .orElseThrow(() -> new DataFoundException("calender db에 없는 데이터입니다."));
        if (!userId.equals(calender.getUser().getId())) {
            throw new UnAuthorizedException("해당 일정을 수정할 권한이 없습니다.");
        }
        calender.updateEntity(updateRequest);
        calenderRepository.save(calender);
    }

    public void delete(Long userId, Long calenderId) {
        CalenderEntity calender = calenderRepository.findByIdWithUser(calenderId)
                .orElseThrow(() -> new DataFoundException("calender db에 없는 데이터입니다."));
        if (!userId.equals(calender.getUser().getId())) {
            throw new UnAuthorizedException("해당 일정을 삭제할 권한이 없습니다.");
        }
        calenderRepository.deleteById(calenderId);
    }
}
