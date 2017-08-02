require './spec/spec_helper'

describe 'Test as registered user.', type: :registered_user do
  before do
    login_as_registered_user
  end

  context 'Use Note page after login.' do
    before do
      click_note_tab
    end

    it 'There are already multiple notes.' do
      expect(note_titles.size).to be >= 1
    end

    context 'Create a new note.' do
      let(:title) { random_title }
      let(:content) { random_content }

      before do
        create_note(title: title, content: content)
      end

      it 'Created note can be found in list.' do
        expect(latest_note_title).to eq title
      end
    end
  end
end

describe 'Test as new user.', type: :new_user do
  before do
    logout
  end

  context 'Use Note page before registering user.' do
    before do
      click_note_tab
    end

    it 'Error message is shown.' do
      expect(error_unauthorized_message).not_to be_nil
    end

    context 'Register user & Use Note page.' do
      context 'Register user.' do
        before do
          register_user
          click_note_tab
        end

        it 'There is no note.' do
          expect(note_titles.size).to be == 0
        end

        context 'Create a new note.' do
          let(:title) { random_title }
          let(:content) { random_content }

          before do
            click_note_tab
            create_note(title: title, content: content)
          end

          it 'Created note can be found in list.' do
            expect(latest_note_title).to eq title
          end
        end
      end
    end
  end
end

