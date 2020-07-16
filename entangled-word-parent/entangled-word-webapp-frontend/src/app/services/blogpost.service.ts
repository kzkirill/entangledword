import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Post } from '../model/post';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BlogpostService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  evetStreamOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'text/event-stream' })
  };

  private backEndBaseURL: String = 'http://localhost:8080';
  private blogpostURL: String = 'blogpost';
  private allposts: String = 'blogpostall';
  private eventSource: EventSource;

  getAll(): Observable<Post[]> {
    let url = [this.backEndBaseURL, this.allposts].join('/');
    this.log('The url: ' + url);

    return this.httpClient.get<Post[]>(url, this.httpOptions).
      pipe(
        tap(_ => this.log("Fetched all posts")),
        catchError(this.handleError<Post[]>('getAll', []))
      );

  }

  getMyFeed(): Observable<Post> {
    let observableFeed: Observable<Post> = Observable.create((observer) => {
      let url = [this.backEndBaseURL, this.blogpostURL].join('/');
      this.log('Feed url: ' + url);
      try {
        this.eventSource = new EventSource(url);
        this.eventSource.onmessage = (event) => {
          this.log('got event data' + event['data']);
          let json = JSON.parse(event.data);
          observer.next(new Post(json['id'], json['author'], json['text'], json['title'], json['created'], json['updated']));
        }
        this.eventSource.onerror = (error) => {
          this.log('Event source error: ' + error);
          // readyState === 0 (closed) means the remote source closed the connection,
          // so we can safely treat it as a normal situation. Another way of detecting the end of the stream
          // is to insert a special element in the stream of events, which the client can identify as the last one.
          if (this.eventSource.readyState === 0) {
            this.log('The stream has been closed by the server.');
            this.eventSource.close();
            observer.complete();
          } else {
            observer.error('EventSource error: ' + error);
          }
        }

      } catch (err) {
        this.handleError<String>(err, 'Exception by EventSource constructor');
      }
    });
    return observableFeed;
  }

  create(post: Post) {
    let url = [this.backEndBaseURL, this.blogpostURL].join('/');
    this.log('POST url: ' + url);

    return this.httpClient.post<Post>(url, post, this.httpOptions).
      pipe(
        tap(_ => this.log("Created post " + post.id)),
        catchError(this.handleError<Post>('savePost', undefined))
      );
  }

  update(post: Post) {
    let url = [this.backEndBaseURL, this.blogpostURL, post.id].join('/');
    this.log('PUT url: ' + url);

    return this.httpClient.put<Post>(url, post, this.httpOptions).
      pipe(
        tap(_ => this.log("Updated post " + post.id)),
        catchError(this.handleError<Post>('savePost', undefined))
      );
  }

  private handleError<T>(operation = 'operation', fallbackResult?: T) {
    return (error: any): Observable<T> => {
      this.log(`${operation} failed: ${error.message}`);
      return of(fallbackResult as T);
    };
  }
  private log(message: string) {
    console.log(`Blogpost service: ${message}`);
  }

  constructor(private httpClient: HttpClient) { }
}
