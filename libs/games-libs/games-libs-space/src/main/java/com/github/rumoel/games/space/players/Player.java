package com.github.rumoel.games.space.players;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

import com.github.rumoel.utils.RSAKeyPairGenerator;

import lombok.Getter;
import lombok.Setter;

public class Player implements Serializable {
	@Getter
	@Setter
	String uid;
	@Getter
	@Setter
	String userName;
	@Getter
	@Setter
	transient String privKey;
	@Getter
	@Setter
	String publicKey;

	public void genNew() throws NoSuchAlgorithmException {
		uid = UUID.randomUUID().toString();
		RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
		PublicKey pub = keyPairGenerator.getPublicKey();
		PrivateKey priv = keyPairGenerator.getPrivateKey();
		setPrivKey(Base64.getEncoder().encodeToString(pub.getEncoded()));
		setPublicKey(Base64.getEncoder().encodeToString(priv.getEncoded()));
	}
}
