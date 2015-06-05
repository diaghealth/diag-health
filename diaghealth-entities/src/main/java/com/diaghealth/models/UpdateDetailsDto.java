package com.diaghealth.models;

import com.diaghealth.nodes.user.UserDetails;

public class UpdateDetailsDto {
	private UserDetails userDetails;
	private String oldPassword;
	private String newPassword;
	private String newPasswordRepeat;
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPasswordRepeat() {
		return newPasswordRepeat;
	}
	public void setNewPasswordRepeat(String newPasswordRepeat) {
		this.newPasswordRepeat = newPasswordRepeat;
	}
}
