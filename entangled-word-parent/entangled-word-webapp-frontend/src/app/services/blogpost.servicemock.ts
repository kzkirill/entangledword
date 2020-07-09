import { Injectable } from '@angular/core';
import { Post } from '../model/post'

@Injectable({
  providedIn: 'root'
})
export class BlogpostServiceMock {
  myFeed: Post[] = [
    {
      id: '12342',
      author: 'Will Smith',
      text: 'WHen you fall thhasd aksjh  dhdaskjhkjasd you can'
    },
    {
      id: '234324asdsd12342',
      author: 'Green Door',
      text: ' runtime.js, runtime.js.map (runtime) 6.08 kB'
    }

  ]

  getMyFeed() {
    return this.myFeed;
  }

  savePost(post: Post) {
    if (!post.id) {
      post.id = '1as687' + Date.now;
      this.myFeed.push(post)
    } else {
      this.myFeed.find(memeber => memeber.id === post.id).text = post.text;
    }
  }

  constructor() { }
}
