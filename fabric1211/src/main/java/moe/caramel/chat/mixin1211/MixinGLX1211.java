package moe.caramel.chat.mixin1211;

import moe.caramel.chat.wayland.WaylandPlatformPreference;
import com.mojang.blaze3d.platform.GLX;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.LongSupplier;

@Mixin(value = GLX.class, remap = false)
abstract class MixinGLX1211 {
    @Inject(
        remap = false,
        method = "_initGlfw()Ljava/util/function/LongSupplier;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/glfw/GLFW;glfwInit()Z",
            shift = At.Shift.BEFORE,
            remap = false
        ),
        require = 1
    )
    private static void caramelChat$selectLinuxGlfwPlatform(
        final CallbackInfoReturnable<LongSupplier> callback
    ) {
        if (!WaylandPlatformPreference.isLinux(System.getProperty("os.name"))) {
            return;
        }

        final String preference = System.getProperty("caramelchat.preferNativeWayland");
        final boolean waylandSupported = GLFW.glfwPlatformSupported(GLFW.GLFW_PLATFORM_WAYLAND);
        final boolean preferWayland = WaylandPlatformPreference.shouldPreferWayland(
            System.getProperty("os.name"),
            System.getenv("WAYLAND_DISPLAY"),
            preference,
            waylandSupported
        );
        final int platform = preferWayland ? GLFW.GLFW_PLATFORM_WAYLAND : GLFW.GLFW_PLATFORM_X11;
        GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, platform);
    }

}
