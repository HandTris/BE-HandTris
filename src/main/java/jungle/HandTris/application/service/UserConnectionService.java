package jungle.HandTris.application.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserConnectionService {

    private Set<String> connectedUsers = ConcurrentHashMap.newKeySet();

    public void addUser(String sessionId) {
        connectedUsers.add(sessionId);
    }

    public void removeUser(String sessionId) {
        connectedUsers.remove(sessionId);
    }

    public Set<String> getAllUsers() {
        return connectedUsers;
    }
}

