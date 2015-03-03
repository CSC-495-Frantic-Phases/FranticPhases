package edu.oswego.franticphases.settings;

import edu.oswego.franticphases.settings.Settings.Setting;


public class SettingsUpdate {
	private final Setting name;
	private final boolean value;

	public SettingsUpdate(Setting name, boolean value) {
		this.name = name;
		this.value = value;
	}

	public Setting getSetting() {
		return name;
	}

	public boolean getValue() {
		return value;
	}
}
