package jungle.HandTris.presentation.dto.request;

public record TetrisMessageReq(String sender, String[][] board, boolean isEnd) {
}
