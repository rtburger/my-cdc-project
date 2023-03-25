# SQL Server Install
I decided to install SQL Server Database Developer License initially for local Dev before going to Azure Cloud. Here are the steps.

https://learn.microsoft.com/en-us/sql/linux/quickstart-install-connect-ubuntu?view=sql-server-ver16


## Install Error 1
https://gist.github.com/kstevenson722/3fff3a76b3f25d4693a2da53438f3341


The following packages have unmet dependencies:
mssql-server : Depends: libldap-2.4-2 but it is not installable
then

### Workaround 
```
wget http://ftp.us.debian.org/debian/pool/main/o/openldap/libldap-2.4-2_2.4.47+dfsg-3+deb10u7_amd64.deb

sudo dpkg -i libldap-2.4-2_2.4.47+dfsg-3+deb10u7_amd64.deb
```
## Intall Error 2
https://learn.microsoft.com/en-us/answers/questions/849599/unable-to-install-microsoft-sql-on-ubuntu-22-04

Error: cannot find libcrypto.so in any system library search paths. Ensure that a file or symbolic link named 'libcrypto.so' exists in the system library search paths. To resolve this error, a symbolic link to the 'libcrypto.so' library can be placed in the '/opt/mssql/lib/' directory.
Error: cannot find libssl.so in any system library search paths. Ensure that a file or symbolic link named 'libssl.so' exists in the system library search paths. To resolve this error, a symbolic link to the 'libssl.so' library can be placed in the '/opt/mssql/lib/' directory.
/opt/mssql/bin/sqlservr: PAL initialization failed. Error: 102

### Workaround
```
wget http://archive.ubuntu.com/ubuntu/pool/main/o/openssl/libssl1.1_1.1.0g-2ubuntu4_amd64.deb
sudo dpkg -i libssl1.1_1.1.0g-2ubuntu4_amd64.deb

$ cd /opt/mssql/lib
$ ls -la
$ sudo rm libcrypto.so libssl.so
$ sudo ln -s /usr/lib/x86_64-linux-gnu/libcrypto.so.1.1 libcrypto.so
$ sudo ln -s /usr/lib/x86_64-linux-gnu/libssl.so.1.1 libssl.1.1

```

# Enable CDC

Create user used in the sample
```
CREATE LOGIN debezium WITH PASSWORD = '<password>';

CREATE USER debezium FOR LOGIN debezium; 
GO
```
Make sure user has db_owner permissions
```
ALTER ROLE [db_owner] ADD MEMBER [debezium];
GO
```
Enable CDC on database 
```
EXEC sys.sp_cdc_enable_db
GO
```
Note:
sys.sp_cdc_enable_db creates the change data capture objects that have database wide scope, including meta data tables and DDL triggers. It also creates the cdc schema and cdc database user and sets the is_cdc_enabled column for the database entry in the sys.databases catalog view to 1.

https://learn.microsoft.com/en-us/sql/relational-databases/system-stored-procedures/sys-sp-cdc-enable-db-transact-sql?view=sql-server-ver16

Enable CDC on selected tables. 
Note: no filegroup change is required for Dev purposes.
```
EXEC sys.sp_cdc_enable_table 
@source_schema = N'dbo',
@source_name   = N'inventory',
@role_name = null,
@supports_net_changes = 0;
GO
```
Verify the CDC has been enabled for the selected tables
```
EXEC sys.sp_cdc_help_change_data_capture;
GO
```

Capture instance table
```
select * from cdc.dbo_inventory_CT;
```

# Error 3
SQLServerAgent is not currently running so it cannot be notified of this action.

### Workaround
```
/opt/mssql/bin/mssql-conf
add
[sqlagent]
enabled = true
errorlogfile = /opt/mssql/dba/srvmssql/log/sqlagent.log
errorlogginglevel = 4
startupwaitforalldb = 0
```

# useful
```
systemctl restart mssql-server
systemctl status mssql-server
sqlcmd -S localhost -U sa -P '<password>'
```


