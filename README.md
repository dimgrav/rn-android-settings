# rn-android-settings

Native module for launching Android device settings from React Native.

2019 &copy; Dimitris Gravanis - <dimgrav@gmail.com>

## Install

[![npm version](https://badge.fury.io/js/rn-android-settings.svg)](https://badge.fury.io/js/rn-android-settings)

With npm:

```
npm i rn-android-settings
```

With yarn:

```
yarn add rn-android-settings
```

## Link

### Automatically

```
react-native link rn-android-settings
```

### Manually

Add to the following files:

`android/settings.gradle`

```groovy
// add these lines
include ":rn-android-settings"
project(":rn-android-settings").projectDir = new File(settingsDir, "../node_modules/rn-android-settings/android")
```

`android/app/build.gradle`

```groovy
dependencies {
    implementation project(":rn-android-settings")  // add this line
}
```

`android/app/src/main/java/.../MainApplication.java`

```java
import com.dimgrav.rnandroidsettings.RNAndroidSettingsPackage;  // add this line


public class MainApplication extends Application implements ReactApplication {

@Override
protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
            new MainReactPackage(),
            new RNAndroidSettingsPackage()  // add this line
    );
}
```

## Usage

The API provides aliases for all `ACTION_SETTINGS` intents referenced in the Android Developer [documentation](https://developer.android.com/reference/android/provider/Settings).

Examples:

```javascript
import { launchSettings, Settings } from "rn-android-settings";


launchSettings(Settings.SYSTEM)
    .then(success => console.log(success))
    .catch(error => console.log(error));

launchSettings(Settings.LOCATION)
    .then(...)
    .catch(...);

launchSettings(Settings.BLUETOOTH)
    .then(...)
    .catch(...);
```

