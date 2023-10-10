package com.sburlyaev.cmd.plugin.model;

import java.io.File;

public record Environment(OperationSystem os,
                          String osVersion,
                          String gui) {

    public static Environment getEnvironment() {
        // todo: use com.intellij.openapi.util.SystemInfo
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String gui = System.getProperty("sun.desktop");

        OperationSystem os = OperationSystem.fromString(osName);

        return new Environment(os, osVersion, gui);
    }

    public Terminal getDefaultTerminal() {
        switch (os) {
            case WINDOWS -> {
                return Terminal.COMMAND_PROMPT;
            }
            case MAC_OS -> {
                return Terminal.MAC_TERMINAL;
            }
            case LINUX -> {
                if (gui != null && gui.toLowerCase().contains("gnome")) {
                    return Terminal.GNOME_TERMINAL;
                }
                if (new File(Terminal.GNOME_TERMINAL.getDefaultPath()).exists()) {
                    return Terminal.GNOME_TERMINAL;
                }
                // todo: KDE?
                return Terminal.KONSOLE;
            }
            default -> throw new IllegalArgumentException("os: " + os);
        }
    }

    @Override
    public String toString() {
        return "Environment{" +
            "os=" + os +
            ", osVersion='" + osVersion + '\'' +
            ", gui='" + gui + '\'' +
            '}';
    }

}
