<img src="common/src/main/resources/icon.png" width="96" alt="caramelChat Icon"/>

# caramelChat

Selects Minecraft's native Wayland input path on supported Linux Wayland sessions.

## What it does

Minecraft 26.2 provides a native Wayland text-input pipeline for vanilla
widgets. On 1.21.1, this mod only selects GLFW's native platform; it does not
add that newer text-input pipeline. When both X11 and Wayland are available,
Minecraft normally uses X11. caramelChat selects GLFW's platform immediately
before Minecraft initializes GLFW when a usable Linux Wayland session is
detected. Each Minecraft version has a separate Fabric module because 1.21.1
uses an obfuscated runtime and a different GLFW initialization flow.

The mod does not ship a native IME library, use JNA, replace GLFW callbacks,
or implement a second composition renderer. Minecraft and LWJGL remain
responsible for IME focus, pre-edit display, candidates, and committed text.

## Build compatibility

| Target | Minecraft | Java | Loader | LWJGL artifact / GLFW API |
| --- | --- | --- | --- | --- |
| Fabric | 1.21.1 | 21 | Fabric Loader 0.16.5+ | 3.3.3 / 3.4.0 |
| Fabric | 26.2 | 25 | Fabric Loader 0.19.3+ | 3.4.1 / 3.4.1 |
| NeoForge | 26.2 | 25 | NeoForge 26.2.0.13-beta | 3.4.1 / 3.4.1 |

Each module targets only the Minecraft version listed above. Untested game
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
nonblank and GLFW reports Wayland support. On Linux, `true` explicitly selects
the Wayland backend when it is included in the GLFW binary; this support check
does not test whether the current session's compositor accepts a Wayland
connection. If the backend cannot connect, GLFW initialization can fail.
`false` explicitly selects X11 on 1.21.1 and retains Minecraft's X11/XWayland
hint on 26.2.

No additional desktop toolkit, JNA installation, or native caramelChat binary
is required. If Minecraft's native Wayland pipeline fails on a different
compositor or IME, use `false` as a fallback and report the Minecraft, Java,
compositor, actual GLFW platform, Fcitx5, and Mozc versions.

## Contributing

All contributions are welcome. Run `./gradlew --no-daemon clean build` with
Java 25 selected; the 1.21.1 module compiles with Java 21 bytecode.

## License

caramelChat is licensed under GNU LGPLv3. See [LICENSE](LICENSE).
