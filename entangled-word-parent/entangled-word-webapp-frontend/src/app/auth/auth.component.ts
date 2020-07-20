import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../services/user.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-auth',
    templateUrl: './auth.component.html',
    styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

    title: String = 'Sign In';
    isSubmitting: Boolean = false;
    authForm: FormGroup;

    constructor(private userService: UserService,
                private router: Router,
                private fb: FormBuilder) {
        this.authForm = this.fb.group({
            'username': ['', Validators.required]
        });

    }

    ngOnInit() {
    }

    submitForm() {
        this.isSubmitting = true;
        this.userService.attemptAuth(this.authForm.value.username).subscribe(() => {
                this.router.navigateByUrl('/');
                console.info('User name is ' + this.userService.getCurrentUser().username);
            },
            () => this.isSubmitting = false);
    }
}
