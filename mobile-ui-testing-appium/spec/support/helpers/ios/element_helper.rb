module ElementHelper
  def tab(name:)
    case name
    when 'User', 'Note'
      find_element(:accessibility_id, name)
    end
  end

  def note_titles_list
    find_element(:accessibility_id, 'notes_list_title').find_elements(:class, 'XCUIElementTypeStaticText')
  end

  def condition(id:)
    [:accessibility_id, id]
  end

end
