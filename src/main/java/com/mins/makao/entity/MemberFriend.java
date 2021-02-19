package com.mins.makao.entity;

import com.mins.makao.entity.enumeration.FriendStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "friendName", "status", "createdDate"})
public class MemberFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", unique = true, nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "friend_id", unique = true, nullable = false)
    private Member friend;

    private String friendName;

    @Enumerated(STRING)
    private FriendStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Builder
    private MemberFriend(Member member, Member friend, String friendName, FriendStatus status) {
        this.member = member;
        this.friend = friend;
        this.friendName = friendName;
        this.status = status;
    }

    /* 생성 메서드 */
    public static MemberFriend create(Member member, Member friend) {
        return new MemberFriend(member, friend, friend.getName(), FriendStatus.NORMAL);
    }

}