package com.chubanova;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public class BattleBase {
    private static final List<Battle> BATTLES = new ArrayList<>();

    public UUID newBattle(List<String> users) {
        Battle battle = new Battle(users);
        BATTLES.add(battle);
        return battle.getId();
    }

    public Optional<Battle> getBattle(UUID battleId, String userId) {
        return BATTLES.stream()
                .filter(battle -> battle.getId().equals(battleId)
                        && battle.getUsersList().contains(userId))
                .findFirst();
    }
}
