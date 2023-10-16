package com.chubanova;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
@Getter
public class Battle {

    private final UUID id;
    private final List<String> usersList;
    private final Queue<Command> commands;

    public Battle(List<String> usersList) {
        id = UUID.randomUUID();
        this.usersList = usersList;
        this.commands = new LinkedList<>();
    }
}

