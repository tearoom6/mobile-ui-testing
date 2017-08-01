package biz.tearoom6.mobile_ui_testing_android.uitest.model;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;

@Getter
public class User {

    private final String nickname;
    private final String email;
    private final String password;

    public User(JSONObject json) {
        try {
            this.nickname = json.getString("nickname");
            this.email = json.getString("email");
            this.password = json.getString("password");
        } catch (JSONException e) {
            throw new IllegalStateException("Failed to create an instance : " + json.toString(), e);
        }
    }
}
