package sql;
import java.sql.*;
public class QueryStatement{
    String query;
    Connect connection;
    Result result;
    // supposons d'abord qu'Oracle fotsiny aloha ny connection
    public QueryStatement( String query ) throws Exception{
        this.setQuery( query );
        this.setConnection();
    }
    public QueryStatement() throws Exception{
        this.setConnection();
    }
    public void setConnection() throws Exception{
        this.connection = new Connect();
        this.getConnection().openConnection("Postgres");
    }
    public void setConnection(Connection connection) throws Exception{
        this.connection = new Connect();
        this.getConnection().setConnection(connection);
    }

    public void executeQuery() throws Exception{
        Statement statement = this.getConnection().getConnection().createStatement();    
        this.setResult( statement.executeQuery( this.getQuery() ) );
    }

    public void executeUpdate() throws Exception{
        Statement statement = this.getConnection().getConnection().createStatement();
        try{
            statement.executeUpdate( this.getQuery() );
            this.commit();
        }catch(Exception e){
            this.getConnection().rollback();
            e.printStackTrace();
        }
    }

    public void setResult(ResultSet resultset) throws Exception{
        this.result = new Result( resultset );
    }

    public void setConnection( Connect connex ){
        this.connection = connex;
    }
    public Connect getConnection(){
        return this.connection;
    }

    public Result getResult(){
        return this.result;
    }

    public void setQuery(String r){
        this.query = r.trim();
    }
    public String getQuery(){
        return this.query;
    }


    public void commit() throws SQLException{
        this.getConnection().commit();
    }

    public void close() throws Exception{
        this.getConnection().close();
    }
}