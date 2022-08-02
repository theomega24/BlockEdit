package dev.omega24.blockedit.util.exception;

import net.kyori.adventure.text.Component;

public abstract class ArgumentParseException extends Exception {

    public abstract Component getComponent();
}
