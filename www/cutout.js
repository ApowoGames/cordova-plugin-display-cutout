var run = require("cordova/exec");

var DisplayCutout = {
  // https://developer.android.com/reference/android/view/WindowManager.LayoutParams
  LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT: 0x00000000,
  LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS: 0x00000003,
  LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER: 0x00000002,
  LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES: 0x00000001,

  // Set the display cutout mode.
  setDisplayCutout: function (mode, success, error) {
    run(success, error, "DisplayCutouts", "setDisplayCutout", [mode || 0]);
  },
  // Get the display coutout returning density adjusted pixels.
  getDisplayCutout: function (success, error) {
    run(success, error, "DisplayCutouts", "getDisplayCutout", true);
  },
  // Get the display coutout returning raw pixel values.
  getDisplayCutoutUnadjusted: function (success, error) {
    run(success, error, "DisplayCutouts", "getDisplayCutout", false);
  },
};

module.exports = DisplayCutout;
