require 'randexp-multibyte'

module NoteHelper
  include ElementHelper

  def random_title
    "Title: #{/[:japanese:]{10}/.gen}"
  end

  def random_content
    "Content: #{/[:japanese:]{10}/.gen}"
  end

  def create_note(title:, content:)
    click_note_tab

    # Click create button.
    click_button(id: 'notes_button_create')
    click_button(id: 'note_button_clear')

    # Input title & content.
    input_text(id: 'note_input_title', text: title)
    input_text(id: 'note_input_content', text: content)

    # Click save button.
    click_button(id: 'note_button_save')
  end

  def note_titles
    click_note_tab

    note_titles_list
  end

  def latest_note_title
    note_titles.first.text
  end

  def click_note_tab
    click_tab(name: 'Note')
  end
end
