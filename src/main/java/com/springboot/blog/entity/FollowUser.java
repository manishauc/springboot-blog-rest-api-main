package com.springboot.blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "follow_user")
public class FollowUser {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;   
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "followedBy", nullable = false)
	    private User followedBy;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "followedTo", nullable = false)
	    private User followedTo;

		public Long getId() {
			return id;
		}
 
		public void setId(Long id) {
			this.id = id;
		}

		public User getFollowedBy() {
			return followedBy;
		}

		public void setFollowedBy(User followedBy) {
			this.followedBy = followedBy;
		}

		public User getFollowedTo() {
			return followedTo;
		}

		public void setFollowedTo(User followedTo) {
			this.followedTo = followedTo;
		}
	    
	    
}
