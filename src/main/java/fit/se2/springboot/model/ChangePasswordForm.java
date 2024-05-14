package fit.se2.springboot.model;

public class ChangePasswordForm {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    // Getters and setters for currentPassword
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    // Getters and setters for newPassword
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    // Getters and setters for confirmNewPassword
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}


