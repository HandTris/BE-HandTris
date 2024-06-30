//package jungle.HandTris.application.impl;
//
//import jungle.HandTris.application.service.MemberConnectionService;
//import jungle.HandTris.domain.exception.ConnectedMemberNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class MemberConnectionServiceImpl implements MemberConnectionService {
//
//    private Set<String> connectedMembers = ConcurrentHashMap.newKeySet();
//
//    @Override
//    public void addUser(String sessionId) {
//        System.out.println("Member Set:" + connectedMembers.toString());
//        System.out.println("Member Set Size:" + connectedMembers.size());
//        connectedMembers.add(sessionId);
//    }
//
//    @Override
//    public void
//    removeUser(String sessionId) {
//        System.out.println("Member Set:" + connectedMembers.toString());
//        System.out.println("Member Set Size:" + connectedMembers.size());
//        connectedMembers.remove(sessionId);
//    }
//
//    @Override
//    public Set<String> getAllUsers() {
//        return connectedMembers;
//    }
//
//    @Override
//    public String getFirstUser() {
//        return connectedMembers.stream().findFirst().orElseThrow(() -> new ConnectedMemberNotFoundException());
//    }
//
//    @Override
//    public Integer getRoomMemberCount() {
//        return connectedMembers.size();
//    }
//
//    @Override
//    public void clearUser(){
//        connectedMembers.clear();
//    }
//
//}