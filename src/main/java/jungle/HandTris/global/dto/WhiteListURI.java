package jungle.HandTris.global.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class WhiteListURI {
    public static final List<String> WhiteListURI = List.of("mypage", "favicon", "actuator", "ws", "app");
}
