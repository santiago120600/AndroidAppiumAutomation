# Appium Setup

## Prerequisites

* Install Appium using npm: `npm install -g appium`
* Install uiautomator2 using npm: `npm install uiautomator2`

## List Available Emulators
`emulator -list-avds`

`avdmanager list avd`

## Check Connected Devices
`adb devices`

## Start an emulator
`emulator -avd Pixel_3`

## Stop an emulator 
`adb -s Pixel_3 emu kill`

`adb emu kill`

`adb kill-server`