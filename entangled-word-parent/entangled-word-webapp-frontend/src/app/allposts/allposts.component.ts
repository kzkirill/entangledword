import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../model/post';
import { BlogpostService } from '../services/blogpost.service';

@Component({
  selector: 'app-allposts',
  templateUrl: './allposts.component.html',
  styleUrls: ['./allposts.component.css']
})
export class AllpostsComponent implements OnInit {

  allposts: Observable<Post[]>;

  constructor(private blogpostservice: BlogpostService) { }

  ngOnInit() {
    this.allposts = this.blogpostservice.getAll();
  }

}
