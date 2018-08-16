<img src="https://image.ibb.co/iCvjc5/Pivotal_My_SQLWeb_BLOG.png" height="120" width="350"/>

PivotalMySQLWeb is a free Pivotal open source project, intended to handle the administration of a Pivotal MySQL Service
Instance over the Web. PivotalMySQLWeb supports a wide range of operations on a Pivotal MySQL Service Instance such as
managing tables, views, indexes which can all be performed via the user interface, while you still have the ability to
directly execute any number of SQL statements

It includes the following capabilities :

<ul>
    <li>Multiple Command SQL worksheet for DDL and DML</li>
    <li>Run Explain Plan across SQL Statements</li>
    <li>View/Run DDL command against Tables/Views/Indexes/Constraints</li>
    <li>Command History</li>
    <li>Auto Bind to Pivotal MySQL Services bound to the Application within Pivotal Cloud Foundry (PCF)</li>
    <li>Manage JDBC Connections</li>
    <li>Load SQL File into SQL Worksheet from Local File System</li>
    <li>SQL Worksheet with syntax highlighting support</li>
    <li>HTTP GET request to auto login without a login form</li>
    <li>Export SQL query results in JSON or CSV formats</li>
</ul>

![alt tag](https://image.ibb.co/kxYJLk/piv_mysqlweb1.png)


<h3>Deploy to Pivotal Cloud Foundry</h3>

To deploy to Pivotal Cloud Foundry you must bind the application to a Pivotal MySQL service instance so it automatically connects
to the MySQL instance as shown in the sample manifest below.

```
applications:
- name: pivotal-mysqlweb
  memory: 1024M
  instances: 1
  random-route: true
  path: ./target/PivotalMySQLWeb-0.0.1-SNAPSHOT.jar
  services:
    - pas-mysql-dedicated-v2
```

Push to PCF using

```
$ cf push -f manifest.yml
```

<h3>Screen Shots</h3>

![alt tag](https://image.ibb.co/kKG6rF/piv_mysqlweb3.png)

![alt tag](https://image.ibb.co/f9rZdv/piv_mysqlweb4.png)

![alt tag](https://image.ibb.co/bWG0Jv/piv_mysqlweb5.png)

![alt tag](https://image.ibb.co/bBCJ5a/piv_mysqlweb6.png)

<h3>SQL Worksheet - Max Records to Display</h3>

You can control the number of records to display in the "SQL Worksheet" using the "Preferences" page. To do that follow these steps:

1. On the top menu bar select "Menu -> Preferences"
2. Set the value for "Max Records in Worksheet" to the value you require it should be more then 30 by default unless it was changed prior to deployment
3. Click "Update Preferences"

Alternatively you can also set that at deployment to use a default value by editing "main/resources/preferences.properties" and setting the property below.

```
maxRecordsinSQLQueryWindow=500
```

<h3>Security - SSO Token Authentication</h3>

By default this application is using UAA via the Single Sign-On PCF tile to protect every endpoint. Configuration options can be passed via environment variables defined in `manifest.yml`. You will need to bind to an SSO service instance either in the `manifest.yml` or after deployment through Apps Manager.

```
env:
     SKIP_SSL_VALIDATION: "true"

     # The location of the deployed resource server sample application
     # RESOURCE_URL: https://resource-server-sample.<your-domain>.com

     # Grant type to be set for the application's UAA client configurations. Only one grant type per application is supported by the SSO service.
     GRANT_TYPE: authorization_code

     # Identity provider(s) to be set for the application's client configurations
     SSO_IDENTITY_PROVIDERS: uaa

     # The following are bootstrap configurations you may use to automatically create client configurations in the SSO service for your application if the configurations do not exist. These configurations take effect when binding or rebinding to the SSO, and will overwrite existing client configurations if any. The values provided below are examples.

     # Whitelist of redirect URI(s) allowed for the application. This value must start with http:// or https://
     # SSO_REDIRECT_URIS: https://my-domain-here.domain.org

     # Client scope(s) for the application, not used for client credentials grant type
     SSO_SCOPES: openid

     # Client scope(s) for the application that are automatically authorized when acting on behalf of a user
     # SSO_AUTO_APPROVED_SCOPES: openid, todo.read

     # Client authorities for the application, only used for client credentials grant type 
     # SSO_AUTHORITIES: openid, uaa.resource, todo.read, todo.write

     # List of groups a user must have in order to authenticate successfully for the application
     SSO_REQUIRED_USER_GROUPS: CHANGE_ME

     # Lifetime in seconds of the application's access token
     # SSO_ACCESS_TOKEN_LIFETIME: 300

     # Lifetime in seconds of the application's refresh token
     # SSO_REFRESH_TOKEN_LIFETIME: 1800

     # Resource(s) that the application will use as scopes/authorities to be created if they do not already exist during bootstrapping
     # SSO_RESOURCES: 

     # Application icon with the application name and launch URL that will be displayed on the Pivotal Account dashboard if configured to show
     # SSO_ICON: <base64 encoded image - do not exceed 64kb>
     # SSO_LAUNCH_URL: <url>
     # SSO_SHOW_ON_HOME_PAGE: <true/false>
```



<hr />
<h3>Credits</h3>

This project is a combination of https://github.com/pivotal-cf/identity-sample-apps and https://github.com/pivotal-cf/PivotalMySQLWeb
