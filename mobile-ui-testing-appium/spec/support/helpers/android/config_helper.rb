module ConfigHelper
  def caps
    {
      automationName:   'uiautomator2',
      platformName:     'Android',
      deviceName:       'Nexus', # On Android this capability is currently ignored, though it remains required.
      avd:              ENV['AVD'],
      app:              ENV['APP_PATH'] || './apps/mobile-ui-testing-android.apk',
      unicodeKeyboard:  true
    }
  end
end