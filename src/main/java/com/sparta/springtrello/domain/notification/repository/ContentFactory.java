package com.sparta.springtrello.domain.notification.repository;

import com.sparta.springtrello.common.ErrorStatus;
import com.sparta.springtrello.common.exception.ApiException;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.board.repository.BoardRepository;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardRepository;
import com.sparta.springtrello.domain.card.util.CardFinder;
import com.sparta.springtrello.domain.comment.entity.Comment;
import com.sparta.springtrello.domain.comment.repository.CommentRepository;
import com.sparta.springtrello.domain.deck.entity.Deck;
import com.sparta.springtrello.domain.deck.repository.DeckRepository;
import com.sparta.springtrello.domain.member.entity.Member;
import com.sparta.springtrello.domain.member.repository.MemberRepository;
import com.sparta.springtrello.domain.notification.enums.NotificationCategory;
import com.sparta.springtrello.domain.workspace.entity.Workspace;
import com.sparta.springtrello.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentFactory {

    private StringBuffer sb = new StringBuffer();

    @Lookup
    private WorkspaceRepository getWorkspaceRepository() {
        return null;
    }

    @Lookup
    private BoardRepository getBoardRepository() {
        return null;
    }

    @Lookup
    private DeckRepository getDeckRepository() {
        return null;
    }

    @Lookup
    private CardRepository getCardRepository() {
        return null;
    }

    @Lookup
    private MemberRepository getMemberRepository() {
        return null;
    }

    @Lookup
    private CommentRepository getCommentRepository() { return null; }

    public Object getContentRepository(NotificationCategory category) {
        return switch (category) {
            case WORKSPACE -> getWorkspaceRepository();
            case BOARD -> getBoardRepository();
            case DECK -> getDeckRepository();
            case CARD -> getCardRepository();
            case MEMEBER -> getMemberRepository();
            default -> throw new ApiException(ErrorStatus.BAD_REQUEST_CONTENT_CATEGORY);
        };
    }

    public Object getContent(NotificationCategory category, Long id) {
        return switch (category) {
            case WORKSPACE -> getWorkspaceRepository()
                    .findById(id)
                    .orElseThrow(
                            () -> new ApiException(ErrorStatus.NOT_FOUND_WORKSPACE)
                    );
            case BOARD -> getBoardRepository()
                    .findById(id)
                    .orElseThrow(
                            () -> new ApiException(ErrorStatus.NOT_FOUND_BOARD)
                    );
            case DECK -> getDeckRepository()
                    .findById(id)
                    .orElseThrow(
                            () -> new ApiException(ErrorStatus.NOT_FOUND_DECK)
                    );
            case CARD -> getCardRepository()
                    .findById(id)
                    .orElseThrow(
                            () -> new ApiException(ErrorStatus.NOT_FOUND_CARD)
                    );
            case MEMEBER -> getMemberRepository()
                    .findJoinMemberById(id)
                    .orElseThrow(
                            () -> new ApiException(ErrorStatus.NOT_FOUND_MEMBER)
                    );
            case COMMENT -> getCommentRepository()
                    .findById(id)
                    .orElseThrow(
                            () -> new ApiException(ErrorStatus.NOT_FOUND_COMMENT)
                    );
            default -> throw new ApiException(ErrorStatus.BAD_REQUEST_CONTENT_CATEGORY);
        };
    }


    public String getMessage(String message, NotificationCategory category, Object content) {
        sb.append(message);

        return switch (category) {
            case WORKSPACE -> getWorkspaceInfo((Workspace) content);
            case BOARD -> getBoardInfo((Board) content);
            case DECK -> getDeckInfo((Deck) content);
            case CARD -> getCardInfo((Card) content);
            case MEMEBER -> getMemberInfo((Member) content);
            case COMMENT -> getCommentInfo((Comment) content);
            default -> throw new ApiException(ErrorStatus.BAD_REQUEST_CONTENT_CATEGORY);
        };
    }

    private String getCommentInfo(Comment content) {
        sb.append("\n카드 이름 : ");
        sb.append(content.getCard().getTitle());
        sb.append("\n댓글 내용 :");
        sb.append(content.getContents());
        sb.append("\n작성일 :");
        sb.append(content.getModifiedAt());

        return sb.toString();
    }

    private String getMemberInfo(Member member) {
        sb.append("\n멤버 닉네임 : ");
        sb.append(member.getUser().getNickname());
        sb.append("  | 유저 권한 : ");
        sb.append(member.getUser().getUserRole());
        sb.append("  |  멤버 권한 : ");
        sb.append(member.getMemberRole());

        return sb.toString();
    }

    private String getCardInfo(Card card) {
        sb.append("\n카드 이름 : ");
        sb.append(card.getTitle());

        return sb.toString();
    }

    private String getDeckInfo(Deck deck) {
        sb.append("\n덱 이름 : ");
        sb.append(deck.getName());

        return sb.toString();
    }

    private String getBoardInfo(Board board) {
        sb.append("\n보드 이름 : ");
        sb.append(board.getTitle());

        return sb.toString();
    }

    private String getWorkspaceInfo(Workspace workspace) {
        sb.append("\n워크스페이스 이름 : ");
        sb.append(workspace.getName());
        sb.append("\n관리자 : ");
        sb.append(workspace.getUser().getNickname());

        return sb.toString();
    }



}
