package sql;
import java.sql.*;
public class Connect{
// variable declarations
    Connection oracle;
    Connection postgres;

    Connection currentConnection;

    String serverName;
    String postgreServer;

    String userName;
    String Password;

    String userPostgres;
    String postgresPassword;

    public Connect(){
        // set server
        this.setServerName();
        this.setPostgreServer();
        
        // set Username
        this.setUsername();
        this.setUserPostgres();
        
        // set Password
        this.setPassword();
        this.setPostgresPassword();
    }
// setters and getters
    public void setServerName(){
        this.serverName = "jdbc:oracle:thin:@localhost:1521:orcl";
    }
    public void setUsername(){
        this.userName = "sarobidy";
    }
    public void setPassword(){
        this.Password = "manoary";
    }

    public void setPostgreServer(){
        this.postgreServer = "jdbc:postgresql://localhost:5432/basketball";
    }
    public void setUserPostgres(){
        this.userPostgres = "sarobidy";
    }
    public void setPostgresPassword(){
        this.postgresPassword = "manoary";
    }

    public String getUsername(){
        return this.userName;
    }public String getServer(){
        return this.serverName;
    }
    public String getPassword(){
        return this.Password;
    }
    public String getUserPostgres(){
        return this.userPostgres;
    }
    public String getPostgreServer(){
        return this.postgreServer;
    }
    public String getPostgresPassword(){
        return this.postgresPassword;
    }
// open connections
    public void openOracle() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.oracle = DriverManager.getConnection(this.getServer(),this.getUsername(),this.getPassword());
    }
    public void openPostgres() throws Exception {
        Class.forName("org.postgresql.Driver");
        this.postgres = DriverManager.getConnection(this.getPostgreServer(),this.getUserPostgres(),this.getPostgresPassword());
    }

    public Connection getOracle() throws Exception{
        if( this.oracle == null ) this.openOracle();
        return oracle;
    }
    
    public Connection getPostgres() throws Exception{
        if( this.postgres == null ) this.openPostgres(); 
        return postgres;
    }
// close connections
    public void closeOracle() throws Exception {
        this.getOracle().close();
    }
    public void closePostgres() throws Exception {
        this.getPostgres().close();
    }

    public Connection getConnection(){
        return this.currentConnection;
    }

    public void close() throws SQLException{
        this.getConnection().close();
    }
    public void rollback() throws SQLException{
        this.getConnection().rollback();
    }
    public void commit() throws SQLException{
        this.getConnection().commit();
    }

    public void setConnection(Connection connection) throws Exception{
        this.currentConnection = connection;      
        this.currentConnection.setAutoCommit(false);
    }

    public void openConnection( String connectionName ) throws Exception {
        if( connectionName.equalsIgnoreCase("Oracle") ){
            this.currentConnection = this.getOracle();
        }else{
            this.currentConnection = this.getPostgres();
        }
        // this.currentConnection = 
    }
}    