package io.github.ristekusdi;

import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class SSHAHashProvider implements PasswordHashProvider {

    private final String providerId;
    public static final String ALGORITHM = "SHA-1";

    public SSHAHashProvider(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public boolean policyCheck(PasswordPolicy passwordPolicy, PasswordCredentialModel passwordCredentialModel) {
        return this.providerId.equals(passwordCredentialModel.getPasswordCredentialData().getAlgorithm());
    }

    @Override
    public PasswordCredentialModel encodedCredential(String rawPassword, int iterations) {
        return null;
    }

    @Override
    public String encode(String rawPassword, int iterations) {
        return "";
    }

    @Override
    public boolean verify(String rawPassword, PasswordCredentialModel passwordCredentialModel) {
        String hashedPassword = "";
        String hash = passwordCredentialModel.getPasswordSecretData().getValue();
        String strSalt = new String(passwordCredentialModel.getPasswordSecretData().getSalt(), StandardCharsets.UTF_8);

        // Convert hex salt to bytes
        byte[] saltBytes = hexStringToByteArray(strSalt);

        // Concatenate the plain password and salt bytes
        byte[] passwordWithSaltBytes = concatenateByteArrays(rawPassword.getBytes(), saltBytes);

        try {
            // Create a MessageDigest instance for SHA-1
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);

            // Generate the hash value
            byte[] hashedBytes = messageDigest.digest(passwordWithSaltBytes);

            // Convert the byte array to a hexadecimal string
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            hashedPassword = stringBuilder.toString();
        } catch (Exception e) {
            // Fail silently
        }

        System.out.println("rawPassword = " + rawPassword);
        System.out.println("hash = " + hash);
        System.out.println("hashedPassword = " + hashedPassword);
        System.out.println("str salt = " + strSalt);
        System.out.println("byte salt = " + Arrays.toString(passwordCredentialModel.getPasswordSecretData().getSalt()));

        return hashedPassword.equals(hash);
    }

    private byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    // Method to convert a hexadecimal string to a byte array
    private byte[] hexStringToByteArray(String saltHex) {
        int len = saltHex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(saltHex.charAt(i), 16) << 4)
                    + Character.digit(saltHex.charAt(i + 1), 16));
        }
        return data;
    }

    @Override
    public void close() {

    }
}
