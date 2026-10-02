package moe.caramel.chat.wayland;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WaylandPlatformPreferenceTest {
    @Test
    void autoSelectsWaylandForASupportedLinuxWaylandSession() {
        assertTrue(WaylandPlatformPreference.shouldPreferWayland("Linux", "wayland-1", null, true));
    }

    @Test
    void autoPreservesMinecraftChoiceWithoutWaylandDisplay() {
        assertFalse(WaylandPlatformPreference.shouldPreferWayland("Linux", " ", "auto", true));
    }

    @Test
    void explicitTrueRequiresSupportedWayland() {
        assertTrue(WaylandPlatformPreference.shouldPreferWayland("Linux", null, "true", true));
        assertFalse(WaylandPlatformPreference.shouldPreferWayland("Linux", "wayland-1", "true", false));
    }

    @Test
    void explicitFalseAlwaysPreservesMinecraftChoice() {
        assertFalse(WaylandPlatformPreference.shouldPreferWayland("Linux", "wayland-1", "false", true));
    }

    @Test
    void malformedValueFallsBackToAuto() {
        assertTrue(WaylandPlatformPreference.shouldPreferWayland("Linux", "wayland-1", "unexpected", true));
        assertFalse(WaylandPlatformPreference.shouldPreferWayland("Linux", null, "unexpected", true));
    }

    @Test
    void explicitTrueStillRequiresLinux() {
        assertFalse(WaylandPlatformPreference.shouldPreferWayland("Windows 11", null, "true", true));
        assertFalse(WaylandPlatformPreference.shouldPreferWayland("Mac OS X", null, "true", true));
        assertFalse(WaylandPlatformPreference.shouldPreferWayland("Windows 11", "wayland-1", "auto", true));
    }

    @Test
    void identifiesInvalidPreferenceValues() {
        assertTrue(WaylandPlatformPreference.isInvalidPreference("unexpected"));
        assertFalse(WaylandPlatformPreference.isInvalidPreference("auto"));
        assertFalse(WaylandPlatformPreference.isInvalidPreference(null));
    }
}
