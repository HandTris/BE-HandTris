package jungle.HandTris.webSocket;

import jungle.HandTris.application.impl.MemberConnectionServiceImpl;
import jungle.HandTris.application.service.MemberConnectionService;
import jungle.HandTris.domain.exception.ConnectedMemberNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // MockitoExtension 사용
class MockMemberConnectionServiceTest {

    private MemberConnectionService memberConnectionService;

    @BeforeEach
    void setUp() {
        memberConnectionService = new MemberConnectionServiceImpl();
    }

    @Test
    void addUser() {
        // given
        String sessionId = "user1";

        // when
        memberConnectionService.addUser(sessionId);

        // then
        Set<String> allUsers = memberConnectionService.getAllUsers();
        assertTrue(allUsers.contains(sessionId));
    }

    @Test
    void removeUser() {
        // given
        String sessionId = "user1";
        memberConnectionService.addUser(sessionId);

        // when
        memberConnectionService.removeUser(sessionId);

        // then
        Set<String> allUsers = memberConnectionService.getAllUsers();
        assertFalse(allUsers.contains(sessionId));
    }

    @Test
    void getAllUsers() {
        // given
        String sessionId1 = "user1";
        String sessionId2 = "user2";
        memberConnectionService.addUser(sessionId1);
        memberConnectionService.addUser(sessionId2);

        // when
        Set<String> allUsers = memberConnectionService.getAllUsers();

        // then
        assertTrue(allUsers.contains(sessionId1));
        assertTrue(allUsers.contains(sessionId2));
    }

    @Test
    void getFirstUser() {
        // given
        String sessionId1 = "user1";
        String sessionId2 = "user2";
        memberConnectionService.addUser(sessionId1);
        memberConnectionService.addUser(sessionId2);

        // when
        String firstUser = memberConnectionService.getFirstUser();

        // then
        assertEquals(sessionId1, firstUser);
    }

    @Test
    void getFirstUser_notFound() {
        // when & then
        assertThrows(ConnectedMemberNotFoundException.class, () -> memberConnectionService.getFirstUser());
    }

    @Test
    void getRoomMemberCount() {
        // given
        String sessionId1 = "user1";
        String sessionId2 = "user2";
        memberConnectionService.addUser(sessionId1);
        memberConnectionService.addUser(sessionId2);

        // when
        int memberCount = memberConnectionService.getRoomMemberCount();

        // then
        assertEquals(2, memberCount);
    }

    @Test
    void clearUser() {
        // given
        String sessionId1 = "user1";
        String sessionId2 = "user2";
        memberConnectionService.addUser(sessionId1);
        memberConnectionService.addUser(sessionId2);

        // when
        memberConnectionService.clearUser();

        // then
        Set<String> allUsers = memberConnectionService.getAllUsers();
        assertTrue(allUsers.isEmpty());
    }
}

