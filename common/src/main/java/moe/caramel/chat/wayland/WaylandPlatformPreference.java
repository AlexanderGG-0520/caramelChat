package moe.caramel.chat.wayland;

import java.util.Locale;

public final class WaylandPlatformPreference {
    private WaylandPlatformPreference() {}

    public static boolean shouldPreferWayland(String os, String display, String preference, boolean supported) {
        if (!supported) return false;
        String mode = preference == null ? "auto" : preference.toLowerCase(Locale.ROOT);
        return switch (mode) {
            case "true" -> true;
            case "false" -> false;
            case "auto" -> isLinux(os) && display != null && !display.isBlank();
            default -> isLinux(os) && display != null && !display.isBlank();
        };
    }

    public static boolean isInvalidPreference(String preference) {
        return preference != null && !switch (preference.toLowerCase(Locale.ROOT)) {
            case "auto", "true", "false" -> true;
            default -> false;
        };
    }

    private static boolean isLinux(String os) {
        return os != null && os.toLowerCase(Locale.ROOT).contains("linux");
    }
}
