package object;

import java.sql.*;
import java.lang.reflect.*;
import java.util.Vector;
import java.time.*;
import sql.*;

public class BddObject{
    
    String table ;
    String primarykey;
    Object primaryValue;

    String prefix ;
    int pkLength = 7;
    String seqName ;

    public BddObject(){
    }
    
    public void setTable(String table){
        this.table = table;
    }
    public String getTable(){
        return this.table;
    }

    public void setPrimaryKey(String pK){
        this.primarykey = pK;
    }
    public String getPrimaryKey(){
        return this.primarykey;
    }

    public void setPrefix(String pK){
        this.prefix = pK;
    }
    public String getPrefix(){
        return this.prefix;
    }

    public void setPklength(int l){
        this.pkLength = l;
    }
    public int getLength(){
        return this.pkLength;
    }

    public void setSeqName(String seq){
        this.seqName = seq;
    }
    public String getSeqName(){
        return this.seqName;
    }



    public void setPrimaryValue(Object pV){
        this.primaryValue = pV;
    }
    public Object getPrimaryValue(){
        return this.primaryValue;
    }
// select
    public Vector<Object> select() throws Exception {
        String query = " Select * from " + this.getTable();
        return selectOperation(query);
    }

    public int getCurrentSequence( Connection c ) throws Exception{
        String query = "Select " + this.getSeqName() + "()";
        Statement statement = c.createStatement();
        try{
            ResultSet result = statement.executeQuery( query );
            while( result.next() ){
                return result.getInt(1);
            }
        } catch (Exception e){
            throw new Exception(" Une erreur s'est produite lors de l'execution de la requete ");
        }finally{
            statement.close();
        }
        return 0;
    }

    public String createPrimaryKey( Connection c ) throws Exception{
        int r = this.getCurrentSequence( c );
        int cc = this.getLength() - this.getPrefix().length() ;
        String e = stringify( r , cc );
        return this.getPrefix() + e;
    }

    public String stringify( int id , int caracterCount ){
        int e = (String.valueOf(id)).length();
        String tmp = "";
        for( int i = 1 ; i <= caracterCount - e ; i++ ){
            tmp += "0";
        }
        tmp += String.valueOf(id);
        return tmp;

    }

    public Vector<Object> selectOperation(String query) throws Exception{
        Vector<Object> list = new Vector<Object>();
        QueryStatement statement = new QueryStatement( query );
        try{
            statement.executeQuery();
            Result result = statement.getResult();
            Class<?> tempClass = this.getClass();
            while( result.getResult().next() ){
                list.add( this.createObject( result , tempClass ) );
            }
        }catch(Exception e){ 
            throw new Exception("Une erreur s'est produite ici : " + query);
        }
        finally{ statement.close(); }
        return list;
    }
    public Vector<Object> select( Object object ) throws Exception {
        String query = " Select * from " + this.getTable() + " where " + this.getPrimaryKey() + " = " + this.surround(object);
        return selectOperation(query);
    }
    // public Vector<Object> select(Object object , String... predicat)
    
// end select
    
    public Object createObject( Result result , Class<?> tempClass) throws Exception{
        Object temp = tempClass.getConstructor().newInstance();
        Field[] fs = tempClass.getDeclaredFields();
        for( int i = 1 , j = 0 ; i <= result.getCount() ; i++ , j++ ){
            Method method = tempClass.getMethod( "set" + toUpperFirst( fs[j].getName() ) , fs[j].getType() );
            Object tmp = this.getExactObject( fs[j] , result.getResult() , i );
            method.invoke( temp , tmp );
        }
        return temp;
    }

    public void insert() throws Exception{
        String query = "Insert into " + this.getTable() + " values ( ";
        String value = this.getValues();
        query += value + ")";
        QueryStatement statement = new QueryStatement( query );
        try{
            statement.executeUpdate();
            statement.commit();
        }
        catch( Exception e ){
            statement.getConnection().rollback();
            e.printStackTrace();
        }
    }

