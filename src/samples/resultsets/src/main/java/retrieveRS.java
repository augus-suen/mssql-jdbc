/*=====================================================================
File: 	 retrieveRS.java
Summary: This Microsoft JDBC Driver for SQL Server sample application
         demonstrates how to use a result set to retrieve a set of
         data from a SQL Server database.
---------------------------------------------------------------------
Microsoft JDBC Driver for SQL Server
Copyright(c) Microsoft Corporation
All rights reserved.
MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files(the ""Software""), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and / or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions :

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
=====================================================================*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class retrieveRS {

	public static void main(String[] args) {

		// Declare the JDBC objects.
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String serverName = null;
		String portNumber = null;
		String databaseName = null;
		String username = null;
		String password= null;

		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

			System.out.print("Enter server name: ");
			serverName = br.readLine();
			System.out.print("Enter port number: ");
			portNumber = br.readLine();
			System.out.print("Enter database name: ");
			databaseName = br.readLine();
			System.out.print("Enter username: ");
			username = br.readLine();	
			System.out.print("Enter password: ");
			password = br.readLine();

			// Create a variable for the connection string.
			String connectionUrl = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";" +
					"databaseName="+ databaseName + ";username=" + username + ";password=" + password + ";";

			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);

			createTable(con);

			// Create and execute an SQL statement that returns a
			// set of data and then display it.
			String SQL = "SELECT * FROM Product_JDBC_Sample;";
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			displayRow("PRODUCTS", rs);
		}

		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (rs != null) try { rs.close(); } catch(Exception e) {}
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
			if (con != null) try { con.close(); } catch(Exception e) {}
		}
	}

	private static void createTable(Connection con) throws SQLException {
		Statement stmt = con.createStatement();

		stmt.execute("if exists (select * from sys.objects where name = 'Product_JDBC_Sample')" +
				"drop table Product_JDBC_Sample" );

		String sql = "CREATE TABLE [Product_JDBC_Sample]("
				+ "[ProductID] [int] IDENTITY(1,1) NOT NULL,"
				+ "[Name] [varchar](30) NOT NULL,"
				+ "[ProductNumber] [nvarchar](25) NOT NULL,"
				+ "[MakeFlag] [bit] NOT NULL,"
				+ "[FinishedGoodsFlag] [bit] NOT NULL,"
				+ "[Color] [nvarchar](15) NULL,"
				+ "[SafetyStockLevel] [smallint] NOT NULL,"
				+ "[ReorderPoint] [smallint] NOT NULL,"
				+ "[StandardCost] [money] NOT NULL,"
				+ "[ListPrice] [money] NOT NULL,"
				+ "[Size] [nvarchar](5) NULL,"
				+ "[SizeUnitMeasureCode] [nchar](3) NULL,"
				+ "[WeightUnitMeasureCode] [nchar](3) NULL,"
				+ "[Weight] [decimal](8, 2) NULL,"
				+ "[DaysToManufacture] [int] NOT NULL,"
				+ "[ProductLine] [nchar](2) NULL,"
				+ "[Class] [nchar](2) NULL,"
				+ "[Style] [nchar](2) NULL,"
				+ "[ProductSubcategoryID] [int] NULL,"
				+ "[ProductModelID] [int] NULL,"
				+ "[SellStartDate] [datetime] NOT NULL,"
				+ "[SellEndDate] [datetime] NULL,"
				+ "[DiscontinuedDate] [datetime] NULL,"
				+ "[rowguid] [uniqueidentifier] ROWGUIDCOL  NOT NULL,"
				+ "[ModifiedDate] [datetime] NOT NULL,)";

		stmt.execute(sql);

		sql = "INSERT Product_JDBC_Sample VALUES ('Adjustable Race','AR-5381','0','0',NULL,'1000','750','0.00','0.00',NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL,'2008-04-30 00:00:00.000',NULL,NULL,'694215B7-08F7-4C0D-ACB1-D734BA44C0C8','2014-02-08 10:01:36.827') ";
		stmt.execute(sql);

		sql = "INSERT Product_JDBC_Sample VALUES ('ML Bottom Bracket','BB-8107','0','0',NULL,'1000','750','0.00','0.00',NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL,'2008-04-30 00:00:00.000',NULL,NULL,'694215B7-08F7-4C0D-ACB1-D734BA44C0C8','2014-02-08 10:01:36.827') ";
		stmt.execute(sql);

		sql = "INSERT Product_JDBC_Sample VALUES ('Mountain-500 Black, 44','BK-M18B-44','0','0',NULL,'1000','750','0.00','0.00',NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL,'2008-04-30 00:00:00.000',NULL,NULL,'694215B7-08F7-4C0D-ACB1-D734BA44C0C8','2014-02-08 10:01:36.827') ";
		stmt.execute(sql);
	}

	private static void displayRow(String title, ResultSet rs) {
		try {
			System.out.println(title);
			while (rs.next()) {
				System.out.println(rs.getString("ProductNumber") + " : " + rs.getString("Name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}