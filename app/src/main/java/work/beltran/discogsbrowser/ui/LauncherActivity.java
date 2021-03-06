package work.beltran.discogsbrowser.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import work.beltran.discogsbrowser.ui.login.LoginActivity;
import work.beltran.discogsbrowser.ui.main.MainActivity;
import work.beltran.discogsbrowser.ui.settings.Settings;

public class LauncherActivity extends Activity {

    private static final String TAG = LauncherActivity.class.getCanonicalName();

    @Inject
    public Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getAppComponent().inject(this);
        if (settings.getUserToken().isEmpty() || settings.getUserSecret().isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            ((App) getApplication()).initApiComponent(settings.getUserToken(), settings.getUserSecret());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Workaround for:
     * http://stackoverflow.com/questions/32169303/activity-did-not-call-finish-api-23
     */
    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }
}
