module ConfigHelper
  def caps
    caps = {
      automationName:   'XCUITest',
      platformName:     'iOS',
      deviceName:       ENV['DEVICE_NAME'] || 'iPhone 5s',
      app:              ENV['APP_PATH'] || "./apps/mobile-ui-testing-ios.#{ENV['UDID'] ? 'ipa' : 'app'}"
    }
    if ENV['UDID']
      caps['udid']            = ENV['UDID'] || 'auto'
      caps['xcodeOrgId']      = ENV['TEAM_ID'] || raise('TEAM_ID should be set')
      caps['xcodeSigningId']  = ENV['SIGNING_ID'] || 'iPhone Developer'
    else
      caps['platformVersion'] = ENV['IOS_VERSION'] || '10.3'
    end
    caps
  end
end