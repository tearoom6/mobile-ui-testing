module ElementHelper

  def click_tab(name:)
    wait_true {
      exists { tab(name: name) }
    }

    tab(name: name).click
  end

  def if_exists(*args)
    if exists { find_element(*args) }
      yield find_element(*args)
    end
  end

  def input_text(id:, text:)
    clear_text(id: id)
    find_element(*condition(id: id)).send_keys(text)
  end

  def clear_text(id:)
    if_exists(*condition(id: id)) { |elem| elem.clear }
  end

  def click_button(id:)
    if_exists(*condition(id: id)) { |elem| elem.click }
  end

  def label(id:)
    find_element(*condition(id: id)).text
  end

end