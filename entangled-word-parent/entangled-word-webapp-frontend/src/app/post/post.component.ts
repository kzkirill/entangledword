import { Component, OnInit, Input } from '@angular/core';
import { Post } from '../model/post';
import { BlogpostService } from '../services/blogpost.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  @Input() post: Post;
  @Input('master') master: string;

  constructor(private blogpostservice: BlogpostService) { }

  ngOnInit() {
    if (this.post === undefined){
      this.post = new Post('','','');
    }
  }

  onTextkey(value: string) {
    this.post.text = value;
  }

  onAuthorKey(value:string) {
    this.post.author = value;
  }

  onSaveClick() {
    this.blogpostservice.savePost(this.post);
  }

}
