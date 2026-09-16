# Hướng dẫn build iohkSU trên Windows

## 1. Chuẩn bị môi trường

Cài Android Studio (JDK 21 đi kèm), Android SDK Platform/Build Tools, CMake và Android NDK đúng phiên bản khai báo trong `manager/build.gradle.kts` (hiện là `29.0.14206865`). Cài Python 3.11+ để chạy script repack.

Thiết lập SDK cho project bằng `manager/local.properties` (file này chỉ dùng local, không commit):

```properties
sdk.dir=C:\\Users\\<ten-ban>\\AppData\\Local\\Android\\Sdk
```

Nếu dùng `repack-config.json` có comment, cài parser JSONC:

```powershell
python -m pip install json-with-comments
```

Đặt `ANDROID_SDK_ROOT` trỏ đến Android SDK. Nếu muốn `--strip` binary `ksud`, đặt thêm `ANDROID_NDK_HOME` trỏ đến NDK.

> [!IMPORTANT]
> Không commit keystore, mật khẩu, `manager/local.properties` hay `repack-config.json`.

## 2. Build bản default

Bản default có package cố định **`com.dwngkhoi.iohksu`**, dùng được luồng Check Stable Update/Check Beta Update của fork này.

Từ thư mục root project `D:\lumla\iohkSU`, vào thư mục `manager` rồi chạy:

```powershell
Push-Location .\manager
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:assembleRelease
Pop-Location
```

- APK debug: `manager\app\build\outputs\apk\debug\`
- APK release (APK tách ABI và universal): `manager\app\build\outputs\apk\release\`

Để tự ký release, khai báo trong `manager/gradle.properties` cục bộ:

```properties
KEYSTORE_FILE=C:\\secure\\iohksu.jks
KEYSTORE_PASSWORD=mat-khau-keystore
KEY_ALIAS=ten-alias
KEY_PASSWORD=mat-khau-key
```

## 3. Build bản spoofed (package ngẫu nhiên)

Bản spoofed thay package name bằng ba chuỗi chữ thường ngẫu nhiên (ví dụ `abcxyz.defghi.jklmno`) và thêm hậu tố `-spoofed` vào version. Do package khác mỗi lần build:

- Không cập nhật đè default hoặc một spoofed APK khác.
- Không dùng luồng Check Stable/Beta Update mặc định.
- Luôn build trong bản sao tạm, **không chạy randomizer trực tiếp trong source default**.

Từ root project, chạy script PowerShell sau:

```powershell
$source = 'D:\lumla\iohkSU\manager'
$work = Join-Path $env:TEMP 'iohksu-spoofed'
Remove-Item $work -Recurse -Force -ErrorAction SilentlyContinue
robocopy $source $work /E /XD .gradle build .cxx /XF local.properties /R:1 /W:1
if ($LASTEXITCODE -gt 7) { throw "robocopy thất bại: $LASTEXITCODE" }
Copy-Item "$source\local.properties" "$work\local.properties" -ErrorAction Stop
Push-Location $work
& 'C:\Program Files\Git\bin\bash.exe' .\randomizer
.\gradlew.bat :app:assembleDebug
Pop-Location
```

APK spoofed debug nằm tại:

```text
%TEMP%\iohksu-spoofed\manager\app\build\outputs\apk\debug\
```

Kiểm tra package thực tế vừa sinh:

```powershell
$apk = Get-ChildItem "$env:TEMP\iohksu-spoofed\manager\app\build\outputs\apk\debug\*.apk" | Select-Object -Last 1
& "$env:ANDROID_SDK_ROOT\build-tools\36.1.0\aapt.exe" dump badging $apk.FullName | Select-String '^package:'
```

## 4. Repack APK với `ksud`

Repack thay `libksud.so` theo ABI, chạy `zipalign` cho page size 16 KB rồi ký lại bằng `apksigner`.

1. Build APK Gradle trước.
2. Build `ksud` đúng ABI để có file tại `target/<triple>/<debug|release>/ksud`.
3. Tạo cấu hình cục bộ:

```powershell
Copy-Item .\repack-config.example.json .\repack-config.json
```

Điền `keystore_path`, `key_alias`, `keystore_pass`, `key_pass`, build type và ABI vào file vừa tạo.

Repack APK mới nhất:

```powershell
python .\repack_apk.py repack -c .\repack-config.json -b release -t release -a arm64-v8a
```

Repack toàn bộ APK Gradle output:

```powershell
python .\repack_apk_multi.py repack -c .\repack-config.json -b release -t release
```

APK đã ký được đặt tại `dist/`. Xác minh chữ ký:

```powershell
& "$env:ANDROID_SDK_ROOT\build-tools\36.1.0\apksigner.bat" verify --verbose --print-certs .\dist\<ten-apk>.apk
```

> [!WARNING]
> APK ký bằng key mới không thể update đè APK ký bằng key cũ. Giữ keystore phát hành ở nơi an toàn.