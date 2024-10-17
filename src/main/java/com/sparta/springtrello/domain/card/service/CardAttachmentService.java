package com.sparta.springtrello.domain.card.service;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.card.dto.response.CardAttachmentResponseDto;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardRepository;
import com.sparta.springtrello.domain.card.util.CardFinder;
import com.sparta.springtrello.domain.manager.repository.ManagerRepository;
import com.sparta.springtrello.domain.manager.util.ManagerUtil;
import com.sparta.springtrello.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardAttachmentService {
    private final CardFinder cardFinder;
    private final CardRepository cardRepository;
    private final ManagerRepository managerRepository;
    private final ManagerUtil managerUtil;

    //카드에 첨부파일 추가
    /*
    1.요청한 유저가 해당 카드의 매니저인지 확인
    2.파일 업로드
    3.Card 객체에 url 담아서 저장
     */
    @Transactional
    public CardAttachmentResponseDto attachFileToCard(Member requestedMember, Long cardId, MultipartFile file) throws IOException {
        Card card = cardFinder.findById(cardId);
        //요청한 유저가 해당 카드의 매니저인지 확인
        managerUtil.validateCardManager(requestedMember, card);
        //파일 업로드
        String url = upload(file);
        //Card 객체에 url 담아서 저장
        card.setAttachmentUrl(url);
        cardRepository.save(card);

        return new CardAttachmentResponseDto(
                card.getId(), card.getTitle(), url
        );
    }


    //파일 첨부 기능
    public String upload(MultipartFile multipartFile) throws IOException {
        //파일 크기, 형식 검증
        validateMultipartFileSize(multipartFile);
        validateMultipartFile(multipartFile);

        //MultipartFile -> File
        File convertFile = convert(multipartFile).orElseThrow(
                () -> new ApiException(ErrorStatus.INTERNAL_SERVER_ERROR_FAILED_CONVERT_FILE)
        );

        //파일 경로를 String 반환
        return convertFile.getAbsolutePath();
    }

    // 파일 convert 후 로컬에 업로드
    private Optional<File> convert(MultipartFile file) throws IOException {
        String uuidFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String uploadDir = System.getProperty("user.dir") + "/upload/";

        // 해당 폴더가 존재하지 않으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 경로에 디렉터리가 없으면 모든 상위 디렉터리도 포함해서 생성
        }

        File convertFile = new File(uploadDir + uuidFilename);
        if (convertFile.createNewFile()) { // 지정한 경로에 파일이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    //파일 크기 검증
    private void validateMultipartFileSize(MultipartFile multipartFile) {
        //파일 크기 제한(5MB)
        long maxFileSize = 5L * 1024 * 1024;
        if (multipartFile.getSize() > maxFileSize) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_EXCEED_FILE_SIZE);
        }
    }

    private void validateMultipartFile(MultipartFile multipartFile) {
        // 업로드 가능한 파일 확장자 목록
        Set<String> allowedExtensions = Set.of("jpg", "png", "pdf", "csv");

        //파일 명이 비어있을 경우
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_INVALID_FILE_NAME);
        }
        //파일에 확장자가 없는 경우
        String fileExtension = getFileExtension(fileName);
        if (fileExtension.isEmpty()) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_INVALID_FILE_TYPE);
        }

        //지원되지 않는 파일 형식으로 업로드한 경우
        if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
            throw new ApiException(ErrorStatus.BAD_REQUEST_INVALID_FILE_TYPE);
        }
    }

    //확장자 추출
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex == -1) {
            return ""; // 확장자가 없는 경우
        }
        return filename.substring(dotIndex + 1);
    }
}
