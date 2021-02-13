package com.web.blog.service;

import java.util.List;

import com.web.blog.dto.Comment;

public interface CommentService {

	public boolean insert(Comment comment);
	public Comment selectDetail(String commentId);
	public boolean update(Comment comment);
	public void delete(String commentId);
	public List<Comment> selectList(Comment comment);
	public int count();
	
}