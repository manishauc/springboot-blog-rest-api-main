package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Tweets;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.TweetsRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private TweetsRepository tweetsRepository;
    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository, TweetsRepository tweetsRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.tweetsRepository = tweetsRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long tweetId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        Tweets tweet = tweetsRepository.findById(tweetId).orElseThrow(
                () -> new ResourceNotFoundException("Tweet", "id", tweetId));

        // set post to comment entity
        comment.setTweet(tweet);

        // comment entity to DB
        Comment newComment =  commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByTweetId(long tweetId) {
        // retrieve comments by tweetId
        List<Comment> comments = commentRepository.findByTweetId(tweetId);

        // convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public Long getCommentsByTweetIdCount(long tweetId) {
        // retrieve comments by tweetId
        List<Comment> comments = commentRepository.findByTweetId(tweetId);

        // convert list of comment entities to list of comment dto's
        return comments.stream().count();
    }
    

    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return  commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
        return  comment;
    }
}
