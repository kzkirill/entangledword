import {Component, OnInit, Input, NgZone} from '@angular/core';
import {Post} from '../model/post';
import {BlogpostService} from '../services/blogpost.service';
import {PostDataBus} from './post.data.bus';
import {PostState} from './poststate';
import {Router} from '@angular/router';

@Component({
    selector: 'app-post',
    templateUrl: './post.component.html',
    styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

    @Input() post: Post;
    @Input('master') master: string;
    protected isNew = false;

    constructor(protected postDataBus: PostDataBus,
                private router: Router,
                private ngZone: NgZone) {
    }

    ngOnInit() {
    }

    getTextPreview(): String {
        return this.post.text.split(' ').slice(0, 3).join(' ') + '...';
    }

    detailsClicked() {
        this.postDataBus.setState(PostState.newFromPostReadOnly(this.post));
        this.ngZone.run(() =>
            this.router.navigate(['/newpost']).then(nav => console.log(nav)));
    }

}
