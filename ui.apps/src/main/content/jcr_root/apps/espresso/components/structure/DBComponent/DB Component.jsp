<%--

  db_comp component.



--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %><%
%><%@page import="com.day.commons.datasource.poolservice.DataSourcePool"%><%
%><%@ page import="javax.sql.DataSource"%><%
%><%@ page import="java.sql.Connection"%><%
%><%@ page import="java.sql.SQLException"%><%
%><%@ page import="java.sql.Statement"%>
<%
%><%@ page import="java.sql.ResultSet"%>
<%
%><%@ page import="java.sql.PreparedStatement"%><%
%><%@ page import="java.sql.DriverManager"%>
DataSourcePool dspService = sling.getService(DataSourcePool.class); 
try{ 

DataSource ds = (DataSource)dspService.getDataSource("mydata_pool"); if(ds != null) { %>
<p>Obtained the datasource!</p>
<%
%>
<%
out.println("Trying to get connection Connection done");
final Connection connection = ds.getConnection();
out.println("Connection done");
final Statement statement = connection.createStatement();
out.println("Create Statement done");
final ResultSet resultSet = statement.executeQuery("SELECT * FROM `student`"); 
int r=0;
while(resultSet.next()){
r=r+1;
} 
resultSet.close();
%><p>
	Number of results:
	<%=r%></p>
<%
	} 
}
catch(Exception e)
	{
%><p>
	error!<%=e.getMessage()%></p>
<%
	}
