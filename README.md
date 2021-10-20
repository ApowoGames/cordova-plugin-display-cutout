Support display cutouts for Cordova
===================================

Plugin for Cordova to perform Android display cutouts.

Released under MIT license.

Installation
------------

**Cordova**

`cordova plugin add cordova-plugin-display-cutout`


Code example
------------

Here are some examples on how you can use this plugin

```js
// Set the cutout mode:
DisplayCutout.setDisplayCutout(displayCutoutMode,successFunction, errorFunction);

// Get the display cutout (density adjusted):
DisplayCutout.getDisplayCutout(successFunction, errorFunction);

// Get the display cutout (raw pixels). Useful for canvas applications.
DisplayCutout.getDisplayCutoutUnadjusted(successFunction, errorFunction);
```
