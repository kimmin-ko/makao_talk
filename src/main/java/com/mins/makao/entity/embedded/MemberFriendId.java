package com.mins.makao.entity.embedded;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MemberFriendId implements Serializable {
    private static final long serialVersionUID = 4923442173702512730L;

    private Long memberId;
    private Long friendId;
}
