import {Injectable} from '@angular/core';
import {CanActivate} from '@angular/router';
import {ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router/src/router_state';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    constructor() {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return true;
    }
}
