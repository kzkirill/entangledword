import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './feed/feed.component';
import { PostComponent } from './post/post.component'
import { AllpostsComponent } from './allposts/allposts.component';

const routes: Routes = [
  { path: 'feed', component: FeedComponent },
  { path: 'all', component: AllpostsComponent },
  { path: 'newpost', component: PostComponent },
  { path: '', redirectTo: '/feed', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
