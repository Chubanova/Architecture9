package com.chubanova;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StartGameController {

    private final BattleBase battleBase;

    @PostMapping("/battle")
    public ResponseEntity<StartGameResponse> newGame(@RequestBody StartGameRequest newGameRequest) {
        UUID battleId = battleBase.newBattle(newGameRequest.getUsers());
        return ResponseEntity.ok(new StartGameResponse(battleId));
    }

    @PostMapping("/battle/{battleId}")
    public ResponseEntity<String> sendCommand(@PathVariable UUID battleId,
                                              @RequestBody SendCommandRequest sendCommandRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        Battle battle = (Battle) battleBase.getBattle(battleId, currentUser)
                .orElseThrow(IllegalArgumentException::new);
        battle.getCommands().add(sendCommandRequest.toCommand());
        return ResponseEntity.ok("Команда принята");
    }
}
