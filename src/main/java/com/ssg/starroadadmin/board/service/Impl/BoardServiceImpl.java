package com.ssg.starroadadmin.board.service.Impl;

import com.ssg.starroadadmin.board.dto.BoardCreateRequest;
import com.ssg.starroadadmin.board.dto.BoardListResponse;
import com.ssg.starroadadmin.board.dto.BoardResponse;
import com.ssg.starroadadmin.board.dto.SearchBoardRequest;
import com.ssg.starroadadmin.board.entity.Board;
import com.ssg.starroadadmin.board.entity.BoardImage;
import com.ssg.starroadadmin.board.repository.BoardImageRepository;
import com.ssg.starroadadmin.board.repository.BoardRepository;
import com.ssg.starroadadmin.board.repository.BoardRepositoryCustom;
import com.ssg.starroadadmin.board.service.BoardService;
import com.ssg.starroadadmin.global.error.code.BoardErrorCode;
import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.exception.BoardException;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.global.util.S3Uploader;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepositoryCustom boardRepositoryCustom;
    private final BoardImageRepository boardImageRepository;
    private final ComplexShoppingmallRepository complexShoppingmallRepository;
    private final ManagerRepository managerRepository;
    private final BoardRepository boardRepository;
    private final S3Uploader s3Uploader;

    /**
     * 게시물 목록 조회
     *
     * @param email
     * @param searchRequest
     * @param pageable
     * @return
     */
    @Override
    public Page<BoardListResponse> searchBoardList(String email, SearchBoardRequest searchRequest, Pageable pageable) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() == Authority.MALL) {
            return boardRepositoryCustom.findAllByMallManager(manager.getId(), searchRequest, pageable);
        } else if (manager.getAuthority() == Authority.ADMIN) {
            return boardRepositoryCustom.findAllCondition(manager.getId(), searchRequest, pageable);
        } else {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }
    }

    /**
     * 게시물 저장
     *
     * @param email
     * @param request
     */
    @Override
    public void createBoard(String email, BoardCreateRequest request) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        // 게시물 저장
        Board board = boardRepository.save(Board.builder()
                .title(request.title())
                .content(request.content())
                .category(request.category())
                .manager(manager)
                .build()
        );

        ComplexShoppingmall complexShoppingmall = complexShoppingmallRepository.findByManagerId(manager.getId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOPPINGMALL_NOT_FOUND));

        // 이미지 S3에 저장
        List<String> imagesURL = s3Uploader.upload(request.images(), "ssg/board/" + complexShoppingmall.getName() + "/board");

        // BoardImage 객체로 변환 후 저장
        List<BoardImage> list = imagesURL.stream().map(url -> BoardImage.builder()
                            .board(board)
                            .imagePath(url)
                            .build()
                )
                .toList();

        boardImageRepository.saveAll(list);
    }

    /**
     * 게시물 상세 조회
     *
     * @param email
     * @param boardId
     * @return
     */
    @Override
    public BoardResponse getBoardDetail(String email, Long boardId) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(BoardErrorCode.BOARD_NOT_FOUND));

        List<BoardImage> boardImages = boardImageRepository.findAllByBoardId(boardId);

        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getManager().getName())
                .contents(board.getContent())
                .category(board.getCategory())
                .images(boardImages.stream().map(BoardImage::getImagePath).toList())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();
    }
}
