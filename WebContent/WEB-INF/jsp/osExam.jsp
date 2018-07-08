<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<!-- Bootstrap -->
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.no-icons.min.css" rel="stylesheet">
	<!-- Icon font -->
	<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
	<!-- Custom styles -->
	<link rel="stylesheet" href="../css/styles.css">
	<link rel="stylesheet" href="../css/bookSystem.css" />

    <style>
        .a td{
            padding: 20px;
        }
    </style>
<table border="1" class="a">
    <tr>
        <td></td>
        <td>Max 最大需求矩阵</td>
        <td>Allocation 已分配矩阵</td>
        <td>Need 需求矩阵</td>
        <td>Available 可用资源</td>
    </tr>
    <tr>
        <td>进程\资源情况</td>
        <td>A&nbsp;&nbsp; &nbsp; B&nbsp; &nbsp; &nbsp; C</td>
        <td>A&nbsp;&nbsp; &nbsp; B&nbsp; &nbsp; &nbsp; C</td>
        <td>A&nbsp;&nbsp; &nbsp; B&nbsp; &nbsp; &nbsp; C</td>
        <td>A&nbsp;&nbsp; &nbsp; B&nbsp; &nbsp; &nbsp; C</td>
    </tr>
    
	<c:forEach begin="0" end="${n-1}" var="i">
	    <tr>
	        <td>进程${i}</td> 
	        <td>${max[i][0]}&nbsp;&nbsp; &nbsp; ${max[i][1]}&nbsp; &nbsp; &nbsp; ${max[i][2]}</td>
	        <td>${allocation[i][0]}&nbsp;&nbsp; &nbsp; ${allocation[i][1]}&nbsp; &nbsp; &nbsp; ${allocation[i][2]}</td>
	       	<td>${need[i][0]}&nbsp;&nbsp; &nbsp; ${need[i][1]}&nbsp; &nbsp; &nbsp; ${need[i][2]}</td>
	       	<c:if test="${i==0}">
	       		<td>${available[0]}&nbsp;&nbsp; &nbsp; ${available[1]}&nbsp; &nbsp; &nbsp; ${available[2]}</td>
	       	</c:if>
   		</tr>
	</c:forEach>
</table>