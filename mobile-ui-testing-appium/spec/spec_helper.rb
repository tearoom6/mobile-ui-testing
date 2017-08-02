require 'rubygems'
require 'appium_lib'
require 'pry' if ENV['DEBUG']

SPEC_ROOT = File.expand_path(File.dirname(__FILE__))

require "#{SPEC_ROOT}/support/helpers/config_helper.rb"

include ConfigHelper
platform = Platform.get_env

# require platform-dependent support files.
case platform
when 'ios', 'android' then
  Dir[File.expand_path("support/helpers/#{platform}/*.rb", SPEC_ROOT)].each { |f| require f }
end

# require support files.
Dir[File.expand_path('support/helpers/*.rb', SPEC_ROOT)].each { |f| require f }

RSpec.configure do |config|
  include UserHelper
  include NoteHelper

  config.formatter = :documentation
  config.color = true

  config.before(:each) do
    @driver = Appium::Driver.new(capability)
    @driver.start_driver
    Appium.promote_appium_methods Object
  end

  config.after(:each) do |example|
    if example.exception
      @driver.screenshot "screenshots/#{example.metadata[:description]}.png"
    end
    @driver.driver_quit
  end
end

