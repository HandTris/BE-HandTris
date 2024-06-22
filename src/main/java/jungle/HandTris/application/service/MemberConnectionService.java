package jungle.HandTris.application.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemberConnectionService {

    private Set<String> connectedMembers = ConcurrentHashMap.newKeySet();

    public void addUser(String sessionId) {
        connectedMembers.add(sessionId);
    }

    public void removeUser(String sessionId) {
        connectedMembers.remove(sessionId);
    }

    public Set<String> getAllUsers() {
        return connectedMembers;
    }
}

