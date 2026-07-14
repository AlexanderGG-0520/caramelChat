# 1.2.3 — 2026-07-15

- Ported the Fabric and NeoForge builds to Minecraft 26.2 and Java 25; Fabric
  Loader 0.19.3 is the tested release loader.
- Added NeoForge 26.2 build support. Its release runtime validation remains
  pending, so no NeoForge artifact is published with 1.2.3.
- Fixed Linux Japanese IME input by selecting native GLFW Wayland instead of
  Minecraft's forced X11 platform on supported sessions.
- Delegated pre-edit, conversion candidates, commit delivery, and rendering to
  Minecraft 26.2 and LWJGL's native pipeline.
- Added `-Dcaramelchat.preferNativeWayland=auto|true|false`.
- Removed the legacy native, JNA, controller, wrapper, and custom-rendering IME
  implementations.
