<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=path %>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<!--  PAGINATION plugin -->
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/css/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/js/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/js/localization/en.js"></script>

<script type="text/javascript">

	$(function(){
		
	$('.myDate').datetimepicker({
			  language: 'zh-CN',//显示中文
			  format: 'yyyy-mm-dd',//显示格式
			  minView: "month",//设置只显示到月份,可选的最小单位
			  initialDate: new Date(),//初始化当前日期
			  autoclose: true,//选中自动关闭
			  todayBtn: true,//显示今日按钮
			  clearBtn:true
		});
		
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });
		
		//给"进药"按钮添加单击事件
		$("#createActivityBtn").click(function(){
			
			 //重置表单
			$("#createActivityForm")[0].reset();
		
			//发送请求
			$.ajax({
				url:'createEnterDrug.do',
				
				type:'post',
				success:function(data){
			
					//显示模态窗口
					
					$("#createActivityModal").modal("show");
				}
			}); 
		});
		
		
		//给"保存"按钮添加单击事件
		$("#saveCreateActivityBtn").click(function(){
			
			//收集参数
			var drug_id=$("#create-drug_id").val();
			
			var drug_name=$("#create-drug_name").val();
			var purchase_price=$.trim($("#create-purchase_price").val());
			var purchase_number=$("#create-purchase_number").val();
			var purchase_date=$("#create-purchase_date").val();
			var provider=$("#create-provider").val();
			var producer=$.trim($("#create-producer").val());
			var specification=$.trim($("#create-specification").val());
			var description=$.trim($("#create-description").val());
			var price=$.trim($("#create-price").val());
			var inventory=$("#create-inventory").val();
			
			//表单验证
			if(drug_id==null||drug_id.length==0){
				alert("药品编号不能为空！");
				return;
			}
			if(drug_name==null||drug_name.length==0){
				alert("药品名称不能为空！");
				return;
			}
			
			//正则表达式
		 	/* var regExp=/^([1-9]\d*|0)$/;
			if(purchase_number!=null&&purchase_number.length>0){
				if(!regExp.test(budgetCost)){
					alert("采购数量只能为非负整数！");
					return;
				}
			} */
			
			//发送请求
			$.ajax({
				url:'saveDrugService.do',
				data:{
					drug_id:drug_id,
					drug_name:drug_name,
					purchase_price:purchase_price,
					purchase_number:purchase_number,
					purchase_date:purchase_date,
					provider:provider,
					producer:producer,
					price:price,
					specification:specification,
					description:description
				},
				type:'post',
				success:function(data){
					
					alert(data);
				 	if(data.success){
						//关闭模态窗口
						$("#createActivityModal").modal("hide");
						//刷新列表(保留)
						 display(1,$("#pageNoDiv").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("创建失败！");
						$("#createActivityModal").modal("show");
					} 
				}
			});

		});
		
		//当bb页面加载完成之后，显示第一页数据
		display(1,10); 
		
		//给"查询"按钮添加单击事件
		$("#queryActivityBtn").click(function(){
			display(1,$("#pageNoDiv").bs_pagination("getOption","rowsPerPage"));
		});
		
		//给"删除"按钮添加单击事件
		$("#deleteActivityBtn").click(function(){
			//收集参数
			var ckdIds=$("#activityListTBody input[type='checkbox']:checked");
			if(ckdIds.size()==0){
				alert("请选择要删除的药品信息！");
				return;
			}
			var ids="";
			$.each(ckdIds,function(index,obj){
				ids+="id="+obj.value+"&";
			});
			ids=ids.substr(0,ids.length-1);
			//发送请求
			if(window.confirm("确定删除吗？")){
				$.ajax({
					url:'deleteDrug.do',
					data:ids,
					type:'post',
					success:function(data){
						if(data.success){
							//刷新药品管理列表，显示第一页数据，保持每页显示条数不变
							display(1,$("#pageNoDiv").bs_pagination("getOption","rowsPerPage"));
						}else{
							alert("删除失败！");
						}
					}
				});
			}
		});
		
		//当页面加载完成之后，"添加字段"下的所有复选框都默认选中
		$("#definedColumns input[type='checkbox']").prop("checked",true);
		//给"添加字段"下的所有复选框添加单击事件
		$("#definedColumns input[type='checkbox']").click(function(){
			if(this.checked){
				$("td[name='"+this.name+"']").show();
			}else{
				$("td[name='"+this.name+"']").hide();
			}
		});
		
		//给"全选"按钮添加单击事件
		$("#chk_all").click(function(){
			$("#activityListTBody input[type='checkbox']").prop("checked",this.checked);
		});
		//给列表中所有的复选框添加单击事件
		$("#activityListTBody").on("click","input[type='checkbox']",function(){
			if($("#activityListTBody input[type='checkbox']").size()==$("#activityListTBody input[type='checkbox']:checked").size()){
				$("#chk_all").prop("checked",true);
			}else{
				$("#chk_all").prop("checked",false);
			}
		});
		
		//给"修改"按钮添加单击事件
		$("#editActivityBtn").click(function(){
			//收集参数
			var chdIds=$("#activityListTBody input[type='checkbox']:checked");
			if(chdIds.size()==0){
				alert("请选择要修改的药品！");
				return;
			}
			if(chdIds.size()>1){
				alert("每次只能修改一条药品信息！");
				return;
			}
			var id=chdIds.val();
			alert(id);
			//发送请求
			$.ajax({
				url:'editDrug.do',
				data:{
					id:id
				},
				type:'post',
				success:function(data){
			
					//设置其它数据
			
					$("#edit-drug_id").val(data.drug.drug_id);
					$("#edit-drug_name").val(data.drug.drug_name);
					$("#edit-purchase_price").val(data.drug.purchase_price);
					$("#edit-purchase_number").val(data.drug.purchase_number);
					$("#edit-purchase_date").val(data.drug.purchase_date);
					$("#edit-provider").val(data.drug.provider);
					$("#edit-producer").val(data.drug.producer);
					$("#edit-specification").val(data.drug.specification);
					$("#edit-description").val(data.drug.description);
					$("#edit-price").val(data.drug.price);
					$("#edit-inventory").val(data.drug.inventory);
					
					//显示模态窗口
					$("#editActivityModal").modal("show");
				}
			});
			
		});
		
		//给"更新"按钮添加单击事件
		$("#saveEditActivityBtn").click(function(){
			//收集参数
		 	var drug_id=$("#edit-drug_id").val();
			var drug_name=$("#edit-drug_name").val();
			var purchase_price=$.trim($("#edit-purchase_price").val());
			var purchase_number=$("#edit-purchase_number").val();
			var purchase_date=$("#edit-purchase_date").val();
			var provider=$("#edit-provider").val();
			var producer=$.trim($("#edit-producer").val());
			var specification=$.trim($("#edit-specification").val());
			var description=$.trim($("#edit-description").val());
			var price=$.trim($("#edit-price").val());
			var inventory=$("#edit-inventory").val();
			//表单验证(作业)
			
			//发送请求
			$.ajax({
				url:'saveEditDrug.do',
				data:{
					drug_id:drug_id,
					drug_name:drug_name,
					purchase_price:purchase_price,
					purchase_number:purchase_number,
					purchase_date:purchase_date,
					inventory:inventory,
					provider:provider,
					producer:producer,
					price:price,
					specification:specification,
					description:description
				},
				type:'post',
				success:function(data){
					if(data.success){
						//关闭模态窗口
						$("#editActivityModal").modal("hide");
						//刷新列表
						display($("#pageNoDiv").bs_pagination("getOption","currentPage"),$("#pageNoDiv").bs_pagination("getOption","rowsPerPage"));
					}
				}
			});
			//给"导出"按钮添加单击事件
			$("#exportActivityBtn").click(function(){
				//收集参数
				   var drug_id=$("#drug_id").val();
		    var drug_name=$("#drug_name").val();
			var purchase_price=$.trim($("#purchase_price").val());
			var purchase_number=$("#purchase_number").val();
			var purchase_date=$("#purchase_date").val();
				//发送请求
				window.location.href="/exportMarketActivity.do?drug_id="+drug_id+"&drug_name="+drug_name+"&purchase_price="+purchase_price+"&purchase_number="+purchase_number+"&purchase_date="+purchase_date;
			});
			
			//给"导入"按钮添加单击事件
			$("#importActivityBtn").click(function(){
				//收集参数
				var fileName=$("#activityFile").val();
				var suffix=fileName.substr(fileName.lastIndexOf(".")+1).toLowerCase();
				if(!(suffix=='xls'||suffix=='xlsx')){
					alert("仅支持.xls或.xlsx格式!");
					return;
				}
				if($("#activityFile")[0].files[0].size>5*1024*1024){
					alert("文件大小不超过5MB!");
					return;
				}
				//发送请求
				//FormData是ajax提供的一个接口，可以模拟键值对向服务器提交数据
				//FormData最大的优势 不但能提交文本数据，还能够提交二进制
				var formData=new FormData();
				formData.append("myFile",$("#activityFile")[0].files[0]);
				formData.append("username","zhangsan");
				$.ajax({
					url:'importMarketActivity.do',
					data:formData,
					type:'post',
					processData:false,//主要是配合contentType参数使用的，默认情况下，ajax对数据进行application/x-www-form-urlencoded编码之前，都会把所有数据统一转化为字符串；把processData设置为false，可以阻止这种行为。
					contentType:false,//ajax向服务器发送数据之前，也会对所有数据进行统一编码，默认情况采用application/x-www-form-urlencoded编码格式；把cotnentType设置为false,可以阻止这种行为。
					success:function(data){
						if(data.success){
							//提示信息
							alert("成功导入"+data.count+"条记录！");
							//关闭模态窗口 
							$("#importActivityModal").modal("hide");
							//刷新列表
							display(1,$("#pageNoDiv").bs_pagination("getOption","rowsPerPage"));
						}else{
							alert("成功导入"+data.count+"条记录！其余的记录请检查文件格式！");
							$("#importActivityModal").modal("show");
						}
					}
				});
			});
		});
		
	
	 function display(pageNo,pageSize){
		 
			//收集参数
			//var pageNo=1;
			//var pageSize=10;
		    var drug_id=$("#drug_id").val();
		    var drug_name=$("#drug_name").val();
			var purchase_price=$.trim($("#purchase_price").val());
			var purchase_number=$("#purchase_number").val();
			var purchase_date=$("#purchase_date").val();
			
			//发送请求
			 $.ajax({
				url:'queryDrugForPageByCondition.do',
				data:{
					pageNo:pageNo,
					pageSize:pageSize,
					drug_id:drug_id,
					drug_name:drug_name,
					purchase_price:purchase_price,
					purchase_number:purchase_number,
					purchase_date:purchase_date,
				}, 
				type:'post',
				success:function(data){
					//设置总条数
					$("#totalCount").html(data.count);//功能由翻页插件取代
					//设置列表
				    var htmlStr="";
					$.each(data.dataList,function(index,obj){
						htmlStr+="<tr>";
						htmlStr+="<td><input value='"+obj.drug_id+"' type='checkbox' /></td>";
						htmlStr+="<td name='drug_id'>"+obj.drug_id+"</td>";
						htmlStr+="<td name='name'><a style='text-decoration: none; cursor: pointer;' onclick='window.location.href=\"workbench/activity/detailMarketActivity.do?id="+obj.drug_id+"\";'>"+obj.drug_name+"</a></td>";
						
						htmlStr+="<td name='purchase_price'>"+obj.purchase_price+"</td>";
					    htmlStr+="<td name='price'>"+obj.price+"</td>";
						htmlStr+="<td name='purchase_number'>"+obj.purchase_number+"</td>";
						htmlStr+="<td name='inventory'>"+obj.inventory+"</td>";
						htmlStr+="<td name='purchase_date'>"+obj.purchase_date+"</td>";
						htmlStr+="<td name='provider'>"+obj.provider+"</td>";
						htmlStr+="<td name='producer'>"+obj.producer+"</td>";

						htmlStr+="<td name='specification'>"+obj.specification+"</td>";
						/* htmlStr+="<td name='editBy'>"+(obj.editBy==null?'':obj.editBy)+"</td>";
						htmlStr+="<td name='editTime'>"+(obj.editTime==null?'':obj.editTime)+"</td>"; */
						/* htmlStr+="<td name='description'>"+obj.description+"</td>"; */
						htmlStr+="</tr>";
					});
					$("#activityListTBody").html(htmlStr);
					//隔行换颜色
					$("#activityListTBody tr:even").addClass("active");
					
					//调用翻页工具函数
					//计算总页数
					var totalPages=0;
					if(data.count%pageSize==0){
						totalPages=data.count/pageSize;
					}else{
						totalPages=parseInt(data.count/pageSize)+1;
					}
					$("#pageNoDiv").bs_pagination({
						  currentPage:pageNo,//当前页号
						  rowsPerPage:pageSize,//每页显示条数
						  totalRows:data.count,//总记录条数
					      totalPages: totalPages,//总页数(必须填)
					      
					      visiblePageLinks:3,//做多显示的卡片数
					      
					      showGoToPage:true,//是否显示"跳转到"
					      showRowsPerPage:true,//是否显示"每页显示条数"
					      showRowsInfo:true,//是否显示记录信息
					      //用来监听页号切换事件的,event就代表这个事件，pageObj代表页信息
					      onChangePage: function(event,pageObj) { // returns page_num and rows_per_page after a link has clicked
					      	//alert(pageObj.currentPage);
					        //alert(pageObj.rowsPerPage);
					    	  display(pageObj.currentPage,pageObj.rowsPerPage);
					      }
				  });
				} 
			 });
	 }

	}); 
