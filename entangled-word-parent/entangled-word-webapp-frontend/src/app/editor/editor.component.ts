import {ChangeDetectorRef, Component, Input, NgZone, OnInit} from '@angular/core';
import {BlogpostService} from '../services/blogpost.service';
import {Post} from '../model/post';
import {PostDataBus} from '../post/post.data.bus';
import {Router} from '@angular/router';
import {STATE_READ_ONLY} from '../post/poststate';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
    selector: 'app-editor',
    templateUrl: './editor.component.html',
    styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {
    isReadOnly: Boolean = true;
    post: Post;


    constructor(private blogpostservice: BlogpostService,
                private postDataBus: PostDataBus,
                private fb: FormBuilder,
                private ngZone: NgZone,
                private router: Router) {
        this.postDataBus.current.subscribe(state => {
            this.updateModel(state.post);
            this.isReadOnly = state.mode == STATE_READ_ONLY;
        });
    }


    ngOnInit() {
    }

    onSaveClick() {
        this.blogpostservice.create(this.post).subscribe(() => {
            this.ngZone.run(() =>
                this.router.navigate(['/feed']).then(nav => console.log(nav)));

        });
    }


    private updateModel(post: Post) {
        this.post = post;
    }

    onTextkey(value: string) {

    }
}
