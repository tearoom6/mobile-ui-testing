require 'securerandom'

module UserHelper
  include ElementHelper

  def temporary_nickname
    "test#{SecureRandom.hex(5)}"
  end

  def temporary_email
    "#{SecureRandom.hex(6)}@example.com"
  end

  def temporary_password
    SecureRandom.hex(10)
  end

  def register_user(email: temporary_email, password: temporary_password, nickname: temporary_nickname)
    click_user_tab

    click_button(id: 'auth_button_openRegister')

    # Input email & password & nickname.
    input_text(id: 'auth_input_registerNickname', text: nickname)
    input_text(id: 'auth_input_registerEmail', text: email)
    input_text(id: 'auth_input_registerPassword', text: password)

    # Click register button.
    click_button(id: 'auth_button_register')

    wait_true {
      exists { find_element(*condition(id: 'auth_button_logout')) }
    }
  end

  def login_as_registered_user
    logout
    login(email: user['email'], password: user['password'])
  end

  def login(email:, password:)
    click_user_tab

    # Input email & password.
    input_text(id: 'auth_input_loginEmail', text: email)
    input_text(id: 'auth_input_loginPassword', text: password)

    # Click login button.
    click_button(id: 'auth_button_login')

    wait_true {
      exists { find_element(*condition(id: 'auth_button_logout')) }
    }
  end

  def logout
    click_user_tab
    click_button(id: 'auth_button_logout')

    wait_true {
      exists { find_element(*condition(id: 'auth_button_login')) }
    }
  end

  def error_unauthorized_message
    label(id: 'notes_label_unauthorized')
  end

  def click_user_tab
    click_tab(name: 'User')
  end
end
