import { NativeModules } from "react-native";
import Settings from "./settings";


const { RNAndroidSettings } = NativeModules;

const ANDROID_SETTINGS = Object.values(Settings);


/**
 * Navigate to the requested settings page on the device
 * @param {string} setting the requested Android setting key
 * @returns promise
 */
export default launchSettings = (setting) => {
    if (ANDROID_SETTINGS.includes(setting)) {
        return RNAndroidSettings.launchSettings(setting);
    } else return new Promise((resolve, reject) => reject({
        name: "KEY_ERROR",
        message: `${setting} is not a valid setting intent`
    }));

};