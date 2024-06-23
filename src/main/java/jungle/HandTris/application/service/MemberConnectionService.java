package jungle.HandTris.application.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface MemberConnectionService {

    void addUser(String sessionId);

    void removeUser(String sessionId);

    Set<String> getAllUsers();

    String getFirstUser();
}

