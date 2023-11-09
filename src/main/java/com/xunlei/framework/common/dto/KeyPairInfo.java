package com.xunlei.framework.common.dto;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyPair;

/**
 * 密钥对信息
 */
public class KeyPairInfo {

    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 私钥
     */
    private String privateKey;

    public static KeyPairInfo create(KeyPair keyPair) {
        KeyPairInfo pairInfo = new KeyPairInfo();
        pairInfo.setPublicKey(new String(Base64.encodeBase64(keyPair.getPublic().getEncoded())));
        pairInfo.setPrivateKey(new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded())));
        return pairInfo;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
