import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { BlogpostService } from '../services/blogpost.service';
import { Post } from '../model/post';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  feed: Post[];

  constructor(private blogpostservice: BlogpostService, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.feed = [];
    this.blogpostservice.getMyFeed().subscribe((post: Post) => {
      this.feed.push(post);
      this.cdr.detectChanges();
    });
  }

}
