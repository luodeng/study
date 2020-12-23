package com.roden.study.examples.com.fasterxml.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 用户模型
 */
//@JsonIgnoreProperties(ignoreUnknown=true)忽略所有未知元素
@JsonIgnoreProperties({"extra"})
public class User {

    // 性别
    private Gender gender;
    // 姓名
    private Name name;

    private boolean isVerified;
    // 用户头像
    private byte[] userImage;

    // 用户生日
    @JsonSerialize(using = JacksonUtil.JsonLocalDateSerializer.class)
    @JsonDeserialize(using = JacksonUtil.JsonLocalDateDeserializer.class)
    private LocalDate birthday;

    // 用户登录时间
    @JsonSerialize(using = JacksonUtil.JsonLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JacksonUtil.JsonLocalDateTimeDeserializer.class)
    private LocalDateTime loginTime;


    // 不想输出此属性,使用此注解。
    @JsonIgnore
    private String note;

    // 性别
    public enum Gender {
        MALE, FEMALE
    }

    // 姓名
    public static class Name {

        private String first;

        // 别名
        //@JsonProperty("LAST")
        private String last;


        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "first='" + first + '\'' +
                    ", last='" + last + '\'' +
                    '}';
        }
    }


    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "gender=" + gender +
                ", name=" + name +
                ", isVerified=" + isVerified +
                ", userImage=" + Arrays.toString(userImage) +
                '}';
    }


}