package com.projectx.board.service;

import com.projectx.board.dto.BoardDTO;
import com.projectx.board.dto.BoardItemDTO;
import com.projectx.board.entity.BoardEntity;
import com.projectx.board.entity.BoardFileEntity;
import com.projectx.board.entity.User;
import com.projectx.board.repository.BoardFileRepository;
import com.projectx.board.repository.BoardRepository;
import com.projectx.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)
// 변환하는 과정이 많이 발생.

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final UserRepository userRepository;

    public void save(BoardDTO boardDTO, String userId) throws IOException {

        User user = userRepository.findById(userId);

        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음.
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, user);
            boardRepository.save(boardEntity);
        } else {
            // 첨부 파일 있음.
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름 가져옴
                3. 서버 저장용 이름을 만듦
                // 내사진.jpg => 839798375892_내사진.jpg
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save 처리
                7. board_file_table에 해당 데이터 save 처리
             */
            MultipartFile boardFile = boardDTO.getBoardFile(); // 1.
            String originalFilename = boardFile.getOriginalFilename(); // 2.
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
            String savePath = "C:/springboot_img/" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
//            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // C:/springboot_img/9802398403948_내사진.jpg
            boardFile.transferTo(new File(savePath)); // 5.
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO, user);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get();

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }

    }

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        } // entity객체를 DTO로 변환을 하고 list에 담는 작업
        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) throws IOException {
        User user = userRepository.findById(boardDTO.getUserId());

        // 기존 게시글 불러오기
        BoardEntity existingBoard = boardRepository.findById(boardDTO.getId()).orElseThrow(() -> new RuntimeException("게시글 없음"));

        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음.
            BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO, user);
            boardRepository.save(boardEntity);
        } else {
            // 첨부 파일 있음.
            MultipartFile boardFile = boardDTO.getBoardFile();
            String originalFilename = boardFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = "C:/springboot_img/" + storedFileName;

            boardFile.transferTo(new File(savePath));

            BoardEntity boardEntity = BoardEntity.toUpdateFileEntity(boardDTO, user);
            boardRepository.save(boardEntity);

            // 기존 파일 삭제 및 새 파일 저장
            boardFileRepository.deleteByBoardEntityId(boardDTO.getId());
            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(boardEntity, originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }

        return findById(boardDTO.getId());
//        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
//        boardRepository.save(boardEntity);
//        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<BoardItemDTO> getBoardItemPage(String searchQuery, Pageable pageable) {
        return boardRepository.getBoardItemPage(searchQuery, pageable);
    }
}













