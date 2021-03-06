package com.web.blog.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dto.BandShow;
import com.web.blog.dto.ShowListRes;
import com.web.blog.model.BasicResponse;
import com.web.blog.service.ShowService;

import io.swagger.annotations.ApiOperation;

//@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
//        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
//        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
//        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })


@CrossOrigin
@RestController
public class ShowController {
	@Autowired
	ShowService service;
	
	@PostMapping(value = "/show")
	@ApiOperation(value = "공연신청하기", notes = "공연신청 요청을 보낸다.")
	
	public Object show(@RequestBody BandShow req) {
		BandShow check=service.getShowByTime(req.getDate(), req.getTime());
		System.out.println(req.getDate());
		System.out.println(req.getTime());
		System.out.println(check);
		if(check==null) {
			service.addshow(req);
			final BasicResponse result = new BasicResponse();
	        result.status = true;
	        result.data = "success";
	        return new ResponseEntity<>(result, HttpStatus.OK);
	      
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping(value = "/show/{showId}")
	@ApiOperation(value = "공연취소하기", notes = "공연취소 요청을 보낸다.")
	
	public Object delshow(@PathVariable String showId) {
		service.delshow(showId);
		final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
	      
		
	}
	
	@GetMapping(value = "/show/{bandId}")
	@ApiOperation(value = "공연신청목록 확인", notes = "공연신청목록 요청을 보낸다.")
	
	public Object showlist(@PathVariable String bandId) {
		List<ShowListRes> list=service.showlist(bandId);
		System.out.println(list);
		if(list!=null) {
			final BasicResponse result = new BasicResponse();
	        result.status = true;
	        result.data = "success";
	        result.object=list;
	        return new ResponseEntity<>(result, HttpStatus.OK);
	      
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@GetMapping(value = "/show/list/{date}")
	@ApiOperation(value = "특정날짜 공연 확인", notes = "특정날짜 공연 확인 요청을 보낸다.")
	
	public Object dateshowlist(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date ) {
		List<BandShow> list=service.dateshowlist(date);
		if(list!=null) {
			final BasicResponse result = new BasicResponse();
	        result.status = true;
	        result.data = "success";
	        result.object=list;
	        return new ResponseEntity<>(result, HttpStatus.OK);
	      
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping(value = "/show/{showId}")
	@ApiOperation(value = "공연정보 수정 확인", notes = "공연정보 수정 요청을 보낸다.")
	
	public Object updateshow(@PathVariable String showId, String title, String showContent ) {
		service.updateshow(showId, title, showContent);
		final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
		
	}
	
//	@GetMapping(value = "/checkshow")
//	@ApiOperation(value = "중복공연 확인", notes = "중복공연 확인 요청을 보낸다.")
//	
//	public Object checkshow(@RequestParam @DateTimeFormat(pattern = "kk:mm") LocalTime time ) {
//		BandShow check=service.getShowByTime1(time);
//		System.out.println(check.getTime());
//		if(check==null) {
//			final BasicResponse result = new BasicResponse();
//	        result.status = true;
//	        result.data = "success";
//	        return new ResponseEntity<>(result, HttpStatus.OK);
//	      
//		}
//		else {
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//		
//	}
	
	/**
	 * 기간 내의 공연 정보 Request
	 * @param start
	 * @param end
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/show/term/{start}/{end}")
	@ApiOperation(value = "기간 내의 공연 정보 얻기", notes = "start - end 기간의 공연 정보를 얻어서 반환합니다")
	public Object showTermList(@PathVariable String start, @PathVariable String end) {
		List<BandShow> list = service.showTermList(start, end);
		
		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";
		result.object = list;
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "/show/detail/{showId}")
	@ApiOperation(value = "공연정보보기", notes = "공연의 정보를 보여줍니다")
	public Object showDetail(@PathVariable String showId) {
		BandShow target = service.showDetail(showId);
		if(target != null) {
			final BasicResponse result = new BasicResponse();
	        result.status = true;
	        result.data = "success";
	        result.object = target;
	        
	        return new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}
}
