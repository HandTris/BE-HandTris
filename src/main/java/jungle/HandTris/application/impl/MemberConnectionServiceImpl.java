package jungle.HandTris.application.impl;

import jungle.HandTris.application.service.MemberConnectionService;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MemberConnectionServiceImpl implements MemberConnectionService {

    private Set<String> connectedMembers = ConcurrentHashMap.newKeySet();

    @Override
    public void addUser(String sessionId) {
        connectedMembers.add(sessionId);
    }

    @Override
    public void removeUser(String sessionId) {
        connectedMembers.remove(sessionId);
    }

    @Override
    public Set<String> getAllUsers() {
        return connectedMembers;
    }
}
