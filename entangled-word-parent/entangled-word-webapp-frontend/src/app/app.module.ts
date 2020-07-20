import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {PostComponent} from './post/post.component';
import {FeedComponent} from './feed/feed.component';
import {AllpostsComponent} from './allposts/allposts.component';
import {HttpClientModule} from '@angular/common/http';
import {HeaderComponent} from './header/header.component';
import {AuthComponent} from './auth/auth.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
    declarations: [
        AppComponent,
        PostComponent,
        FeedComponent,
        AllpostsComponent,
        HeaderComponent,
        AuthComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        FormsModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
