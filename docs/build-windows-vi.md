# Build iohkSU on Windows

## Prerequisites

- Android Studio with its bundled JDK 21.
- Android SDK Build Tools (provides `zipalign` and `apksigner`), the SDK platforms/build-tools required by Gradle, Android NDK and CMake versions declared by `manager/build.gradle.kts`.
- Python 3.11+ to use the repack scripts. If using JSON-with-comments configuration, install `json-with-comments`: `python -m pip install json-with-comments`.
- Rust Android toolchains only when you need to compile a replacement `ksud` binary. The Python repack operation itself expects it below `target/<triple>/<debug|release>/ksud`.

Set `ANDROID_SDK_ROOT` to the Android SDK location. For optional stripping, set `ANDROID_NDK_HOME` to the installed Android NDK location.

## Build manager APK

In the `manager` directory:

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:assembleRelease
```

Debug output is under `manager/app/build/outputs/apk/debug/`; release split and universal APKs are under `manager/app/build/outputs/apk/release/`.

A release build signing setup uses the values expected by the `apksign` Gradle plugin. Keep this file local and never commit it:

```properties
KEYSTORE_FILE=C:\secure\iohksu.jks
KEYSTORE_PASSWORD=...
KEY_ALIAS=...
KEY_PASSWORD=...
```

## Repack an APK with `ksud`

`repack_apk.py` takes the most recently built APK, injects the built `libksud.so` per ABI, aligns it for 16 KB pages, and signs it. Copy `repack-config.example.json` to ignored `repack-config.json`, then fill it with your local keystore data.

```powershell
Copy-Item repack-config.example.json repack-config.json
python .\repack_apk.py repack -c .\repack-config.json -b release -t release -a arm64-v8a
```

The result is written to `dist/`. For all APKs produced by Gradle, use:

```powershell
python .\repack_apk_multi.py repack -c .\repack-config.json -b release -t release
```

Verify the signed result:

```powershell
& "$env:ANDROID_SDK_ROOT\build-tools\<version>\apksigner.bat" verify --verbose --print-certs .\dist\<apk>.apk
```

> [!WARNING]
> Do not commit a keystore, passwords, or `repack-config.json`. APKs signed using a new key cannot update an APK signed with a different key.