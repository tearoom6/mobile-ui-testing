module ElementHelper
  def tab(name:)
    case name
    when 'User', 'Note'
      find_element(xpath: "//android.widget.TextView[@text=\"#{name}\"]")
    end
  end

  def note_titles_list
    find_element(:id, 'notes_list_title').find_elements(:class, 'android.widget.TextView')
  end

  def condition(id:)
    [:id, "#{packagename}:id/#{id}"]
  end
end
