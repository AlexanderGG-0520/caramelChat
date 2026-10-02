package moe.caramel.chat.mixin;

import moe.caramel.chat.util.ModLogger;
import moe.caramel.chat.wayland.WaylandPlatformPreference;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.LongSupplier;

@Mixin(targets = "com.mojang.blaze3d.platform.GLX")
abstract class MixinGLX {
    @Redirect(method = "_initGlfw", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwInitHint(II)V", ordinal = 0), require = 1)
    private static void caramelChat$preferWayland(final int hint, final int value) {
        final String os = System.getProperty("os.name");
        if (!WaylandPlatformPreference.isLinux(os)) {
            GLFW.glfwInitHint(hint, value);
            return;
        }

        String preference = System.getProperty("caramelchat.preferNativeWayland");
        boolean supported = GLFW.glfwPlatformSupported(GLFW.GLFW_PLATFORM_WAYLAND);
        boolean preferWayland = WaylandPlatformPreference.shouldPreferWayland(os, System.getenv("WAYLAND_DISPLAY"), preference, supported);
        int selected = preferWayland ? GLFW.GLFW_PLATFORM_WAYLAND : value;
        if (preferWayland) {
            ModLogger.log("Replaced Minecraft GLFW X11 platform hint with Wayland");
        } else if ("true".equalsIgnoreCase(preference) && !supported) {
            ModLogger.warn("Native Wayland was requested but GLFW does not support it; preserving Minecraft's platform choice");
        } else if (WaylandPlatformPreference.isInvalidPreference(preference)) {
            ModLogger.warn("Unknown caramelchat.preferNativeWayland value '{}'; using auto", preference);
        }
        GLFW.glfwInitHint(hint, selected);
    }

    @Inject(method = "_initGlfw", at = @At("TAIL"))
    private static void caramelChat$logSelectedPlatform(final CallbackInfoReturnable<LongSupplier> callback) {
        int platform = GLFW.glfwGetPlatform();
        String name = platform == GLFW.GLFW_PLATFORM_WAYLAND ? "Wayland" : platform == GLFW.GLFW_PLATFORM_X11 ? "X11" : Integer.toString(platform);
        ModLogger.log("GLFW platform: {}", name);
    }
}
