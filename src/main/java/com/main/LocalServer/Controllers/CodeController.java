package com.main.LocalServer.Controllers;

import com.main.LocalServer.Services.CodeService;
import com.main.LocalServer.Entities.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/code")
@RequiredArgsConstructor
@CrossOrigin
public class CodeController {

    private final CodeService codeService;

    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(@RequestBody String email) {
        try {
            codeService.sendCode(email);
            return ResponseEntity.ok("Code sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage(), Error.ErrorType.UNEXPECTED.toString()));
        }
    }
}
