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
  private isNew = false;

  constructor(private blogpostservice: BlogpostService) { }

  ngOnInit() {
    if (this.post === undefined) {
      this.isNew = true;
      this.post = new Post('', '', '');
    }
  }

  onTextkey(value: string) {
    this.post.text = value;
  }

  onAuthorKey(value: string) {
    this.post.author = value;
  }

  onSaveClick() {
    if (this.isNew) {
      this.blogpostservice.create(this.post).subscribe(newPost => this.post = newPost);
    } else {
      this.blogpostservice.update(this.post).subscribe(updatedPost => this.post = updatedPost);
    }
  }

}
