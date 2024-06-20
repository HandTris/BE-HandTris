package jungle.HandTris.presentation.controller;

import jungle.HandTris.application.service.MemberRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberRecordController {
    private final MemberRecordService memberRecordService;


}
