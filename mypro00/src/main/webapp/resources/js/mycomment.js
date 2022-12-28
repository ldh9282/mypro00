/**
 * mycomment.js : 댓글-답글 데이터 처리용 Ajax Closure Module 
 */
 
//alert("댓글 처리 클로저 모듈 실행됨========================") ;

var myCommentClsr = (function(){

	function getCmtList(pagingParams, callback, error) {
		
		var bno = pagingParams.bno ;
		var page = pagingParams.page || 1 ;
		
		
		console.log("getCmtList()가 전달받은 bno: " + bno) ;
		console.log("getCmtList()가 전달받은 page: " + page) ;
		console.log("getCmtList()함수의 ajax 처리 시작....................");
		
		$.ajax({
			type: 'get' ,
			url : "/mypro00/replies/" + bno + "/page/" + page ,
			dataType: "json" ,
			success: function(replyPagingCreator) {
						if(callback) {
							callback(replyPagingCreator) ;
						}
			} ,
			error: function(xhr, status, err){
				if(error) {
					error(err) ;
				}	
			}
		
		
		}); //.ajax-end
		
	} //getCmtList END
	
	
	//댓글등록
	function registerCmt(comment, callback, error) {
		var bno = comment.bno ;
		
		console.log("registerCmt() 에 전달된 bno: " + bno);
		console.log("registerCmt() 함수의 comment 등록 ajax 처리 시작....................");
		
		$.ajax({
			type: "post" ,
			url : "/mypro00/replies/" + bno + "/new" ,
			data: JSON.stringify(comment) ,
			contentType: "application/json;charset=utf-8" ,
			dataType: "text" ,
			success: function(result, status, xhr) {
						if(callback) {
							callback(result) ; //RegisterSuccess 문자열
						}
			} ,
			error: function(xhr, status, err) {
						if(error) {
							error(err) ;
						}
			} 
		
		});    //ajax-end
		
	
	}    //registerCmt() end
	
	//답글등록
	function registerReply(reply, callback, error) {
		var bno = reply.bno ;
		var prno = reply.prno ;
		
		console.log("registerReply() 전달받은 bno: " + bno);
		console.log("registerReply() 전달받은 prno: " + prno);
		console.log("registerReply()함수의 댓글에 대한 답글 등록 ajax 처리 시작..................");
		
		$.ajax({
			type: "post" ,
			url: "/mypro00/replies/" + bno + "/" + prno + "/new" ,
			data: JSON.stringify(reply) ,
			contentType: "application/json;charset=utf-8" ,
			dataType: "text" ,
			success: function(result, status, xhr) {
						if(callback) {
							callback(result) ;
						}
			} ,
			error: function(xhr, status, err) {
						if(error) {
							error(err) ;
						}
			} 
			
		
		}); //ajax-end
		
	}  //registerReply-end
	
	//특정 댓글답글 조회
	function getCmtReply(bnoAndRno, callback, error){
		var bno = bnoAndRno.bno ;
		var rno = bnoAndRno.rno ;
		
		console.log("getCmtReply() 전달받은 bno: " + bno);
		console.log("getCmtReply() 전달받은 rno: " + rno);
		console.log("getCmtReply() 함수의 특정 댓글에 조회 ajax 처리 시작......");
		
		$.ajax({
			type: "get" ,
			url: "/mypro00/replies/" + bno + "/" + rno ,
			dataType: "json" ,
			success: function(myReply) {
						if(callback) {
							callback(myReply) ;
						}
			} ,
			error: function(xhr, status, err) {
						if(error){
							error(err) ;
						}
			}
			
			
		}); //ajax-end
			
	
	} //getCmtReply-end
	
	//댓글답글 수정
	function modifyCmtReply(comment, callback, error) {
		var bno = comment.bno ;
		var rno = comment.rno ;
		
		console.log("modifyCmtReply() 전달받은 bno: " + bno + " 전달받은 rno: " + rno);
		console.log("modifyCmtReply() 함수의 특정 댓글 수정 ajax 처리 시작......");
		
		$.ajax({
			type: "put" ,
			url: "/mypro00/replies/" + bno + "/" + rno ,
			data: JSON.stringify(comment) ,
			contentType: "application/json; charset=utf-8" ,
			dataType: "text" ,
			success: function(result) {
						if(callback){
							callback(result) ;
						}
			}
		
		}); //ajax-end
	
	}  //modifyCmtReply-end
	
	
	//댓글 삭제(실제 삭제)
	function removeCmtReply(comment, callback, error){
		var bno = comment.bno ;
		var rno = comment.rno ;
		var rwriter = comment.rwriter ;
		
		//console.log("removeCmtReply() 전달받은 bno: " + bno);
		//console.log("removeCmtReply() 전달받은 rno: " + rno);
		//console.log("removeCmtReply() 전달받은 rwrier: " + rwriter);
		//console.log("removeCmtReply() 함수의 댓글 삭제 ajax 처리 시작.......");
		
		$.ajax({
			type: "delete" ,
			url: "/mypro00/replies/" + bno + "/" + rno ,
			data: JSON.stringify({bno: bno, rno: rno, rwriter: rwriter}) ,
			contentType: "application/json; charset=utf-8" ,
			//data: rwriter,
			//contentType: "text/plain; charset=utf-8" ,
			success: function(removeResult) {
						if(callback) {
							callback(removeResult) ;
						}
			}
		
		}) ; //ajax-end 
	
	
	
	} //removeCmtReply-end
	
	
	function showDatetime(datetimeValue){
		
		var dateObj = new Date(datetimeValue) ;
		
		var str = '' ;
		
		var yyyy = dateObj.getFullYear() ;
		var MM = dateObj.getMonth() +1 ;
		var dd = dateObj.getDate() ;
		var hh = dateObj.getHours() ;
		var mm = dateObj.getMinutes() ;
		var ss = dateObj.getSeconds() ;
		
		str = [ yyyy, '-' , 
		        (MM > 9 ? '' : '0') + MM , '-' ,
		        (dd > 9 ? '' : '0') + dd , ' ' ,
		        (hh > 9 ? '' : '0') + hh , ':' ,
		        (mm > 9 ? '' : '0') + mm , ':' ,
		        (ss > 9 ? '' : '0') + ss
		       
		      ].join('') ;
		      
		 return str ;
	
	}
	
	
	return {
		getCmtList : getCmtList ,
		registerCmt : registerCmt ,
		registerReply : registerReply,
		getCmtReply: getCmtReply ,
		modifyCmtReply: modifyCmtReply,
		removeCmtReply: removeCmtReply,
		showDatetime: showDatetime
	};
	
	

})();





