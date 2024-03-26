package com.airlines.catalog.controller;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/* create a class to implement methods for public key verification.
URL is provided as input
Add other mandatory methods to implement the interface . Handle all the exceptions.
*/
public class AwsCognitoRSAKeyProvider implements RSAKeyProvider {

    private final JwkProvider provider;

    public AwsCognitoRSAKeyProvider(String url) throws MalformedURLException {
        provider = new JwkProviderBuilder(new URL(url)).build();
    }

    @Override
    public RSAPublicKey getPublicKeyById(String kid) {
        try {
            return (RSAPublicKey) provider.get(kid).getPublicKey();
        } catch (JwkException e) {
            return null;
        }
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }
    @Override
    public String getPrivateKeyId() {
        return null;
    }
}