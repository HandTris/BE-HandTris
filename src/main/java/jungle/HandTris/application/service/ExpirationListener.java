package jungle.HandTris.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExpirationListener implements MessageListener {

    private final MemberService memberService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString(); // "member:{name}:session"
        String memberName = expiredKey.split(":")[1];
        log.info("Session expired: {}", memberName);
    }
}
