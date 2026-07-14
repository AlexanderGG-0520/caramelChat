<img src="common/src/main/resources/icon.png" width="96" alt="caramelChat Icon"/>

# caramelChat

Selects Minecraft 26.2's native Wayland input path on supported Linux Wayland sessions.

## What it does

Minecraft 26.2 and its bundled LWJGL 3.4.1 provide the text-input focus,
pre-edit rendering, candidate rectangle, and committed-text pipeline for
vanilla widgets. When both X11 and Wayland are available, Minecraft normally
forces GLFW's X11 platform. caramelChat narrowly replaces that one platform
hint with Wayland when a usable Linux Wayland session is detected.

The mod does not ship a native IME library, use JNA, replace GLFW callbacks,
or implement a second composition renderer. Minecraft and LWJGL remain
responsible for IME focus, pre-edit display, candidates, and committed text.

## Tested build compatibility

| Component | Tested version |
| --- | --- |
| Minecraft | 26.2 |
| Java | 25 |
| Fabric Loader | 0.19.3 |
| NeoForge | 26.2.0.13-beta |
| LWJGL | 3.4.1 (bundled by Minecraft) |

Fabric and NeoForge build for exactly Minecraft 26.2. Untested future 26.x
versions are not implied to work. Legacy Forge, Quilt, historical native
libraries, and historical Windows/macOS/X11 backends are not active in this
build.

## Linux Wayland behavior

On CachyOS/Arch Linux with Hyprland, Fcitx5, and Mozc, native GLFW Wayland was
runtime-tested: Japanese composition and committed chat text worked through
Minecraft's own pipeline. XWayland remains available as a safe launch fallback;
that fallback was verified to launch, but this port makes no Japanese-IME claim
for it.

Set one JVM property before launching Minecraft to control platform selection:

```text
-Dcaramelchat.preferNativeWayland=auto
```

`auto` (the default) selects Wayland only on Linux when `WAYLAND_DISPLAY` is
nonblank and GLFW reports Wayland support. `true` requests Wayland whenever it
is supported. `false` preserves Minecraft's normal X11/XWayland choice.

No additional desktop toolkit, JNA installation, or native caramelChat binary
is required. If Minecraft's native Wayland pipeline fails on a different
compositor or IME, use `false` as a fallback and report the Minecraft, Java,
compositor, actual GLFW platform, Fcitx5, and Mozc versions.

## Contributing

All contributions are welcome. The active build instructions require Java 25;
run `./gradlew --no-daemon build` from a shell with that JDK selected.

## License

caramelChat is licensed under GNU LGPLv3. See [LICENSE](LICENSE).
