package com.example.vishnusports;

public class dataholder {

    public dataholder(String username, String gender, String email, String password, String gameOne, String gameTwo, String gameThree) {
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.gameOne = gameOne;
        this.gameTwo = gameTwo;
        this.gameThree = gameThree;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGameOne() {
        return gameOne;
    }

    public void setGameOne(String gameOne) {
        this.gameOne = gameOne;
    }

    public String getGameTwo() {
        return gameTwo;
    }

    public void setGameTwo(String gameTwo) {
        this.gameTwo = gameTwo;
    }

    public String getGameThree() {
        return gameThree;
    }

    public void setGameThree(String gameThree) {
        this.gameThree = gameThree;
    }

    String username,gender,email,password,gameOne,gameTwo,gameThree;
}
