import {Observable} from 'rxjs';
import {User} from '../model/user.model';
import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    currrentUser:User = new User('');

    getCurrentUser(){
        return this.currrentUser;
    }

    attemptAuth(credentials): Observable<User> {
        return Observable.create(obs => {
            this.currrentUser = new User(credentials);
            obs.next(this.currrentUser);
            obs.complete();
        });
    }
}