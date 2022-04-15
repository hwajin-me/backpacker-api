package com.idus.backpacker.api.kernel.application.command;

public interface Command<T extends CommandModel> {
    void invoke(T model);
}
