package biz.tearoom6.mobile_ui_testing_android.uitest.helper;

import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import biz.tearoom6.mobile_ui_testing_android.uitest.model.User;

public class AssetHelper {

    public static User loadTestUser() {
        JSONObject json;
        try {
            json = new JSONObject(readFromAsset("data.json")).getJSONObject("testuser");
        } catch (JSONException | IOException e) {
            throw new IllegalStateException("Failed to load test data.", e);
        }
        return new User(json);
    }

    /**
     * Load asset file.
     * @param file file name
     * @return String file content
     * @throws IOException IOException
     */
    public static String readFromAsset(String file) throws IOException {
        AssetManager manager = InstrumentationRegistry.getContext().getAssets();

        BufferedReader br = new BufferedReader(new InputStreamReader(manager.open(file)));
        StringBuilder  sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        return sb.toString();
    }
}

