package za.co.wethinkcode.robotworlds.Server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * RecipeDbDemo shows how to read/write/update/delete data using the example Brewery Database.
 */
public class RecipeDbDemo
{
    public static final String DISK_DB_URL_PREFIX = "jdbc:sqlite:";
    public static final String SEPARATOR = "\t";

    public static void main( String[] args ) {
        final RecipeDbDemo app = new RecipeDbDemo( args );
    }

    private String dbUrl = null;

    private RecipeDbDemo( String[] args ) {
        processCmdLineArgs( args );
        try( Connection connection = DriverManager.getConnection( dbUrl )){ // <1>Other ways to get a Connection
            useTheDb( connection );
        }catch( SQLException e ){
            throw new RuntimeException( e );
        }
    }

    private void useTheDb( final Connection connection )
            throws SQLException
    {
        createData( connection );
        updateData( connection );
        deleteData( connection );

        readData( connection );
    }

    private void createData( final Connection connection )
            throws SQLException
    {
        try( final Statement stmt = connection.createStatement() ){
            boolean gotAResultSet = stmt.execute(
                    "INSERT INTO ingredients(name, type) VALUES (\"Munich\", \"malt\")" );
            if( gotAResultSet ){
                throw new RuntimeException( "Unexpectedly got a SQL resultset." );
            }else{
                final int updateCount = stmt.getUpdateCount();
                if( updateCount == 1 ){
                    System.out.println( "1 row INSERTED into ingredients" );
                }else{
                    throw new RuntimeException( "Expected 1 row to be inserted, but got " + updateCount );
                }
            }
        }
    }

    private void updateData( final Connection connection )
            throws SQLException
    {
        try( final Statement stmt = connection.createStatement() ){
            boolean gotAResultSet = stmt.execute( // return value should be `false` as before
                    "UPDATE products SET name = \"Sourflat IPA\" WHERE name = \"Lemondrop IPA\""
            );
            if( gotAResultSet ){
                throw new RuntimeException( "Unexpectedly got a SQL resultset." );
            }else{
                final int updateCount = stmt.getUpdateCount();
                if( updateCount == 1 ){
                    System.out.println( "1 row UPDATED in products" );
                }else{
                    throw new RuntimeException( "Expected 1 row to be inserted, but got " + updateCount );
                }
            }
        }
    }

    private void deleteData( final Connection connection )
            throws SQLException
    {
        try( final Statement stmt = connection.createStatement() ){
            boolean gotAResultSet = stmt.execute( // return value should be `false` as before
                    "DELETE FROM products WHERE id = 2"
            );
            if( gotAResultSet ){
                throw new RuntimeException( "Unexpectedly got a SQL resultset." );
            }else{
                final int updateCount = stmt.getUpdateCount();
                if( updateCount == 1 ){
                    System.out.println( "1 row DELETED from products" );
                }else{
                    throw new RuntimeException( "Expected 1 row to be inserted, but got " + updateCount );
                }
            }
        }
    }

    private void readData( final Connection connection )
            throws SQLException
    {
        try( final Statement stmt = connection.createStatement() ){
            boolean gotAResultSet = stmt.execute(
                    "SELECT p.name productname, i.type, i.name ingredname, r.qty, r.units "
                            + "FROM products p, recipe_quantities r, ingredients i "
                            + "WHERE "
                            + "        productname = 'Buffalo Bay Blonde'"
                            + "    AND p.id = r.product_id "
                            + "    AND r.ingredient_id = i.id"
            );
            if( ! gotAResultSet ){
                throw new RuntimeException( "Expected a SQL resultset, but we got an update count instead!" );
            }
            try( ResultSet results = stmt.getResultSet() ){
                int rowNo = 1;
                while( results.next() ){
                    final String productName = results.getString( "productname" );
                    final String ingredType = results.getString( "type" );
                    final String ingredName = results.getString( "ingredname" );
                    final int qty = results.getInt( "qty" );
                    final String units = results.getString( "units" );

                    final StringBuilder b = new StringBuilder( "Row " ).append( rowNo ).append( SEPARATOR )
                            .append( productName ).append( SEPARATOR )
                            .append( ingredType ).append( SEPARATOR )
                            .append( ingredName ).append( SEPARATOR )
                            .append( qty ).append( SEPARATOR )
                            .append( units );
                    System.out.println( b.toString() );
                }
            }
        }
    }

    private void processCmdLineArgs( String[] args ){
        if( args.length == 2 && args[ 0 ].equals( "-f" )){
            final File dbFile = new File( args[ 1 ] );
            if( dbFile.exists() ){
                dbUrl = DISK_DB_URL_PREFIX + args[ 1 ];
            }else{
                throw new IllegalArgumentException( "Database file " + dbFile.getName() + " not found." );
            }
        }else{
            throw new RuntimeException( "Expected arguments '-f filenanme' but didn't find it." );
        }
    }
}