</script>
</head>
<body>

	<!-- 进药的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">进药信息录入</h4>
				</div>
				<div class="modal-body">
				
					<form id="createActivityForm" class="form-horizontal" role="form">
					
						<div class="form-group">
						
							<label for="create-startTime" class="col-sm-2 control-label">药品编码</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-drug_id">
							</div>
							
							
							<label for="create-startTime" class="col-sm-2 control-label">药品名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-drug_name">
							</div>
							
							
							<label for="create-startTime" class="col-sm-2 control-label">采购价</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-purchase_price">
							</div>
						
							<label for="create-startTime" class="col-sm-2 control-label">采购数量</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-purchase_number">
							</div>
							
							<label for="create-startTime" class="col-sm-2 control-label">采购日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-purchase_date" readonly="readonly">
							</div>
							
							<label for="create-startTime" class="col-sm-2 control-label">供应商</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-provider">
							</div>
							
							
							<label for="create-startTime" class="col-sm-2 control-label">生产厂家</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-producer">
							</div>
						
							<label for="create-startTime" class="col-sm-2 control-label">规格</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-specification">
							</div>
							
							<label for="create-startTime" class="col-sm-2 control-label">零售价</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-price">
							</div>
							
							<!-- <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-endDate" readonly="readonly">
							</div> -->
						</div>
						
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveCreateActivityBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改药品信息的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改药品信息</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-drug_id">
					
						<div class="form-group">
							<label for="edit-actualCost" class="col-sm-2 control-label">药品名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-drug_name" value="4,000">
							</div>
							<label for="edit-budgetCost" class="col-sm-2 control-label">采购价</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-purchase_price" value="5,000">
							</div>
						</div>
						
							
						<div class="form-group">
							<label for="edit-actualCost" class="col-sm-2 control-label">采购数量</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-purchase_number" value="4,000">
							</div>
							<label for="edit-actualCost" class="col-sm-2 control-label">零售价</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-price" value="4,000">
							</div>
						
						</div>
					
						<div class="form-group">
							<label for="edit-actualCost" class="col-sm-2 control-label">生产厂家</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-producer" value="4,000">
							</div>
								<label for="edit-budgetCost" class="col-sm-2 control-label">供应商</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-provider" value="5,000">
							</div>
							
						</div>

						<div class="form-group">
						<label for="edit-budgetCost" class="col-sm-2 control-label">规格</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-specification" value="5,000">
							</div>
							<label for="edit-actualCost" class="col-sm-2 control-label">库存</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-inventory" value="4,000">
							</div>
							
							
						</div>
					
						<div class="form-group">
						<label for="edit-startTime" class="col-sm-2 control-label">采购日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-purchase_date" value="2020-10-10">
							</div>
							
						</div>
						
						<div class="form-group">
						
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description">药品信息Marketing，是指品牌主办或参与的展览会议与公关药品活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveEditActivityBtn" type="button" class="btn btn-primary">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 导入药品信息的模态窗口 -->
	<div class="modal fade" id="importActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">导入药品活动</h4>
				</div>
				<div class="modal-body" style="height: 350px;">
					<div style="position: relative;top: 20px; left: 50px;">
						请选择要上传的文件：<small style="color: gray;">[仅支持.xls或.xlsx格式]</small>
					</div>
					<div style="position: relative;top: 40px; left: 50px;">
						<input type="file" id="activityFile">
					</div>
					<div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
						<h3>重要提示</h3>
						<ul>
							<li>给定文件的第一行将视为字段名。</li>
							<li>请确认您的文件大小不超过5MB。</li>
							<li>从XLS/XLSX文件中导入全部重复记录之前都会被忽略。</li>
							<li>复选框值应该是1或者0。</li>
							<li>日期值必须为MM/dd/yyyy格式。任何其它格式的日期都将被忽略。</li>
							<li>日期时间必须符合MM/dd/yyyy hh:mm:ss的格式，其它格式的日期时间将被忽略。</li>
							<li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
							<li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
						</ul>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
				</div>
			</div>
		</div>
	</div>
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>药品管理列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 130%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">药品编码</div>
				      <input class="form-control" type="text" id="drug_id">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">药品名称</div>
				      <input class="form-control" type="text" id="drug_name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">采购价格</div>
				      <input class="form-control" type="text" id="purchase_price">
				    </div>
				  </div>
		
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">采购数量</div>
					  <input class="form-control" type="text" id="purchase_number" />
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">采购日期</div>
					  <input class="form-control myDate" type="text" id="purchase_date"readonly="readonly">
				    </div>
				  </div>
				  <button id="queryActivityBtn" type="button" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button id="createActivityBtn" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 进药</button>
				  <button id="editActivityBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button id="deleteActivityBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 售药</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal"><span class="glyphicon glyphicon-import"></span> 导入</button>
				  <button id="exportActivityBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 导出</button>
				</div>
				
				<div class="btn-group" style="position: relative; top: 18%; left: 5px;">
					<button type="button" class="btn btn-default">添加字段</button>
					<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
						<span class="caret"></span>
						<span class="sr-only">Toggle Dropdown</span>
					</button>
					<ul id="definedColumns" class="dropdown-menu" role="menu"> 
						<li><a href="javascript:void(0);"><input name="drug_id" type="checkbox"/> 药品编码</a></li>
						<li><a href="javascript:void(0);"><input name="drug_name" type="checkbox"/> 药品名称</a></li>
						<li><a href="javascript:void(0);"><input name="purchase_price" type="checkbox"/> 采购价格</a></li>
						<li><a href="javascript:void(0);"><input name="price" type="checkbox"/> 零售价</a></li>
						<li><a href="javascript:void(0);"><input name="purchase_number" type="checkbox"/> 采购数量</a></li>
						<li><a href="javascript:void(0);"><input name="inventory" type="checkbox"/> 库存</a></li>
						<li><a href="javascript:void(0);"><input name="purchase_date" type="checkbox"/> 采购日期</a></li>
						<li><a href="javascript:void(0);"><input name="provider" type="checkbox"/> 供应商</a></li>
						<li><a href="javascript:void(0);"><input name="producer" type="checkbox"/> 生产厂家</a></li>
						<li><a href="javascript:void(0);"><input name="specification" type="checkbox"/> 规格</a></li>

					</ul>
				</div>

				<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
					<form class="form-inline" role="form">
					  <div class="form-group has-feedback">
					    <input type="email" class="form-control" style="width: 300px;" placeholder="支持任何字段搜索">
					    <span class="glyphicon glyphicon-search form-control-feedback"></span>
					  </div>
					</form>
				</div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input id="chk_all" type="checkbox" /></td>
							<td name="drug_id">药品编码</td>
							<td name="drug_name">药品名称</td>
							<td name="purchase_price">采购价</td>
							<td name="price">零售价</td>
							<td name="purchase_number">采购数量</td>
							<td name="inventory">库存</td>
							<td name="purchase_date">采购日期</td>
							<td name="provider">供应商</td>
							<td name="producer">生产厂家</td>
							<td name="specification">规格</td>
							
							<!-- <td name="createTime">创建时间</td>
							<td name="editBy">修改者</td>
							<td name="editTime">修改时间</td>
							<td name="description" width="10%">描述</td> -->
						 	</tr>
					</thead>
					<tbody id="activityListTBody" >
						
					</tbody>
				</table>
				<div id="pageNoDiv"></div>
			</div>
			
		</div>
		
	</div> 
	
</body>
</html>
