# Keycloak SSHA
Add salted SHA1 hashing OpenLDAP support to Keycloak. 
i.e. you're migrating to Keycloak and need to import legacy passwords stored as SHA-1 salted hashes in OpenLDAP.

## Requirements
- Java 17
- Maven 3.9.6

## Building

- Run `mvn package`
- JAR archive is generated in `./target/keycloak-ssha.jar`

## Deploying to Keycloak Container

1. Move the built JAR file to Keycloak's directory `/opt/keycloak/providers`.

## How to use
Use algorithm `ssha` when importing users through JSON. Below an example with
* rawPassword: `Hello123`
* hash: `81559ae5f35fa393f804f1ad89a199dd4a4912b5`
* salt `765a7f0e9daff310` (base64-encoded: `NzY1YTdmMGU5ZGFmZjMxMA==`)

JSON:

    {
      "realm": "master",
      "users": [
        {
          "username": "user1",
          "enabled": true,
          "totp": false,
          "emailVerified": true,
          "firstName": "user1",
          "lastName": "user1",
          "email": "user1@test.com",
          "credentials": [
            {
              "algorithm": "ssha",
              "hashedSaltedValue": "81559ae5f35fa393f804f1ad89a199dd4a4912b5",
              "salt": "NzY1YTdmMGU5ZGFmZjMxMA==",
              "hashIterations": 0,
              "type": "password"
            }
          ],
          "disableableCredentialTypes": [],
          "requiredActions": [],
          "realmRoles": [
            "offline_access",
            "uma_authorization"
          ],
          "clientRoles": {
            "account": [
              "manage-account",
              "view-profile"
            ]
          }
        }
      ]
    }
