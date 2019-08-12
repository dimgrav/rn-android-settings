export { default as Settings } from "./js/settings";

declare module "rn-android-settings" {
    const launchSettings: (setting: string) => Promise<Object>;
}