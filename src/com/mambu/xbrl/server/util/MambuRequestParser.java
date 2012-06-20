package com.mambu.xbrl.server.util;

import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.google.gson.Gson;

public class MambuRequestParser {

	public static final String HMAC_TYPE = "hmacSHA256";

	public final static String SIGNED_REQUEST_PARAM = "signed_request";

	private String request;
	private String encodedSignature;
	private String payload;
	private HashMap<String, String> payloadMap;

	@SuppressWarnings("unchecked")
	public MambuRequestParser(HttpServletRequest req) {
		request = req.getParameter(SIGNED_REQUEST_PARAM);

		// split the results
		String[] split = request.split(".");

		encodedSignature = split[0];
		payload = split[1];

		// decode the payload
		byte[] decodedBase64 = Base64.decodeBase64(payload);
		String stringdecoded = String.valueOf(decodedBase64);

		// and store the resulting map
		this.payloadMap = new Gson().fromJson(stringdecoded, HashMap.class);
	}

	/**
	 * Check if the request was properly signed
	 * 
	 * @param secretKey
	 *            App secret key which is used to sign the request
	 * @return
	 */
	public boolean isValidRequest(String secretKey) {
		String hmacSha256 = hmacSha256(payload, secretKey);
		return encodedSignature.equalsIgnoreCase(hmacSha256);
	}

	/**
	 * Encoded a string with the HMac265 sha
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	private String hmacSha256(String value, String key) {
		try {
			// Get an hmac_sha1 key from the raw key bytes
			byte[] keyBytes = key.getBytes();
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_TYPE);

			// Get an hmac_sha256 Mac instance and initialize with the signing
			// key
			Mac mac = Mac.getInstance(HMAC_TYPE);
			mac.init(signingKey);

			// Compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(value.getBytes());

			// Convert raw bytes to Hex
			byte[] hexBytes = new Hex().encode(rawHmac);

			// Covert array of Hex bytes to a String
			return new String(hexBytes, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getPayloadParam(String key) {
		return payloadMap.get(key);
	}

	public HashMap<String, String> getPayloadMap() {
		return payloadMap;
	}

}