    public void insert(Connection connection) throws Exception{
        String query = "Insert into " + this.getTable() + " values ( ";
        String value = this.getValues();
        query += value + ")";
        QueryStatement statement = new QueryStatement( query );
        try{
            statement.setConnection(connection);
            statement.executeUpdate();
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }

    public boolean checkIfDateAndTime( Object field ) throws Exception{
        Class<?> fieldClass = field.getClass();
        boolean date = fieldClass.getSuperclass() == java.util.Date.class;
        boolean time = fieldClass == Time.class;
        return ( date || time );
    }
    
    public boolean checkIfDateAndTime( Field field ) throws Exception{
        boolean date = field.getType() == java.util.Date.class;
        boolean time = field.getType() == Time.class;
        return ( date || time );
    }

// update indray izao
    public void update( ) throws Exception {
        String query = "Update " + this.getTable() + " set ";
        String condition = " where " + this.getPrimaryKey() + " = " + this.surround( this.getPrimaryValue() );
        String value  = this.updateValue();
        query = query.concat( value ).concat( condition );
        try{
            QueryStatement statement = new QueryStatement( query );
            statement.executeUpdate();
            Method update = this.getClass().getMethod("init");
            update.invoke( this );
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String updateValue() throws Exception{
        String value = "";
        Field[] fields = this.getClass().getDeclaredFields();
        for( int i = 0 ; i < fields.length ; i++ ){
            Method method = this.getClass().getMethod( "get"+toUpperFirst(fields[i].getName()) );
            Object data =  this.surround( method.invoke(this) );
            String tmp = new String(fields[i].getName() + " = " + String.valueOf(data) + (( i != fields.length - 1 ) ? " , " : "") );
            value = value.concat(tmp);
        }
        return value;
    }

    public String getValues() throws Exception{
        String value = "";
        Field[] fields = this.getClass().getDeclaredFields();
        for( int i = 0 ; i < fields.length ; i++ ){
            Method method = this.getClass().getMethod( "get" + toUpperFirst( fields[i].getName() ) );
            Object data = this.surround( method.invoke( this ) );
            value += String.valueOf(data) ;
            if ( i != fields.length-1 )
                value += ",";
        }
        return value;
    }

    public Object surround( Object toSurround ){
        //  ---> si non de entourena amin'ny '' le objet
        String value = this.escape( String.valueOf( toSurround ) );
        if( toSurround.getClass() == Date.class ){
            String date = "TO_DATE('" + value + "','YYYY-MM-DD')";
            return date;
        }
        if( !checkIfNumber( toSurround ) ){
            String toreturn = "'" + value + "'";
            return toreturn;
        }
        return toSurround;
    }

    public boolean checkIfNumber( Field field ){ // for the fields
        boolean number = ( field.getType().getSuperclass() == Number.class);
        boolean numbers = ( field.getType() == Integer.TYPE || field.getType() == Double.TYPE || field.getType() == Float.TYPE );
        return ( number || numbers );
    }

    public boolean checkIfNumber( Object object ){ // for an object
        boolean number = ( object.getClass().getSuperclass() == Number.class);
        boolean numbers = ( object.getClass() == Integer.TYPE || object.getClass() == Double.TYPE || object.getClass() == Float.TYPE );
        return ( number || numbers );
    }

    public String toUpperFirst( String methods ){
        char[] chars = methods.toCharArray();
        chars[0] = Character.toUpperCase( chars[0] );
        return new String( chars );
    }

    public void delete(){
        String query = "delete from " + this.getTable();
        Object data = this.surround( this.primaryValue );
        String condition = " where " + this.getPrimaryKey() + " = " + String.valueOf(data);
        query = query.concat( condition );
        try{
            QueryStatement statement = new QueryStatement( query );
            statement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    } 
// okey
    public Object getExactObject( Field field , ResultSet result , int index ) throws Exception{
        if( this.checkIfNumber( field ) )
            return this.getNumberValue( field , result , index );            
        if( this.checkIfDateAndTime( field ) )
            return this.getDateOrTimeValue( field , result , index );
        return result.getString(index);
    }

    // result set no omena azy di any indice anle colone dia izay ihany 
    public Object getNumberValue( Field field , ResultSet result , int index ) throws Exception{
        if( field.getType() == Integer.TYPE )
            return result.getInt( index );
        else if( field.getType() == Double.TYPE)
            return result.getDouble( index );        
        return null;
    }
    
    public Object getDateOrTimeValue( Field field , ResultSet result , int index ) throws Exception{
        if( field.getType() == java.util.Date.class )
            return result.getDate( index );
        else if( field.getType() == Time.class )
            return result.getTime( index );
        return null;
    }

    // for the values
    public String escape( String toEscape ){
        return toEscape.replace("\\" , "\\\\")
                .replace("\t" , "\\t")
                .replace("\b" , "\\b")
                .replace("\r" , "\\r")
                .replace("\n" , "\\n")
                .replace("'" , "\'\'")
                .replace("\"" , "\\\"");
    }

    public String saveValue(){
        Field[] fields = this.getClass().getDeclaredFields();
        String str = "";
        try{
            for (Field f : fields){
                str += f.getName() + ":" + this.getClass().getMethod("get"+toUpperFirst( f.getName() )).invoke(this) + ";";
            }
        }catch(Exception e){ e.printStackTrace();}
        return str;
    }

    // to string function
    public String toString(){
        Field[] fields = this.getClass().getDeclaredFields();
        String str = "";
        try{
            for (Field f : fields){
                str += " " + this.toUpperFirst( f.getName() ) + " : " +(this.getClass().getMethod("get"+toUpperFirst( f.getName() )).invoke(this)) + " , ";
            }
        }catch(Exception e){ e.printStackTrace();}
        return str;
    }
}