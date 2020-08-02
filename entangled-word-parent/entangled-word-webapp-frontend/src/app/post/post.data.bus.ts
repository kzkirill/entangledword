import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {PostState} from './poststate';

@Injectable({
        providedIn: "root"
    }
)

export class PostDataBus {
    private poststate_: BehaviorSubject<PostState> = new BehaviorSubject(null);
    public readonly current: Observable<PostState> = this.poststate_.asObservable();

    constructor() {}

    setState(newState): void {
        console.log("PostDataBus setState and next " + newState);
        this.poststate_.next(newState);
        console.log("PostDataBus Value after next " + this.poststate_.value.post.text);
    }


}
