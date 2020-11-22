package io.entangledword.domain.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogpostPreview extends BlogpostDTO {
    private String userFullname ;
    private String userPicture ;
	
    
    public BlogpostPreview(BlogpostDTO blogpost, String userFullname, String userPicture) {
		super(blogpost.getTitle(), blogpost.getText(), blogpost.getAuthor());
		this.setCreated(blogpost.getCreated());
		this.setUpdated(blogpost.getUpdated());
		this.setID(blogpost.getID());
		this.setReplies(blogpost.getReplies());
		this.setTags(blogpost.getTags());
		this.setUserFullname(userFullname);
		this.setUserPicture(userPicture);
	}
}
