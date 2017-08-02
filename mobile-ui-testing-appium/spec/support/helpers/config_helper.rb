require 'yaml'

module ConfigHelper
  module Platform
    def get_env
      platform = ENV['PLATFORM']
      raise('platform should be android|ios') unless %w[android ios].include?(platform)
      platform
    end
  end
  include Platform


  def user
    all_config['user']
  end

  def packagename
    all_config['packagename']
  end

  def all_config
    @config ||= YAML.load_file(config_file).freeze
  end

  def config_file
    File.join('./', 'config/asset.yml')
  end

  def capability
    {
      caps: caps,
      appium_lib: {
        wait: 10
      }
    }
  end
end
