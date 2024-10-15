//package com.sparta.springtrello.domain.card.service;
//
//import com.sparta.springtrello.common.ErrorStatus;
//import com.sparta.springtrello.common.exception.ApiException;
//import com.sparta.springtrello.domain.card.dto.request.CardCreateRequestDto;
//import com.sparta.springtrello.domain.card.dto.request.CardUpdateRequestDto;
//import com.sparta.springtrello.domain.card.dto.response.CardCreateResponseDto;
//import com.sparta.springtrello.domain.card.dto.response.CardUpdateResponseDto;
//import com.sparta.springtrello.domain.card.entity.Card;
//import com.sparta.springtrello.domain.card.repository.CardRespository;
//import com.sparta.springtrello.domain.card.util.CardFinder;
//import com.sparta.springtrello.domain.list.entity.List;
//import com.sparta.springtrello.domain.list.util.ListFinder;
//import com.sparta.springtrello.domain.member.entity.Member;
//import com.sparta.springtrello.domain.member.enums.MemberRole;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CardService {
//
//    private final CardRespository cardRespository;
//    private final CardFinder cardFinder;
//    private final ListFinder listFinder;
//
//    /*
//    이 워크스페이스의 멤버가 아니거나
//    멤더라도 READ_ONLY 권한일 경우
//     */
//    public CardCreateResponseDto create(Long workspaceId,
//                                        Long listId,
//                                        Member member,
//                                        CardCreateRequestDto requestDto) {
//
//        validateMember(member, workspaceId);
//        validateReadOnly(member);
//
//        Card card = new Card(requestDto.getTitle());
//        List list = listFinder.findById(listId);
//        card.setList(list);
//        Card savedCard = cardRespository.save(card);
//        return new CardCreateResponseDto(
//                savedCard.getId(),
//                savedCard.getTitle());
//    }
//
//    public CardUpdateResponseDto update(Long workspaceId,
//                                        Long cardId,
//                                        CardUpdateRequestDto requestDto,
//                                        Member member) {
//
//        validateMember(member, workspaceId);
//        validateReadOnly(member);
//
//        Card card = cardFinder.findById(cardId);
//        card.update(requestDto);
//        Card savedCard = cardRespository.save(card);
//        return new CardUpdateResponseDto(
//                savedCard.getId(),
//                savedCard.getTitle(),
//                savedCard.getContents(),
//                savedCard.getDeadline());
//    }
//
//    private void validateMember(Member member, Long workspaceId) {
//        //이 워크스페이스의 멤버인 경우
//        if (!member.getWorkspace().getId().equals(workspaceId)) {
//            throw new ApiException(ErrorStatus.FORBIDDEN_NOT_MEMBER);
//        }
//    }
//
//    private void validateReadOnly(Member member) {
//        if (member.getMemberRole() == MemberRole.READ_ONLY) {
//            throw new ApiException(ErrorStatus.FORBIDDEN_READ_ONLY);
//        }
//    }
//
//
//}
