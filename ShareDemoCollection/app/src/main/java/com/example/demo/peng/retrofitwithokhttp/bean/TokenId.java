package com.example.demo.peng.retrofitwithokhttp.bean;

/**
 * Created by pengwu on 16/7/4.
 */
public class TokenId {
    private String tokenId;
    private User user;

    public TokenId(String tokenId, User user) {
        this.tokenId = tokenId;
        this.user = user;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
