package me.huar.jetpack_demo.config;


import me.huar.jetpack_demo.model.entry.UserInfoEntry;

public class UserConfig {

    private int id;
    private String username;
    private String nickname;
    private String mobile;
    private int level;
    private int gender;
    private String avatar_text;
    private String token;
    private int user_id;

    public UserConfig() {
    }

    public UserConfig(UserInfoEntry user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.mobile = user.getMobile();
        this.level = user.getLevel();
        this.gender = user.getGender();
        this.avatar_text = user.getAvatar_text();
        this.user_id = user.getUser_id();
    }

    public void update(UserInfoEntry user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.mobile = user.getMobile();
        this.level = user.getLevel();
        this.gender = user.getGender();
        this.avatar_text = user.getAvatar_text();
        this.user_id = user.getUser_id();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar_text() {
        return avatar_text;
    }

    public void setAvatar_text(String avatar_text) {
        this.avatar_text = avatar_text;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
