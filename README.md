# n3xPAL

## **[⬇ DOWNLOAD APK](https://github.com/SushantSundi/n3xpal/releases/latest/download/n3xPAL-v1.1.0.apk)**

**Android-to-Android screen mirroring client** — control and mirror one Android device from another over USB OTG or Wi-Fi.

Built and maintained by [SushantSundi](https://github.com/SushantSundi).  
Package ID: `com.sushantsundi.n3xPAL`

---

## Features

- **USB OTG & Wireless ADB** — connect via cable or over the same network
- **Three display scaling modes** — Fit (letterbox), Fill (crop-to-fill), Stretch (ignore aspect ratio)
- **Double-tap fullscreen toggle** — hide/show system bars instantly
- **Pinch-to-zoom** (1x-6x) with two-finger pan — purely local, no effect on the remote device
- **Three interaction modes** — Direct touch, Gesture touchpad, Touchpad with mouse buttons
- **Audio streaming** — AAC, Opus, FLAC or raw PCM from the remote device
- **MP4 recording** — record the mirrored session locally
- **Clipboard sync** — bi-directional clipboard between both devices
- **Remote shell** — run ADB shell commands from within the app
- **Persistent settings** — display mode, interaction mode and all options saved across sessions

## Requirements

| Device | Role | Requirement |
|---|---|---|
| Controller (e.g. tablet) | Runs n3xPAL | Android 8.0+ (API 26), USB OTG host support |
| Target (e.g. phone) | Mirrored device | USB debugging enabled, accept ADB authorization |

## Build

```bash
# Prerequisites: JDK 21, Android SDK platform 35 + build-tools 35
export JAVA_HOME=/path/to/jdk21

git clone https://github.com/SushantSundi/n3xpal.git
cd n3xpal

# Create local.properties with your SDK path
echo "sdk.dir=/path/to/android-sdk" > local.properties

./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```

## Install

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Usage

1. Connect the target phone to the controller tablet with a USB-C OTG cable
2. Open n3xPAL on the tablet — it auto-detects the phone and requests ADB authorization
3. Accept **"Allow USB debugging"** on the phone (the tablet appears as **n3xPAL Tablet**)
4. The phone screen appears on the tablet — use the **•••** button to access display and control settings

**Display modes** (accessible from the ••• menu):

| Mode | Behavior |
|---|---|
| Fit | Preserves phone aspect ratio with black bars |
| Fill | Crops edges to fill the tablet screen |
| Stretch | Fills screen, ignores aspect ratio |

**Double-tap** anywhere on the mirrored image to toggle system bars fullscreen.

## Wireless connection

1. Ensure both devices are on the same Wi-Fi network
2. Enable **Wireless debugging** on the phone (Developer options)
3. Enter the phone's IP and port in the Connection tab, tap Connect

## Credits

- **scrcpy server** `v3.3.4` — [Genymobile/scrcpy](https://github.com/Genymobile/scrcpy) (Apache-2.0, unmodified binary)
- **Android client base** — [sw3gmach1n3/Scrcpy_for_Android](https://github.com/sw3gmach1n3/Scrcpy_for_Android)
- **libadb-android** `3.1.1` — [MuntashirAkon/libadb-android](https://github.com/MuntashirAkon/libadb-android) (Apache-2.0)
- **Conscrypt** `2.5.3` — [google/conscrypt](https://github.com/google/conscrypt) (Apache-2.0)

## License

The n3xPAL client source code is released under the Apache License 2.0.  
See `app/src/main/assets/Apache-2.0.txt` and `app/src/main/assets/THIRD-PARTY-NOTICES.txt` for full details.
