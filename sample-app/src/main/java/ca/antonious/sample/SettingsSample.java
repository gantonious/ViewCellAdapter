package ca.antonious.sample;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import ca.antonious.viewcelladapter.ViewCellAdapter;
import ca.antonious.viewcelladapter.construction.SectionBuilder;
import ca.antonious.viewcelladapter.sections.Section;
import ca.antonious.viewcelladapter.viewcells.AbstractViewCell;
import ca.antonious.viewcelladapter.viewcells.builtins.MaterialLabelViewCell;
import ca.antonious.viewcelladapter.viewcells.builtins.MaterialToggleSettingViewCell;

/**
 * Created by George on 2017-04-26.
 */

public class SettingsSample extends BaseActivity {
    private ViewCellAdapter viewCellAdapter;
    private Section userSettings;
    private Section notificationSettings;

    private static final int USER_PROFILE_PRIVACY_SETTINGS_ID = 0;
    private static final int NOTIFIY_ON_COMMENT_SETTING = 1;
    private static final int NOTIFIY_ON_NEW_POST_SETTING = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewCellAdapter = buildAdapter();
        setupUserSettings();
        setupNotificationSettings();

        recyclerView.setAdapter(viewCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ViewCellAdapter buildAdapter() {
        userSettings = new Section();
        notificationSettings = new Section();

        return ViewCellAdapter.create()
            .section(
                SectionBuilder.wrap(userSettings)
                    .separateWithDividers()
                    .header(buildHeader("User Settings"))
                    .build()
            )
            .section(
                SectionBuilder.wrap(notificationSettings)
                    .separateWithDividers()
                    .header(buildHeader("Notification Settings"))
                    .build()
            )
            .listener(new MaterialToggleSettingViewCell.OnSettingToggledListener() {
                @Override
                public void onSettingToggled(int settingId, boolean isOn) {
                    showSnackbar("Setting " + settingId + " was set to " + isOn);
                }
            })
            .build();
    }

    private AbstractViewCell buildHeader(String headerText) {
        return MaterialLabelViewCell.create()
            .textColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            .label(headerText)
            .build();
    }

    private void setupUserSettings() {
        userSettings.add(buildUserProfilePrivacySetting());
    }

    private void setupNotificationSettings() {
        notificationSettings.add(buildCommentNotificationSetting());
        notificationSettings.add(buildNewPostNotificationSetting());
    }

    private AbstractViewCell buildUserProfilePrivacySetting() {
        return MaterialToggleSettingViewCell.create()
            .id(USER_PROFILE_PRIVACY_SETTINGS_ID)
            .name("Public Profile")
            .description("Allow anyone to access your profile")
            .build();
    }

    private AbstractViewCell buildCommentNotificationSetting() {
        return MaterialToggleSettingViewCell.create()
            .id(NOTIFIY_ON_COMMENT_SETTING)
            .name("Notify on Comment")
            .description("Get notifications pushed when someone comments on a post you created")
            .build();
    }

    private AbstractViewCell buildNewPostNotificationSetting() {
        return MaterialToggleSettingViewCell.create()
            .id(NOTIFIY_ON_NEW_POST_SETTING)
            .name("Notify when a post is created")
            .build();
    }

}
